package com.foxminded.university.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.foxminded.university.entities.Group;
import com.foxminded.university.services.GroupService;

@SpringBootTest
public class StudentGroupValidatorTest {

	@MockBean
	private GroupService groupServise;

	@MockBean
	Group group;

	@Autowired
	private StudentsGroupValidator validator;

	@Test
	public void shouldPassWhenLessThenTwoStudents() {
		Mockito.when(groupServise.find(Integer.valueOf(1))).thenReturn(Optional.ofNullable(group));
		Mockito.when(group.countStudents()).thenReturn(1);

		boolean violations = validator.isValid(1, null);
		System.out.print(violations + "\n\n\n\n");
		assertTrue(violations);
	}

	@Test
	public void shouldNotPassWhenTwoStudents() {
		Mockito.when(groupServise.find(Integer.valueOf(1))).thenReturn(Optional.ofNullable(group));
		Mockito.when(group.countStudents()).thenReturn(2);

		boolean violations = validator.isValid(1, null);
		System.out.print(violations + "\n\n\n\n");
		assertFalse(violations);
	}

}
