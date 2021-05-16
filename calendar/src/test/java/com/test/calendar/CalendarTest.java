package com.test.calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.test.calendar.entities.Date;
import com.test.calendar.entities.Task;

public class CalendarTest {

	private List<Date> list = new ArrayList<Date>();
	private final Calendar calendar = new Calendar();

	@Test
	void shouldReturnFullMonthWhenDaysExsist() {

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

		List<Date> result = calendar.fillCalendar(list);
		assertTrue(result.size() == 31);
		assertTrue(result.get(2).getTasks() != null);
	}

	@Test
	void shouldReturnFullMonthWhenNoDaysExsist() {
		List<Date> result = calendar.fillCalendar(5, 2021);
		assertTrue(result.size() == 31);
	}

	@Test
	void shouldReturnCorrectDayCount() {
		assertTrue(Calendar.getNumberOfDays(5, 2021) == 31);
		assertTrue(Calendar.getNumberOfDays(4, 2021) == 30);
		assertTrue(Calendar.getNumberOfDays(2, 2020) == 29);
	}

	@Test
	void shouldReturnCorrectLastDayOfPreviousMounth() {
		assertEquals(6, Calendar.getWeekday(1, 5, 2021));
		assertEquals(7, Calendar.getWeekday(1, 8, 2021));
		assertEquals(1, Calendar.getWeekday(1, 11, 2021));
		assertEquals(3, Calendar.getWeekday(1, 1, 2020));
		assertEquals(6, Calendar.getWeekday(1, 2, 2020));
		assertEquals(7, Calendar.getWeekday(1, 3, 2020));

	}

}