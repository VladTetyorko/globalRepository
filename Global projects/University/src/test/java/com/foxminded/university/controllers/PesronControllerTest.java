package com.foxminded.university.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.foxminded.university.dao.TestHelper;
import com.foxminded.university.entities.Group;
import com.foxminded.university.entities.Person;
import com.foxminded.university.entities.Role;
import com.foxminded.university.entities.personDetails.Student;
import com.foxminded.university.entities.personDetails.Teacher;
import com.foxminded.university.forms.formatters.StudentFormFormatter;
import com.foxminded.university.services.PersonService;

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

@SpringBootTest
@ContextConfiguration(classes = { TestHelper.class })
public class PesronControllerTest {

	@MockBean
	private PersonService personService;
	@MockBean
	private StudentFormFormatter studentFormater;
	
	private MockMvc mockMvc;

	
	private ArrayList<Person> list = new ArrayList<Person>();

	@BeforeEach
	void setup(WebApplicationContext wac) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		Group g=new Group();
		g.setId(1);
		g.setGroupName("gname");
		Person student1 = new Student(1, "testName",  "testPass", "testFaculty", Role.STUDENT, g);

		Person teacher1=new Teacher(2, "testNameTeacher", "passTeacher", "testFaculty", Role.TEACHER, "docent");

		list.add(student1);
		list.add(teacher1);
		Mockito.when(personService.findAll()).thenReturn(list);
		Mockito.when(personService.find(1)).thenReturn(Optional.of(list.get(0)));
		Mockito.when(personService.find(2)).thenReturn(Optional.of(list.get(1)));
		Mockito.when(personService.find(5)).thenReturn(Optional.of(list.get(1)));
	}

	@Test
	void shouldProcessWhenFindAll() throws Exception {
		this.mockMvc.perform(get("/persons")).andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(model().attribute("persons", list));
	}

	@Test
	void shouldProcessWhenDeleteOne() throws Exception {
		this.mockMvc.perform(get("/persons/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/persons"));
		verify(personService, times(1)).delete(1);
	}

	@Test
	void shouldProcessWhenSaveStudent() throws Exception {		
		this.mockMvc
				.perform(post("/persons/save/student")
						.param("id", "0")
						.param("name","testStudent")
						.param("password", "0")
						.param("faculty", "testFaculty")
						.param("groupId", "1"))					
				.andExpect(status().is3xxRedirection());
		verify(studentFormater,times(1)).getStudent(Mockito.any());
		verify(personService, times(1)).save(Mockito.any());
	}
	
	@Test
	void shouldProcessWhenSaveTeacher() throws Exception {
		this.mockMvc
		.perform(post("/persons/save/teacher")
				.param("id", "0")
				.param("name","testStudent")
				.param("password", "0")
				.param("faculty", "testFaculty")
				.param("academicDegree", "degree"))					
		.andExpect(status().is3xxRedirection());
		verify(personService, times(1)).save(Mockito.any());
	}

}