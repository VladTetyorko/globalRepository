package com.foxminded.warehouse.controllers.impl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.foxminded.warehouse.controllers.UserController;
import com.foxminded.warehouse.entities.AuthRequest;
import com.foxminded.warehouse.entities.AuthResponse;
import com.foxminded.warehouse.security.JwtProvider;
import com.foxminded.warehouse.services.UserService;

@RestController
public class UserControllerImpl implements UserController {

	private final UserService userService;

	private final JwtProvider jwtProvider;

	public UserControllerImpl(UserService userService, JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
		this.userService = userService;
	}

	@Override
	public List<Object> getAllUsers() {
		List<Object> users = userService.findAllForAdmin();
		return users;
	}

	@Override
	public UserDetails getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (UserDetails) auth.getPrincipal();
	}

	@Override
	public AuthResponse loginPost(@RequestBody AuthRequest request) {
		UserDetails user = userService.findByLoginAndPassword(request.getUsername(), request.getPassword());
		String token = jwtProvider.generateToken(request);
		return new AuthResponse(token);
	}

}