package com.test.calendar.exeptions;

public class IlegalDateException extends CalendarException {
	private final int day;
	private final int month;
	private final int year;

	public IlegalDateException(int day, int month, int year) {
		super("Incorrecd day");
		this.day = day;
		this.month = month;
		this.year = year;
	}

	@Override
	public String getMessage() {
		return String.format("Illegal date with year=%d, month=%d, day=%d", year, month, day);
	}

}
