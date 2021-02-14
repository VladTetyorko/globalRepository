package com.test.calendar.exeptions;

public class IlegalDateException extends RuntimeException {
	private final int month;
	private final int year;

	public IlegalDateException(int month, int year) {
		this.month = month;
		this.year = year;
	}

	@Override
	public String getMessage() {
		return String.format("Illegal date with year=%d, month=%d", year, month);
	}

}
