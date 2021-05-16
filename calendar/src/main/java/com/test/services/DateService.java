package com.test.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.calendar.Calendar;
import com.test.calendar.entities.Date;
import com.test.calendar.exeptions.IlegalDateException;
import com.test.calendar.exeptions.IlegalMonthException;
import com.test.repositories.DateRepository;

@Service
public class DateService {

	private final DateRepository repository;

	private final Calendar calendar;

	private static final Logger logger = LoggerFactory.getLogger(DateService.class.getName());

	public DateService(DateRepository repository, Calendar calendar) {
		this.repository = repository;
		this.calendar = calendar;
	}

	@Transactional
	public Date save(Date date) {
		Date saved = null;
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
	public Optional<Date> find(int id) {
		Optional<Date> founded = null;
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
	public List<Date> findAll() {
		if (logger.isDebugEnabled())
			logger.debug("Searching for all dates with tasks");
		List<Date> foundedList = repository.findAll();
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
	public List<Date> findMonth(int month, int year) {
		if (logger.isDebugEnabled())
			logger.debug("Searching for month #{} ({} year) calendar", month, year);
		if (month < 0 || month > 12) {
			logger.warn("Illegal date!", new IlegalMonthException(month, year));
			throw new IlegalMonthException(month, year);
		}
		List<Date> foundedList = repository.findByMonthAndYear(month, year);
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
	public Date findDay(int day, int month, int year) {
		if (logger.isDebugEnabled())
			logger.debug("Searching for {}.{}.{} day", day, month, year);

		if (day < 0 || day > Calendar.getNumberOfDays(month, year) || month < 0 || month > 12) {
			logger.warn("Illegal date!", new IlegalDateException(day, month, year).getMessage());
			throw new IlegalDateException(day, month, year);
		}

		Optional<Date> founded = repository.findByDayAndMonthAndYear(day, month, year);
		if (founded.isPresent()) {
			logger.info("Day with id={} was founded", founded.get().getID());
			return founded.get();
		} else
			logger.info("Was created new day");
		return new Date(day, month, year);
	}

	public List<Date> findLastDaysInPreviousMonth(int month, int year) {
		int neededYear = 0;
		int neededMonth = 0;
		List<Date> list = new ArrayList<Date>();
		if (month == 1) {
			neededYear = year - 1;
			neededMonth = 12;
		} else {
			neededYear = year;
			neededMonth = month - 1;
		}
		int dayCount = Calendar.getNumberOfDays(neededMonth, neededYear);
		int firstDayOfCurrent = Calendar.getWeekday(1, month, year);
		int i = 0;
		while (i < firstDayOfCurrent - 1) {
			Date d = findDay(dayCount, neededMonth, neededYear);
			list.add(d);
			dayCount--;
			i++;
		}
		Collections.sort(list);
		return list;
	}

	@Transactional
	public List<Date> findFirstDaysInPreviousMonth(int month, int year) {
		int neededYear = 0;
		int neededMonth = 0;
		List<Date> list = new ArrayList<Date>();
		if (month == 12) {
			neededYear = year + 1;
			neededMonth = 1;
		} else {
			neededYear = year;
			neededMonth = month + 1;
		}
		int lastDayInCurrent = Calendar.getNumberOfDays(month, year);
		int firstDayOfNext = Calendar.getWeekday(lastDayInCurrent, month, year);
		int i = firstDayOfNext;
		int dayCount = 1;
		while (i < 7) {
			Date d = findDay(dayCount, neededMonth, neededYear);
			list.add(d);
			dayCount++;
			i++;
		}
		Collections.sort(list);
		return list;
	}

}
