package com.foxminded.university.DAOs;

import java.time.DayOfWeek;
import java.util.List;

import com.foxminded.university.entities.Group;
import com.foxminded.university.entities.Lecture;

public interface ScheduleRepositoryCustom {

	public List<Lecture> findByGroupAndWeekday(Group group, DayOfWeek weekday);

	public List<Lecture> findByGroupAndWeek(Group group, int week);

	public List<Lecture> findByTeacherAndWeekday(int teacherId, DayOfWeek weekday);

	public List<Lecture> findByTeacherAndWeek(int teaherId, int week);

	public List<Lecture> findByRoomNumberAndWeekday(int room, DayOfWeek weekday);

	public List<Lecture> findByRoomNumberAndWeek(int room, int week);
}
