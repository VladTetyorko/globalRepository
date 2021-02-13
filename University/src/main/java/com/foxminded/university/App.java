package com.foxminded.university;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.foxminded.university.entities.Calendar;

@SpringBootApplication
public class App {

	private static Logger logger = LoggerFactory.getLogger(App.class.getName());

	public static void main(String[] args) {
		logger.info("Application Start");
		SpringApplication.run(App.class, args);
		logger.info("Application Finished");
	}

	@Bean(name = "calendar")
	public Calendar getCalendar() {
		LocalDate thisDay = LocalDate.now();
		LocalDate startPoint = thisDay.minusDays(thisDay.getDayOfWeek().getValue()).plusDays(1);
		return new Calendar(startPoint, 1);
	}

}
