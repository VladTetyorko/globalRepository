package com.test.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.calendar.Calendar;
import com.test.calendar.entities.Date;
import com.test.calendar.exeptions.IlegalDateException;
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
			logger.error(e.toString());
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
			logger.debug("Searching for all dates");
		List<Date> foundedList = repository.findAll();
		return foundedList;
	}

	@Transactional
	public List<Date> findMonth(int month, int year) {
		if (logger.isDebugEnabled())
			logger.debug("Searching for month #{} ({} year) calendar", month, year);
		List<Date> foundedList = repository.findByMonthAndYear(month, year);
		if (!foundedList.isEmpty())
			foundedList = calendar.fillCalendar(foundedList);
		else
			foundedList = calendar.fillCalendar(month, year);
		return foundedList;
	}

	@Transactional
	public Date findDay(int day, int month, int year) {
		if (logger.isDebugEnabled())
			logger.debug("Searching for {}.{}.{} day", day, month, year);
		if (day < 0 || day > Calendar.getNumberOfDays(month, year) || month < 0 || month > 12) {
			logger.warn("Illegal date!", new IlegalDateException(month, year));
			throw new IlegalDateException(month, year);
		}
		Optional<Date> founded = repository.findByDayAndMonthAndYear(day, month, year);
		if (founded.isPresent()) {
			logger.info("Day with id={} was founded", founded.get().getID());
			return founded.get();
		} else
			logger.info("Was created new day");
		return new Date(day, month, year);
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
			logger.warn(e.toString());
			return false;
		}
	}

}
