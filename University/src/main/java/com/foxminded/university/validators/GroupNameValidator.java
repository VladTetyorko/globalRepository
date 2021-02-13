package com.foxminded.university.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GroupNameValidator implements ConstraintValidator<GroupNameConstraint, String> {

	@Override
	public void initialize(GroupNameConstraint groupName) {
	}

	@Override
	public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
		return Character.isUpperCase(contactField.charAt(0)) && Character.isUpperCase(contactField.charAt(1))
				&& contactField.charAt(2) == '-' && Character.isDigit(contactField.charAt(3))
				&& Character.isDigit(contactField.charAt(4));
	}

}