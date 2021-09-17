package com.itrans.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itrans.entities.User;
import com.itrans.entities.UserForm;
import com.itrans.services.UserService;

@Controller
public class UserController {

	private final UserService service;

	private final SessionUtils utils;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class.getName());

	public UserController(UserService service, SessionUtils utils) {
		this.service = service;
		this.utils = utils;
	}

	@RequestMapping("/")
	public String showUserTable(Model model) {
		List<User> users = service.findAll();
		model.addAttribute("userList", users);
		return "index";
	}

	@RequestMapping(value = "/user/save", method = RequestMethod.POST)
	public String saveCategory(Model model, @Valid User user, BindingResult bindingResult) {
		logger.info("Html page= /save");
		if (bindingResult.hasErrors()) {
			logger.warn("Got errors {}", bindingResult.getAllErrors());
			return "editItem";
		}
		try {
			service.save(user);
			return "redirect:/";
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getLocalizedMessage());
			return "user";
		}
	}

	@RequestMapping(value = "/user/new", method = RequestMethod.GET)
	public String saveCategory(Model model) {
		logger.info("Html page= /user/new");
		UserForm user = new UserForm();
		user.setIsActive(true);
		model.addAttribute("user", user);
		return "user";
	}

	@RequestMapping(path = "/delete", method = RequestMethod.GET)
	public String deleteUsers(@RequestParam(name = "id") List<Integer> idList) {
		idList.forEach(id -> expireSessions(service.delete(id)));
		return "redirect:/";
	}

	@RequestMapping(path = "/block", method = RequestMethod.GET)
	public String blockUsers(@RequestParam(name = "id") List<Integer> idList) {
		idList.forEach(id -> expireSessions(service.block(id)));
		return "redirect:/";
	}

	@RequestMapping(path = "/unblock", method = RequestMethod.GET)
	public String unblockUsers(@RequestParam(name = "id", required = true) List<Integer> idList) {
		idList.forEach(id -> service.unblock(id));
		return "redirect:/";
	}

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String loginPage(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "create", required = false) String create, Model model) {
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

	private void expireSessions(String username) {
		utils.expireUserSessions(username);
	}

}
