package com.foxminded.university.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.foxminded.university.dao.TestHelper;
import com.foxminded.university.entities.Calendar;
import com.foxminded.university.entities.Course;
import com.foxminded.university.entities.Group;
import com.foxminded.university.entities.Lecture;
import com.foxminded.university.entities.Person;
import com.foxminded.university.entities.Role;
import com.foxminded.university.entities.personDetails.Student;
import com.foxminded.university.entities.personDetails.Teacher;
import com.foxminded.university.forms.formatters.LectureFormFormatter;
import com.foxminded.university.services.CourseService;
import com.foxminded.university.services.GroupService;
import com.foxminded.university.services.PersonService;
import com.foxminded.university.services.ScheduleService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ContextConfiguration(classes = { TestHelper.class })
public class ScheduleControllerTest {

	@MockBean
	private ScheduleService scheduleService;
	@MockBean
	private PersonService personService;
	@MockBean
	private CourseService courseService;
	@MockBean
	private GroupService groupService;
	@MockBean
	private LectureFormFormatter letureFormatter;

	@Autowired
	private Calendar calendar;
	
	MockMvc mockMvc;
	
	private Group g1;
	private Student s1;
	private Teacher teacher1;
	private Course c1;

	@BeforeEach
	void setup(WebApplicationContext wac) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

		List<Person> listTeachers = new ArrayList<Person>();
		teacher1 = new Teacher(2, "testNameTeacher", "passTeacher", "testFaculty", Role.TEACHER, "docent");
		listTeachers.add(teacher1);
		Mockito.when(personService.findTeachers()).thenReturn(listTeachers);

		c1 = new Course();
		c1.setCourseId(1);
		c1.setCourseName("cName");
		c1.setCourseDescription("cDesc");
		List<Course> listCourses = new ArrayList<Course>();
		Mockito.when(courseService.findAll()).thenReturn(listCourses);

		g1 = new Group();
		g1.setId(1);
		g1.setGroupName("gname");
		List<Group> listGroups = new ArrayList<Group>();
		listGroups.add(g1);
		Mockito.when(groupService.findAll()).thenReturn(listGroups);

		List<Person> listPersons = new ArrayList<Person>();
		s1=new Student(1, "s1", "password", "testFaculty", Role.STUDENT, g1);
		listPersons.add(s1);
		listPersons.add(teacher1);
		Mockito.when(personService.findAll()).thenReturn(listPersons);
		Mockito.when(personService.find(1)).thenReturn(Optional.of(s1));
		Mockito.when(personService.find(2)).thenReturn(Optional.of(teacher1));

		Lecture l1 = new Lecture();
		l1.setLectureId(1);
		l1.setTimeSlot(1);
		l1.setAudience(1);
		l1.setCourse(c1);
		l1.setGroup(g1);
		l1.setTeacher(teacher1);
		l1.setWeek(1);
		l1.setWeekday(DayOfWeek.TUESDAY);
		
		Lecture l2 = new Lecture();
		l2.setLectureId(2);
		l2.setTimeSlot(2);
		l2.setAudience(2);
		l2.setCourse(c1);
		l2.setGroup(g1);
		l2.setTeacher(teacher1);
		l2.setWeek(1);
		l2.setWeekday(DayOfWeek.TUESDAY);
		List<Lecture> lectures=new ArrayList<Lecture>();
		lectures.add(l1);
		lectures.add(l2);
		Mockito.when(scheduleService.findAll()).thenReturn(lectures);
		Mockito.when(scheduleService.findForTeacherOnWeek(2,1)).thenReturn(lectures);
		Mockito.when(scheduleService.findForStudentOnDay(g1,DayOfWeek.TUESDAY)).thenReturn(lectures);
	}

	@Test
	void shouldProcessWhenFindForAll() throws Exception {
		this.mockMvc.perform(get("/schedule"))
			.andExpect(status().isOk())
			.andExpect(content().contentType("text/html;charset=UTF-8"))
			.andExpect(model().attribute("schedule", scheduleService.findAll()))
			.andExpect(model().attribute("persons", personService.findAll()))
			.andExpect(model().attribute("daysOfWeek", calendar.getDayList()));
	}
	
	@Test
	void shouldProcessWhenFindForTeacherForWeek() throws Exception {
		this.mockMvc.perform(get("/schedule/forWeek/1/2"))
			.andExpect(status().isOk())
			.andExpect(content().contentType("text/html;charset=UTF-8"))
			.andExpect(model().attribute("schedule", scheduleService.findAll()))
			.andExpect(model().attribute("persons", personService.findAll()))
			.andExpect(model().attribute("daysOfWeek", calendar.getDayList()));
		verify(scheduleService,times(1)).findForTeacherOnWeek(Mockito.anyInt(),Mockito.anyInt());
	}
	
	@Test
	void shouldProcessWhenFindForStudentOnDay() throws Exception {
		this.mockMvc.perform(get("/schedule/forDay/TUESDAY/1"))
			.andExpect(status().isOk())
			.andExpect(content().contentType("text/html;charset=UTF-8"))
			.andExpect(model().attribute("schedule", scheduleService.findAll()))
			.andExpect(model().attribute("persons", personService.findAll()))
			.andExpect(model().attribute("daysOfWeek", calendar.getDayList()));
		verify(scheduleService,times(1)).findForStudentOnDay(Mockito.any(),Mockito.any());
	}

	@Test
	void shouldProcessWhenDeleteOne() throws Exception {
		this.mockMvc.perform(get("/schedule/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/schedule"));
		verify(scheduleService, times(1)).delete(1);
	}
	
	@Test
	void shouldProcessWhenSaveLecture() throws Exception {
		Lecture lectureSave = new Lecture();
		lectureSave.setLectureId(3);
		lectureSave.setTimeSlot(2);
		lectureSave.setAudience(2);
		lectureSave.setCourse(c1);
		lectureSave.setGroup(g1);
		lectureSave.setTeacher(teacher1);
		lectureSave.setWeek(1);
		lectureSave.setWeekday(DayOfWeek.TUESDAY);
		this.mockMvc
				.perform(post("/schedule/save")
						.param("lectureId", lectureSave.getLectureId().toString())
						.param("timeSlot", "2")
						.param("teacherId", lectureSave.getTeacher().getId().toString())
						.param("courseId", lectureSave.getCourse().getCourseId().toString())
						.param("groupId", lectureSave.getGroup().getId().toString())
						.param("roomNumber", lectureSave.getAudience().toString())
						.param("week", lectureSave.getWeek().toString())
						.param("weekday", lectureSave.getWeekday().toString()))
				.andExpect(status().is3xxRedirection());
		verify(letureFormatter, times(1)).getLecture(Mockito.any());
		verify(scheduleService, times(1)).save(Mockito.any());
	}

}