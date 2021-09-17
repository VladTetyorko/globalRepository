package com.foxminded.warehouse.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.foxminded.warehouse.entities.User;
import com.foxminded.warehouse.security.JwtProvider;
import com.foxminded.warehouse.services.UserService;

@SpringBootTest
public class UserControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	JwtProvider jwtProvider;

	private User user;

	private UserDetails userDetails;

	@BeforeEach
	void setup(WebApplicationContext wac) {
		UserBuilder builder = null;
		builder = org.springframework.security.core.userdetails.User.withUsername("user");
		builder.password("password");
		builder.roles("ADMIN");
		userDetails = builder.build();

		user = new User("user", "password");
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	final void shouldGetListOfUsers() throws Exception {
		List<Object> list = new ArrayList<Object>();
		list.add("Should be user list");
		Mockito.when(userService.findAllForAdmin()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/warehouse/admin/users")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		System.out.print("\n\n\n" + response.getContentAsString() + "\n\n");
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(0, response.getContentAsString().indexOf("[\"Should be user list\"]" + ""));
	}

	@Test
	final void shouldProcessWhenLogin() throws Exception {
		Mockito.when(userService.save(Mockito.any())).thenReturn(user);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/warehouse/login")
				.accept(MediaType.APPLICATION_JSON).content("{\"username\":\"user12\",\"password\":\"password\"}")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}

}
