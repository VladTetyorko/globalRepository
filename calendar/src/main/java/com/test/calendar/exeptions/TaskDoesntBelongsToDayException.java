package com.test.calendar.exeptions;

import com.test.calendar.entities.Date;

public class TaskDoesntBelongsToDayException extends RuntimeException {
	private String name;
	private Date date;

	public TaskDoesntBelongsToDayException(String name, Date date) {
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
