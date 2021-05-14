package com.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.test.calendar.Calendar;
import com.test.calendar.exeptions.IlegalDateException;

@SpringBootApplication
public class CalendarApplication {

	private static Logger logger = LoggerFactory.getLogger(CalendarApplication.class.getName());

	public static void main(String[] args) throws IlegalDateException {
		logger.info("Application Start");
		SpringApplication.run(CalendarApplication.class, args);
		logger.info("Application Start");

	}

	@Bean
	public Calendar getCalendar() {
		return new Calendar();
	}
}
