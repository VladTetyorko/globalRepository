package com.test.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.test.calendar.entities.DateEntity;

@Component
public class CalendarImpl {

	public final static String MONTHS[] = { "January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };

	public static int getWeekday(int day, int month, int year) {
		LocalDate date = LocalDate.of(year, month, day);
		DayOfWeek dayOfWeek = DayOfWeek.from(date);
		return dayOfWeek.getValue();
	}

	public static int getNumberOfDays(int month, int year) {
		YearMonth yearMonthObject = YearMonth.of(year, month);
		return yearMonthObject.lengthOfMonth();
	}

	public static String getStringMonth(int month) {
		return MONTHS[month - 1];
	}

	public List<DateEntity> fillCalendar(List<DateEntity> inputDates) {
		Optional<DateEntity> example = inputDates.stream().findFirst();
		int dayCount = getNumberOfDays(example.get().getMonth(), example.get().getYear());

		Map<Integer, DateEntity> datesMap = (HashMap<Integer, DateEntity>) inputDates.stream()
				.collect(Collectors.toMap(DateEntity::getDay, date -> date));

		for (int i = 1; i <= dayCount; i++) {
			if (datesMap.get(i) == null) {
				datesMap.put(i, new DateEntity(i, example.get().getMonth(), example.get().getYear()));
			}
		}
		return datesMap.values().stream().collect(Collectors.toList());
	}

	public List<DateEntity> fillCalendar(int month, int year) {
		Map<Integer, DateEntity> datesMap = new HashMap<Integer, DateEntity>();
		int dayCount = getNumberOfDays(month, year);
		for (int i = 1; i <= dayCount; i++) {
			datesMap.put(i, new DateEntity(i, month, year));
		}
		return datesMap.values().stream().collect(Collectors.toList());
	}
}
