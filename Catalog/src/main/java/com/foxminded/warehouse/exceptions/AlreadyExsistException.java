package com.foxminded.warehouse.exceptions;

public class AlreadyExsistException extends RuntimeException {

	String objectName;

	public AlreadyExsistException(String objectName) {
		this.objectName = objectName;
	}

	@Override
	public String getMessage() {
		return this.objectName + " already exsist";
	}

}
