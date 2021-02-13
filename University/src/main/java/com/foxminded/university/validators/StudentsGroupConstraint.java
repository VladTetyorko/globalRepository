package com.foxminded.university.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = StudentsGroupValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StudentsGroupConstraint {

	String message() default "This group is full. It should be less than 15 students in one group";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
