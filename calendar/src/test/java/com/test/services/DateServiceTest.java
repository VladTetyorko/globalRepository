package com.test.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.context.WebApplicationContext;

import com.test.calendar.entities.Date;
import com.test.calendar.exeptions.IlegalDateException;
import com.test.calendar.exeptions.IlegalMonthException;
import com.test.repositories.DateRepository;

@SpringBootTest
class DateServiceTest {

	@MockBean
	private DateRepository repository;

	@Autowired
	private DateService service;

	private List<Date> list;

	@BeforeEach
	void setup(WebApplicationContext wac) {
		list = new ArrayList<Date>();
		Date d = new Date(1, 5, 2020);
		d.setID(1);
		Date d2 = new Date(6, 5, 2020);
		d2.setID(2);

		list.add(d);
		list.add(d2);

		Mockito.when(repository.findByMonthAndYear(5, 2020)).thenReturn(list);
		Mockito.when(repository.findByMonthAndYear(2, 2020)).thenReturn(new ArrayList<Date>());
		Mockito.when(repository.findByDayAndMonthAndYear(1, 5, 2020)).thenReturn(Optional.of(d));

	}

	@Test
	final void shouldThrowIllegalDateExceptionSWhenFindIlligalDate() {
		assertThrows(IlegalMonthException.class, () -> service.findMonth(13, 2020));
		assertThrows(IlegalDateException.class, () -> service.findDay(29, 2, 2021));
		assertThrows(IlegalDateException.class, () -> service.findDay(30, 2, 2020));
	}

	@Test
	final void shouldFindMonthWhenSomeDaysExsists() {
		assertTrue(service.findMonth(5, 2020).size() == 31);
		verify(repository, times(1)).findByMonthAndYear(5, 2020);
	}

	@Test
	final void shouldFindDayWhenDayExsist() {
		assertTrue(service.findDay(1, 5, 2020).getDay() == 1);
		verify(repository, times(1)).findByDayAndMonthAndYear(1, 5, 2020);
	}

}
