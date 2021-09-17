package com.foxminded.university.exeptions;

public class UniversityDaoException extends UniversityException {
	Object entity;

	public UniversityDaoException(String message, Object model) {
		super(message,model);
		this.entity=model;
	}
	
	@Override
	public String toString() {
		return String.format("\"%s\"  with argument:%n{%s}", message, entity.toString());
	}
}
