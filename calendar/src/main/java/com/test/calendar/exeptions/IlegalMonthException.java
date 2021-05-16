package com.test.calendar.exeptions;

public class IlegalMonthException extends CalendarException {

	private int month;
	private int year;

	public IlegalMonthException(int month, int year) {
		super("Incorrect month");
		this.month = month;
		this.year = year;
	}

	@Override
	public String getMessage() {
		return String.format("Illegal month with parameters year=%d, month=%d", year, month);
	}

}
