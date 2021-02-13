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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.foxminded.university.dao.TestHelper;
import com.foxminded.university.entities.Group;
import com.foxminded.university.entities.Person;
import com.foxminded.university.entities.Role;
import com.foxminded.university.entities.personDetails.Teacher;
import com.foxminded.university.services.GroupService;
import com.foxminded.university.services.PersonService;

@SpringBootTest
@ContextConfiguration(classes = { TestHelper.class })
public class GroupControllerTest {

	@MockBean
	private GroupService groupService;

	@MockBean
	private PersonService personService;

	private MockMvc mockMvc;

	private ArrayList<Group> list = new ArrayList<Group>();

	@BeforeEach
	void setup(WebApplicationContext wac) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		Group g1 = new Group();
		g1.setId(1);
		g1.setGroupName("testName");

		Group g2 = new Group();
		g2.setId(2);
		g2.setGroupName("testName2");
		list.add(g1);
		list.add(g2);
		Person admin = new Teacher(0, "S1", "pass1", "developing", Role.TEACHER, "");
		Mockito.when(personService.find(5)).thenReturn(Optional.of(admin));
		Mockito.when(groupService.findAll()).thenReturn(list);
		Mockito.when(groupService.find(1)).thenReturn(Optional.of(list.get(1)));
	}

	@Test
	void shouldProcessWhenFindAll() throws Exception {
		this.mockMvc.perform(get("/groups")).andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(model().attribute("groups", list));
	}

	@Test
	void shouldProcessWhenDeleteOne() throws Exception {
		this.mockMvc.perform(get("/groups/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/groups"));

		verify(groupService, times(1)).delete(1);
	}

	@Test
	void shouldProcessWhenSaveOne() throws Exception {
		Group saveGroup = new Group();
		saveGroup.setId(12);
		saveGroup.setGroupName("GG-11");
		this.mockMvc.perform(post("/groups/save").param("Id", saveGroup.getId().toString()).param("groupName",
				saveGroup.getGroupName())).andExpect(status().is3xxRedirection());
		verify(groupService, times(1)).save(Mockito.any());
	}

}