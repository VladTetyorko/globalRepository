package com.foxminded.university.exeptions;

public class UniversityException extends RuntimeException {

	String message;
	
	public UniversityException(String message, Object model) {
		super(message);
		this.message=message;
	}
	
	
}
