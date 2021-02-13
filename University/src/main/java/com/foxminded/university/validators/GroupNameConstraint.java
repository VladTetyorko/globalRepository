package com.foxminded.university.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = GroupNameValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface GroupNameConstraint {
	String message() default "Invalid group name. It should be LL-NN. L-Litter, N-Number";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}