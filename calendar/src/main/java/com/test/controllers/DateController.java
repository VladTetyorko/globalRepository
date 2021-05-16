package com.test.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.calendar.Calendar;
import com.test.calendar.entities.Date;
import com.test.calendar.exeptions.CalendarException;
import com.test.calendar.exeptions.IlegalDateException;
import com.test.calendar.exeptions.IlegalMonthException;
import com.test.services.DateService;

@Controller
public class DateController {

	private final DateService dateService;

	private static final Logger logger = LoggerFactory.getLogger(DateController.class.getName());

	public DateController(DateService dateService) {
		this.dateService = dateService;
	}

	@RequestMapping(value = "/")
	public String findCalendarOnMonth(Model model, @RequestParam(value = "y", defaultValue = "2021") int year,
			@RequestParam(value = "m", defaultValue = "5") int month) {
		logger.info("Opening calendar on {} {}", month, year);
		try {
			List<Date> previous = dateService.findLastDaysInPreviousMonth(month, year);
			List<Date> current = dateService.findMonth(month, year);
			List<Date> nextDays = dateService.findFirstDaysInPreviousMonth(month, year);
			String stringMonth = Calendar.getStringMonth(month);
			model.addAttribute("year", year);
			model.addAttribute("month", month);
			model.addAttribute("stringMonth", stringMonth);
			model.addAttribute("prevoiousDays", previous);
			model.addAttribute("currentDays", current);
			model.addAttribute("nextDays", nextDays);

			logger.info("Opened successfull");
			return "calendar";
		} catch (IlegalMonthException e) {
			logger.warn(e.getMessage());
			throw new CalendarException(e.getMessage());
		}
	}

	@RequestMapping(value = "/", params = { "y", "m", "d" })
	public String findCalendar(Model model, @RequestParam(value = "y", defaultValue = "2021") int year,
			@RequestParam(value = "m", defaultValue = "5") int month,
			@RequestParam(value = "d", defaultValue = "1") int day) {
		logger.info("Opening tasks on {}.{}.{}", day, month, year);
		try {
			Date d = dateService.findDay(day, month, year);
			model.addAttribute("date", d);
			logger.info("Openen successful");
			return "day";
		} catch (IlegalDateException e) {
			logger.warn(e.getMessage());
			throw new CalendarException(e.getMessage());
		}
	}

}
