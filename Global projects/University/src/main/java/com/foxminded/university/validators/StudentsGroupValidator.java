package com.foxminded.university.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxminded.university.services.GroupService;

@Component
public class StudentsGroupValidator implements ConstraintValidator<StudentsGroupConstraint, Integer> {

	@Autowired
	GroupService groupServise;

	@Override
	public void initialize(StudentsGroupConstraint studentGroup) {
	}

	@Override
	public boolean isValid(Integer contactField, ConstraintValidatorContext cxt) {
		System.out.print(groupServise.find(1) + "\n\n\n");

		if (groupServise.find(contactField.intValue()).get().countStudents() < 2)
			return true;
		else
			return false;
	}

}
