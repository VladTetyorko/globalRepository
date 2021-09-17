package com.foxminded.university.dao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.foxminded.university.DAOs.ScheduleRepository;
import com.foxminded.university.entities.Course;
import com.foxminded.university.entities.Group;
import com.foxminded.university.entities.Lecture;
import com.foxminded.university.entities.Role;
import com.foxminded.university.entities.personDetails.Teacher;

@DataJpaTest

class ScheduleDAOTest {

	@Autowired
	private ScheduleRepository sheduleDAO;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	static Group testGroup = new Group();
	static Course testCourse = new Course();
	static Teacher testTeacher = new Teacher(1, "Teacher", "pass", "PM", Role.TEACHER, "docent");

	@Test
	final void shouldSaveWhenInsert() {
		testGroup.setId(1);
		testGroup.setGroupName("g1");

		testCourse.setCourseId(1);
		testCourse.setCourseName("insertName");

		jdbcTemplate.execute("Delete from persons;" + "Delete from Courses;" + "Delete from UNIVERSITY_GROUPS;"
				+ "Insert INTO PERSONS(PERSON_ID,NAME,USERS_PASSWORD,FACULTY,ROLE) VALUES(1,'n','p','f','TEACHER');"
				+ "INSERT INTO Teachers(PERSON_ID,Academic_Degree) VALUES(1,'insertNAME');"
				+ "INSERT INTO Courses(Course_id,Course_name) VALUES(1,'insertNAME');"
				+ "INSERT INTO UNIVERSITY_GROUPS(GROUP_ID,Group_Name) VALUES(1,'g1');");

		Lecture lecture1 = new Lecture();
		lecture1.setAudience(1);
		lecture1.setCourse(testCourse);
		lecture1.setGroup(testGroup);
		lecture1.setTeacher(testTeacher);
		lecture1.setTimeSlot(1);
		lecture1.setWeek(1);
		lecture1.setWeekday(DayOfWeek.MONDAY);

		Lecture saved1 = new Lecture();
		saved1 = sheduleDAO.save(lecture1);
		assertTrue(sheduleDAO.count() > 0);
		assertTrue(saved1.getLectureId() != null);

	}

	@Test
	final void shouldProcessWhenFindById() {
		jdbcTemplate.execute("Delete from persons;" + "Delete from Lectures;" + "Delete from Courses;"
				+ "Delete from UNIVERSITY_GROUPS;"
				+ "Insert INTO PERSONS(PERSON_ID,NAME,USERS_PASSWORD,FACULTY,ROLE) VALUES(1,'n','p','f','TEACHER');"
				+ "INSERT INTO Teachers(PERSON_ID,Academic_Degree) VALUES(1,'insertNAME');"
				+ "INSERT INTO Courses(Course_id,Course_name) VALUES(1,'insertNAME');"
				+ "INSERT INTO UNIVERSITY_GROUPS(GROUP_ID,Group_Name) VALUES(1,'g1');"
				+ "Insert Into LECTURES(LECTURE_ID,TIMESLOT,TEACHER_ID, COURSE_ID, GROUP_ID, ROOM_NUMBER, WEEK, WEEKDAY) "
				+ " VALUES (1,1,1,1,1,1,1, 'WEDNESDAY');");

		Optional<Lecture> shouldBeFound = sheduleDAO.findById(1);
		Lecture found = shouldBeFound.get();
		assertTrue(found.getLectureId() == 1);
		assertTrue(found.getWeekday() == DayOfWeek.WEDNESDAY);

		Optional<Lecture> shouldNotBeFound = sheduleDAO.findById(4);
		assertFalse(shouldNotBeFound.isPresent());
	}

	@Test
	final void shouldProcessWhenDeleteById() {
		jdbcTemplate.execute("Delete from persons;" + "Delete from LECTURES;" + "Delete from Courses;"
				+ "Delete from UNIVERSITY_GROUPS;"
				+ "Insert INTO PERSONS(PERSON_ID,NAME,USERS_PASSWORD,FACULTY,ROLE) VALUES(1,'n','p','f','TEACHER');"
				+ "INSERT INTO Teachers(PERSON_ID,Academic_Degree) VALUES(1,'insertNAME');"
				+ "INSERT INTO Courses(Course_id,Course_name) VALUES(1,'insertNAME');"
				+ "INSERT INTO UNIVERSITY_GROUPS(GROUP_ID,Group_Name) VALUES(1,'g1');"
				+ "Insert Into LECTURES(LECTURE_ID,TIMESLOT,TEACHER_ID, COURSE_ID, GROUP_ID, ROOM_NUMBER, WEEK, WEEKDAY) "
				+ "VALUES (1,1,1,1,1,1,1, 'MONDAY')");
		sheduleDAO.deleteById(1);
		assertThrows(EmptyResultDataAccessException.class, () -> sheduleDAO.deleteById(1));
	}

}
