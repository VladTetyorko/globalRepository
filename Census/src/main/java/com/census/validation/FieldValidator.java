package com.census.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldValidator implements ConstraintValidator<FieldConstraint, String> {

	@Override
	public void initialize(FieldConstraint name) {
	}

	@Override
	public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
		return !(contactField == null || contactField == "");
	}

}