package com.test.services;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.test.repositories.DateRepository;
import com.test.services.DateService;

class DateServiceTest {

	@MockBean
	private DateRepository repository;

	private DateService service;

	@Test
	final void shouldThrowIllegalDayeExceptionWhenFindIlligalDate() {
		assertThrows(NullPointerException.class, () -> service.findDay(29, 2, 2021));
		assertThrows(NullPointerException.class, () -> service.findDay(30, 2, 2020));
//rebuild!
	}

}
