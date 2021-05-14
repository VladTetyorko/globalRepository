package com.test.calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.test.calendar.entities.Date;
import com.test.calendar.entities.Task;

public class CalendarTest {

	private List<Date> list = new ArrayList<Date>();
	private final Calendar c = new Calendar();

	@BeforeEach
	void setup() {
		Date d1 = new Date(3, 5, 2021);
		Date d2 = new Date(6, 5, 2021);
		Date d3 = new Date(9, 5, 2021);
		Task t1 = new Task("11:00", "Test Task", d1);
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(t1);
		d1.setTasks(tasks);
		d3.setTasks(tasks);
		list.add(d1);
		list.add(d2);
		list.add(d3);
	}

	@Test
	void shouldReturnFullMonthWhenDaysExsist() {
		List<Date> result = c.fillCalendar(list);
		assertTrue(result.size() == 31);
		assertTrue(result.get(2).getTasks() != null);
	}

	@Test
	void shouldReturnFullMonthWhenNoDaysExsist() {
		List<Date> result = c.fillCalendar(5, 2021);
		assertTrue(result.size() == 31);
	}

	@Test
	void shouldReturnCorrectDayCount() {
		assertTrue(c.getNumberOfDays(5, 2021) == 31);
		assertTrue(c.getNumberOfDays(4, 2021) == 30);
		assertTrue(c.getNumberOfDays(2, 2020) == 29);
	}

	@Test
	void shouldReturnCorrectLastDayOfPreviousMounth() {
		assertEquals(5, c.findLastDaysInPreviousMonth(5, 2021).size());
	}

}