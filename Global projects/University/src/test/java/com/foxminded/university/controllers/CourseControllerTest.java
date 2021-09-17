package com.foxminded.university.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.foxminded.university.entities.Course;
import com.foxminded.university.entities.Person;
import com.foxminded.university.entities.Role;
import com.foxminded.university.entities.personDetails.Teacher;
import com.foxminded.university.services.CourseService;
import com.foxminded.university.services.PersonService;

@SpringBootTest
public class CourseControllerTest {

	@MockBean
	private CourseService courseService;

	@MockBean
	private PersonService personService;

	private MockMvc mockMvc;

	private ArrayList<Course> list = new ArrayList<Course>();

	@BeforeEach
	void setup(WebApplicationContext wac) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		Course c1 = new Course();
		c1.setCourseId(1);
		c1.setCourseName("testName");
		c1.setCourseDescription("testDesk1");

		Course c2 = new Course();
		c2.setCourseId(2);
		c2.setCourseName("testName2");
		c2.setCourseDescription("testDesk2");

		list.add(c1);
		list.add(c2);
		Person admin = new Teacher(0, "S1", "pass1", "developing", Role.TEACHER, "");
		Mockito.when(personService.find(5)).thenReturn(Optional.of(admin));
		Mockito.when(courseService.findAll()).thenReturn(list);
		Mockito.when(courseService.find(1)).thenReturn(Optional.of(list.get(1)));
	}

	@Test
	void shouldProcessWhenFindAll() throws Exception {
		this.mockMvc.perform(get("/courses")).andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(model().attribute("courses", list));
	}

	@Test
	void shouldProcessWhenDeleteOne() throws Exception {
		this.mockMvc.perform(get("/courses/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/courses"));
		verify(courseService, times(1)).delete(1);
	}

	@Test
	void shouldProcessWhenSaveOne() throws Exception {
		Course saveGroup = new Course();
		saveGroup.setCourseId(12);
		saveGroup.setCourseName("cTest");
		saveGroup.setCourseDescription("testDesc");
		this.mockMvc.perform(post("/courses/save").param("courseId", saveGroup.getCourseId().toString())
				.param("courseName", saveGroup.getCourseName()).param("courseDes", saveGroup.getCourseDescription()))
				.andExpect(status().is2xxSuccessful());
		verify(courseService, times(1)).save(Mockito.any());
	}

}