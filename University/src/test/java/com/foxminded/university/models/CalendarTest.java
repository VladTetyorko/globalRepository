package com.foxminded.university.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.foxminded.university.entities.Calendar;

class CalendarTest {
	private final LocalDate startPoint = LocalDate.of(2020, 1, 6);
	private final Calendar calendar = new Calendar(startPoint, 2);

	@Test
	final void shouldGetDate() {
		String testDateString = "2020-01-06";
		LocalDate testDate1 = calendar.getDate(testDateString);
		assertEquals(startPoint, testDate1);
		assertEquals(DayOfWeek.MONDAY, testDate1.getDayOfWeek());
	}

	@Test
	final void shouldExtermWhenIllegalDate() {
		String testDateString = "2020-01-05";
		assertThrows(IllegalArgumentException.class, () -> calendar.getDate(testDateString));
	}

}
