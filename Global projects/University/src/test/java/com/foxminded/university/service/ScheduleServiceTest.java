package com.foxminded.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.foxminded.university.DAOs.ScheduleRepository;
import com.foxminded.university.entities.Course;
import com.foxminded.university.entities.Group;
import com.foxminded.university.entities.Lecture;
import com.foxminded.university.entities.Role;
import com.foxminded.university.entities.personDetails.Teacher;
import com.foxminded.university.exeptions.UniversityDaoSaveException;
import com.foxminded.university.services.ScheduleService;

@SpringBootTest
@EnableAutoConfiguration
public class ScheduleServiceTest {

	@MockBean
	private ScheduleRepository sheduleDAO;
	@Autowired
	private ScheduleService servise;

	private static Group testGroup = new Group();
	private static Course testCourse = new Course();
	private static Teacher testTeacher = new Teacher(0, "Teacher", "pass", "PM", Role.TEACHER, "docent");

	@BeforeAll
	final static void init() {
		testGroup.setGroupName("tG");
		testCourse.setCourseName("tC");
	}

	@Test
	final void shouldProcessWhenLegalDate() {
		Lecture legalLecture = new Lecture();
		legalLecture.setAudience(1);
		legalLecture.setCourse(testCourse);
		legalLecture.setGroup(testGroup);
		legalLecture.setTeacher(testTeacher);
		legalLecture.setTimeSlot(1);
		legalLecture.setWeek(1);
		legalLecture.setWeekday(DayOfWeek.MONDAY);
		Mockito.when(sheduleDAO.save(legalLecture)).then(new Answer<Lecture>() {
			@Override
			public Lecture answer(InvocationOnMock invocation) throws Throwable {
				legalLecture.setLectureId(1);
				return legalLecture;
			}
		});
		assertEquals(legalLecture, servise.save(legalLecture));
	}

	@Test
	final void shouldProcessLoggerTest() {
		Lecture testLecture = new Lecture();
		testLecture.setAudience(1);
		testLecture.setCourse(testCourse);
		testLecture.setGroup(testGroup);
		testLecture.setTeacher(testTeacher);
		testLecture.setWeekday(DayOfWeek.MONDAY);
		Mockito.when(sheduleDAO.save(testLecture))
				.thenThrow(new UniversityDaoSaveException("Failed when saving lecture", testLecture));
		servise.save(testLecture);
	}

}
