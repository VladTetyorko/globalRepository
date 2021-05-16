package com.test.calendar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.test.calendar.entities.Date;

public class Calendar {

	private final static int DAYS_OF_WEEK[] = { 7, 1, 2, 3, 4, 5, 6 };
	private final static int CENTURY_YEAR_OVERSIGHT[] = { 0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5 };

	public final static String MONTHS[] = { "January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };

	private static boolean ifIsLap(int year) {
		if (year % 400 == 0)
			return true;
		else if (year % 100 == 0)
			return false;
		else if (year % 4 == 0)
			return true;
		return false;
	}

	private static int getYearOversight(int yyyy) {
		int j;
		if ((yyyy / 100) % 2 == 0) {
			if ((yyyy / 100) % 4 == 0)
				j = 6;
			else
				j = 2;
		} else {
			if (((yyyy / 100) - 1) % 4 == 0)
				j = 4;
			else
				j = 0;
		}
		return j;
	}

	public static int getWeekday(int day, int month, int year) {
		int dayNumber = 0;
		int total = (year % 100) + ((year % 100) / 4) + day + CENTURY_YEAR_OVERSIGHT[month - 1]
				+ getYearOversight(year);
		if (ifIsLap(year)) {
			if ((total % 7) > 0)
				dayNumber = DAYS_OF_WEEK[(total % 7) - 1];
			else
				dayNumber = DAYS_OF_WEEK[6];
			if (month > 2)
				dayNumber++;
		} else
			dayNumber = DAYS_OF_WEEK[(total % 7)];
		return dayNumber;
	}

	public static int getNumberOfDays(int month, int year) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 2:
			if (ifIsLap(year))
				return (29);
			else
				return (28);
		case 4:
		case 6:
		case 9:
		case 11:
			return (30);
		}
		return 0;
	}

	public static String getStringMonth(int month) {
		return MONTHS[month - 1];
	}

	public List<Date> fillCalendar(List<Date> inputDates) {
		Optional<Date> example = inputDates.stream().findFirst();
		int dayCount = getNumberOfDays(example.get().getMonth(), example.get().getYear());

		Map<Integer, Date> datesMap = (HashMap<Integer, Date>) inputDates.stream()
				.collect(Collectors.toMap(Date::getDay, date -> date));

		for (int i = 1; i <= dayCount; i++) {
			if (datesMap.get(i) == null) {
				datesMap.put(i, new Date(i, example.get().getMonth(), example.get().getYear()));
			}
		}
		return datesMap.values().stream().collect(Collectors.toList());
	}

	public List<Date> fillCalendar(int month, int year) {
		Map<Integer, Date> datesMap = new HashMap<Integer, Date>();
		int dayCount = getNumberOfDays(month, year);
		for (int i = 1; i <= dayCount; i++) {
			datesMap.put(i, new Date(i, month, year));
		}
		return datesMap.values().stream().collect(Collectors.toList());
	}

}
