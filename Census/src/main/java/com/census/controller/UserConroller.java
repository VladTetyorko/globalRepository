package com.census.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.census.entities.User;
import com.census.services.UserService;

@Controller
public class UserConroller {

	private final UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(UserConroller.class.getName());

	public UserConroller(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public String getCurrentUserInfo(Model model) {
		logger.info("Html page= /me");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			User user = userService.findUserByUsername(((UserDetails) principal).getUsername()).get();
			model.addAttribute("loginedPerson", user);
			model.addAttribute("pages", 1);
		}
		return "user";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "create", required = false) String create, Model model) {
		String errorMessge = null;
		if (error != null) {
			errorMessge = "Username or Password is incorrect !!";
		}
		if (logout != null) {
			errorMessge = "You have been successfully logged out !!";
		}
		if (create != null) {
			errorMessge = "Please contact the administrator";
		}
		model.addAttribute("errorMessge", errorMessge);
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout=true";
	}

	public User getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUserByUsername(((UserDetails) principal).getUsername()).get();
		return user;
	}

}
