package com.foxminded.warehouse.controllers;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.foxminded.warehouse.entities.AuthRequest;
import com.foxminded.warehouse.entities.AuthResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Controller")
public interface UserController {

	@Operation(summary = "Get list of all registrated users. Only for Admin")
	@GetMapping(path = "/api/v1/warehouse/admin/users")
	public List<Object> getAllUsers();

	@Operation(summary = "Get information about currently loggined user")
	@GetMapping(path = "/api/v1/warehouse/me")
	public UserDetails getCurrentUser();

	@Operation(summary = "Login and get token")
	@PostMapping("/api/v1/warehouse/login")
	public AuthResponse loginPost(@RequestBody AuthRequest request);

}
