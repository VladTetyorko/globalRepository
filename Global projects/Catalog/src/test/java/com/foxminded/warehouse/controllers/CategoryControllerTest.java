package com.foxminded.warehouse.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.foxminded.warehouse.entities.Category;
import com.foxminded.warehouse.services.CategoryService;

@SpringBootTest
class CategoryControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private CategoryService categoryService;

	private Category category;

	@BeforeEach
	void setup(WebApplicationContext wac) {
		category = new Category();
		category.setId(1);
		category.setName("categoryName");
		Mockito.when(categoryService.findByName(Mockito.any())).thenReturn(Optional.of(category));
		Mockito.when(categoryService.save(Mockito.any())).thenReturn(category);

		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	final void shouldGetAllCategories() throws Exception {
		List<Category> list = new ArrayList<Category>();
		Mockito.when(categoryService.findAll()).thenReturn(list);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/warehouse/categories")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		verify(categoryService, times(1)).findAll();
	}

	@Test
	final void shouldPostCategory() throws Exception {
		Category category = new Category();
		category.setName("categoryName");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/warehouse/categories")
				.accept(MediaType.APPLICATION_JSON).content("{\"name\":\"category22\"}")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		verify(categoryService, times(1)).save(Mockito.any());
	}

	@Test
	final void shouldPutCategory() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/warehouse/categories/categoryName")
				.accept(MediaType.APPLICATION_JSON).content("{\"name\":\"categoryName1\"}")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		verify(categoryService, times(1)).findByName("categoryName");
		verify(categoryService, times(1)).save(Mockito.any());
	}

	@Test
	final void shouldDeleteCategory() throws Exception {
		Mockito.when(categoryService.delete(1)).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/warehouse/categories/categoryName")
				.accept(MediaType.APPLICATION_JSON).content("{\"name\":\"categoryName\"}")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		verify(categoryService, times(1)).delete(1);
	}

}
