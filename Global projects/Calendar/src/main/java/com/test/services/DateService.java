package com.test.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.calendar.CalendarImpl;
import com.test.calendar.entities.DateEntity;
import com.test.calendar.exeptions.IlegalDateException;
import com.test.calendar.exeptions.IlegalMonthException;
import com.test.repositories.DateRepository;

@Service
public class DateService {

	private final DateRepository repository;

	private final CalendarImpl calendar;

	private static final Logger logger = LoggerFactory.getLogger(DateService.class.getName());

	public DateService(DateRepository repository, CalendarImpl calendar) {
		this.repository = repository;
		this.calendar = calendar;
	}

	@Transactional
	public DateEntity save(DateEntity date) {
		DateEntity saved = null;
		try {
			saved = repository.save(date);
			logger.info("Date {} saved", saved.toString());
			return saved;
		} catch (Exception e) {
			logger.warn(e.toString());
		}
		return saved;
	}

	@Transactional
	public Optional<DateEntity> find(int id) {
		Optional<DateEntity> founded = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching date with id {}", id);
		founded = repository.findById(id);
		if (founded.isPresent())
			logger.info("Date with id {} was found successfull", id);
		else
			logger.warn("Date with id {} doesnt exsist", id);
		return founded;
	}

	@Transactional
	public List<DateEntity> findAll() {
		if (logger.isDebugEnabled())
			logger.debug("Searching for all dates with tasks");
		List<DateEntity> foundedList = repository.findAll();
		return foundedList;
	}

	@Transactional
	public boolean delete(int id) {
		if (logger.isDebugEnabled())
			logger.debug("Deleting day with id {}", id);
		try {
			repository.deleteById(id);
			logger.info("Day with id={} was deleted", id);
			return true;
		} catch (Exception e) {
			logger.warn("Day with id={} does not exsist" + e.toString(), id);
			return false;
		}
	}

	@Transactional
	public List<DateEntity> findMonth(int month, int year) {
		if (logger.isDebugEnabled())
			logger.debug("Searching for month #{} ({} year) calendar", month, year);
		if (month < 0 || month > 12) {
			logger.warn("Illegal date!", new IlegalMonthException(month, year));
			throw new IlegalMonthException(month, year);
		}
		List<DateEntity> foundedList = repository.findByMonthAndYear(month, year);
		if (!foundedList.isEmpty()) {
			logger.debug("Some days was found, filling another. month={},{}", month, year);
			foundedList = calendar.fillCalendar(foundedList);
		} else {
			logger.debug("No days was found in month={}, {}", month, year);
			foundedList = calendar.fillCalendar(month, year);
		}
		logger.info("Mounth #{},{} found successful", month, year);
		return foundedList;
	}

	@Transactional
	public DateEntity findDay(int day, int month, int year) {
		if (logger.isDebugEnabled())
			logger.debug("Searching for {}.{}.{} day", day, month, year);
		if (day < 0 || day > CalendarImpl.getNumberOfDays(month, year) || month < 0 || month > 12) {
			logger.warn("Illegal date!", new IlegalDateException(day, month, year).getMessage());
			throw new IlegalDateException(day, month, year);
		}
		Optional<DateEntity> founded = repository.findByDayAndMonthAndYear(day, month, year);
		if (founded.isPresent()) {
			logger.info("Day with id={} was founded", founded.get().getID());
			return founded.get();
		} else
			logger.info("Was created new day");
		return new DateEntity(day, month, year);
	}

	public List<DateEntity> findLastDaysInPreviousMonth(int month, int year) {
		int neededYear = 0;
		int neededMonth = 0;
		List<DateEntity> list = new ArrayList<DateEntity>();
		if (month == 1) {
			neededYear = year - 1;
			neededMonth = 12;
		} else {
			neededYear = year;
			neededMonth = month - 1;
		}
		int dayCount = CalendarImpl.getNumberOfDays(neededMonth, neededYear);
		int firstDayOfCurrent = CalendarImpl.getWeekday(1, month, year);
		int i = 0;
		while (i < firstDayOfCurrent - 1) {
			DateEntity d = findDay(dayCount, neededMonth, neededYear);
			list.add(d);
			dayCount--;
			i++;
		}
		Collections.sort(list);
		return list;
	}

	@Transactional
	public List<DateEntity> findFirstDaysInPreviousMonth(int month, int year) {
		int neededYear = 0;
		int neededMonth = 0;
		List<DateEntity> list = new ArrayList<DateEntity>();
		if (month == 12) {
			neededYear = year + 1;
			neededMonth = 1;
		} else {
			neededYear = year;
			neededMonth = month + 1;
		}
		int lastDayInCurrent = CalendarImpl.getNumberOfDays(month, year);
		int firstDayOfNext = CalendarImpl.getWeekday(lastDayInCurrent, month, year);
		int i = firstDayOfNext;
		int dayCount = 1;
		while (i < 7) {
			DateEntity d = findDay(dayCount, neededMonth, neededYear);
			list.add(d);
			dayCount++;
			i++;
		}
		Collections.sort(list);
		return list;
	}

}
