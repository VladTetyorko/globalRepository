package com.test.calendar.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.test.calendar.entities.Date;
import com.test.calendar.entities.Task;
import com.test.services.DateService;
import com.test.services.TaskService;

@SpringBootTest
class TaskControllerTest {
	@MockBean
	private DateService dateService;
	@MockBean
	private TaskService taskService;

	private MockMvc mockMvc;

	private Date date;

	private Task task;

	@BeforeEach
	void setup(WebApplicationContext wac) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		date = new Date(1, 5, 2020);
		task = new Task("test1", "14:00", date);
		task.setID(1);
		Mockito.when(dateService.findDay(1, 5, 2020)).thenReturn(date);
		Mockito.when(taskService.find(1)).thenReturn(Optional.of(task));

	}

	@Test
	void shouldProcessWhenFindOneDay() throws Exception {
		this.mockMvc.perform(get("/calendar/2020/5/1")).andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(model().attribute("date", date));
	}

	@Test
	void shouldProcessWhenFindOneTaskInDay() throws Exception {
		date.setID(1);
		task.setID(1);
		this.mockMvc.perform(get("/calendar/2020/5/1/editTask/1")).andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(model().attribute("task", task));
	}

	@Test
	void saveWhenSaveTask() throws Exception {
		Task saveTask = new Task("18:00", "test name", date);
		this.mockMvc.perform(post("/save/task").param("name", saveTask.getName()).param("time", saveTask.getTime()))
				.andExpect(status().is3xxRedirection());
		verify(taskService, times(1)).save(Mockito.any());
	}

	@Test
	void shouldProcessWhenDeleteOne() throws Exception {
		this.mockMvc.perform(get("/calendar/2020/5/1/deleteTask/1")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));
		verify(taskService, times(1)).delete(1);
	}

}
