package com.test.calendar.exeptions;

import com.test.calendar.entities.DateEntity;

public class TaskDoesntBelongsToDayException extends RuntimeException {
	private String name;
	private DateEntity date;

	public TaskDoesntBelongsToDayException(String name, DateEntity date) {
		super(String.format("This task \"%s\" doesnt belongs to date: %d %s,%d", name, date.getDay(),
				date.getStringMonth(), date.getYear()));
		this.date = date;
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("This task= %s doesnt belongs to date:on %d %s,%d", name, date.getDay(),
				date.getStringMonth(), date.getYear());
	}

}
