package com.census.exceptions;

public class SQLUnexpectedException extends DatabaseException {

	private String message;

	public SQLUnexpectedException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return String.format("Unexpected message=%s", this.message);
	}

}
