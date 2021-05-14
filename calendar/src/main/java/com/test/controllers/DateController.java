package com.test.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.calendar.Calendar;
import com.test.calendar.entities.Date;
import com.test.calendar.exeptions.IlegalDateException;
import com.test.services.DateService;

@Controller
public class DateController {

	private final DateService dateService;
	private final Calendar calendar;

	public DateController(DateService dateService, Calendar calendar) {
		this.calendar = calendar;
		this.dateService = dateService;
	}

	@RequestMapping(value = "/")
	public String currentCalendar(Model model) {
		List<Date> current = dateService.findMonth(5, 2021);
		List<Date> previous = calendar.findLastDaysInPreviousMonth(5, 2021);
		String stringMonth = calendar.getStringMonth(5);
		model.addAttribute("month", stringMonth);
		model.addAttribute("prevoiousDays", previous);
		model.addAttribute("currentDays", current);
		return "calendar";
	}

	@RequestMapping(value = "/calendar/{year}/{month}")
	public String findCalendarOnMonth(Model model, @PathVariable(value = "year") int year,
			@PathVariable(value = "month") int month) {
		List<Date> current = dateService.findMonth(month, year);
		List<Date> previous = calendar.findLastDaysInPreviousMonth(month, year);
		String stringMonth = calendar.getStringMonth(month);
		model.addAttribute("month", stringMonth);
		model.addAttribute("prevoiousDays", previous);
		model.addAttribute("currentDays", current);
		return "calendar";
	}

	@RequestMapping(value = "/calendar/{year}/{month}/{day}")
	public String findCalendar(Model model, @PathVariable(value = "year") int year,
			@PathVariable(value = "month") int month, @PathVariable(value = "day") int day) {
		try {
			Date d = dateService.findDay(day, month, year);
			model.addAttribute("date", d);
			return "day";
		} catch (IlegalDateException e) {
			return "redirect:/";
		}
	}

}
