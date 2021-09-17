package com.census.exceptions;

public class LocationAlreadyExsistException extends AlreadyExsistException {

	public LocationAlreadyExsistException(String name) {
		super(name);
	}

	@Override
	public String getMessage() {
		return String.format("Location with name %s already exsists", this.getName());
	}
}
