package com.census.exceptions;

public class AlreadyExsistException extends DatabaseException {

	private String name;

	public AlreadyExsistException(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
