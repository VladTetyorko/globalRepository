package com.foxminded.university.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.foxminded.university.entities.Group;

@SpringBootTest
public class GroupNameValidatorTest {
	@Autowired
	private Validator validator;

	@BeforeEach()
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void shouldGetValidationErrorWhenInvalidName() {
		Group group = new Group();
		group.setGroupName("GG11");
		Set<ConstraintViolation<Group>> violations = validator.validate(group);
		assertFalse(violations.isEmpty());
	}

	@Test
	public void shouldGetSuccessWhenValidName() {
		Group group = new Group();
		group.setGroupName("GG-11");
		Set<ConstraintViolation<Group>> violations = validator.validate(group);
		assertTrue(violations.isEmpty());
	}
}
