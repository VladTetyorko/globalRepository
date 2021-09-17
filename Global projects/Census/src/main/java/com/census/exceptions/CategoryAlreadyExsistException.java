package com.census.exceptions;

public class CategoryAlreadyExsistException extends AlreadyExsistException {

	private String message;

	public CategoryAlreadyExsistException(String name) {
		super(name);
	}

	@Override
	public String getMessage() {
		return String.format("Category with name %s already exsists", super.getName());
	}
}
