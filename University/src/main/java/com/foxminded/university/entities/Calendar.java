package com.foxminded.university.entities;

import java.time.LocalDate;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;

public class Calendar {

	private List<LocalDate> calendarList;
	private final DayOfWeek[] dayList= {DayOfWeek.MONDAY,DayOfWeek.TUESDAY,DayOfWeek.WEDNESDAY,DayOfWeek.THURSDAY,DayOfWeek.FRIDAY};
	private final SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private Logger logger = LoggerFactory.getLogger(Calendar.class.getName());

	public Calendar(LocalDate startPoint, int weekCount) {
		if (logger.isDebugEnabled())
			logger.debug("Creating new calendar with startPoint={} and {} weeks", startPoint.toString(), weekCount);
		if (!startPoint.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
			logger.error("Wrong date, first day should be monday");
			throw new IllegalArgumentException("Illegal date. First day must be Monday");
		}
		List<LocalDate> calendarList = new ArrayList<LocalDate>();
		LocalDate day = startPoint;
		for (int week = 0; week < weekCount; week++) {
			for (int i = 1; i <= 5; i++) {
				calendarList.add(day);
				day = day.plusDays(1);
			}
			day = day.plusDays(2);
		}
		this.calendarList = calendarList;

	}

	public boolean ifDateMatches(LocalDate thisDate) {
		Optional<LocalDate> date = calendarList.stream().filter(d -> d.isEqual(thisDate)).findAny();
		if (date.isPresent())
			return true;
		else
			return false;
	}

	public LocalDate getDate(String stringDate) {
		Date date = null;
		try {
			if (logger.isDebugEnabled())
				logger.debug("Converting table string value {} to LocalDate", stringDate);
			date = simpleDateFormatter.parse(stringDate);
			LocalDate returningDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (this.calendarList.stream().filter(d -> d.isEqual(returningDate)).findAny().isPresent())
				return returningDate;
			else {
				logger.error("{} date is out of semester.",stringDate);
				throw new IllegalArgumentException("Date is out of semester");
			}
		} catch (ParseException e) {
			logger.error("Wrong format saved in table. Cant't convert to date", e);
			throw new IllegalArgumentException();
		}
	}
	public List<LocalDate> getCalendar(){
		return calendarList;
	}

	public DayOfWeek[] getDayList() {
		return dayList;
	}
}
