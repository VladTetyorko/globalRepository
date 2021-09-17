package com.foxminded.university.dao;

import java.time.LocalDate;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.foxminded.university.entities.Calendar;

@TestConfiguration
@EnableTransactionManagement
public class TestHelper {

	@Bean(name = "testCalendar")
	public Calendar getTestCalendar() {
		LocalDate startPoint = LocalDate.of(2020, 1, 6);
		return new Calendar(startPoint, 1);
	}

}
