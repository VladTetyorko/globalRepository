package com.foxminded.university.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foxminded.university.entities.Group;
import com.foxminded.university.services.GroupService;
import com.foxminded.university.services.PersonService;

@Controller
public class GroupController {
	@Autowired
	private GroupService groupServise;
	@Autowired
	private PersonService personService;

	@RequestMapping(value = { "/groups" }, method = RequestMethod.GET)
	public String groups(Model model) {
		model.addAttribute("loginedPerson", personService.find(5).get());
		model.addAttribute("groups", groupServise.findAll());
		return "groups";
	}

	@RequestMapping(path = "groups/save", method = RequestMethod.POST)
	public String saveGroups(@Valid Group group, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "editGroups";
		}
		groupServise.save(group);
		return "redirect:/groups";
	}

	@RequestMapping(value = { "/groups/createGroup" }, method = RequestMethod.GET)
	public String createGroups(Model model) {
		model.addAttribute("group", new Group());
		return "editGroups";
	}

	@RequestMapping(value = { "/groups/editGroups/{id}" }, method = RequestMethod.GET)
	public String editGroups(Model model, @PathVariable(value = "id") int id) {
		model.addAttribute("group", groupServise.find(id));
		return "editGroups";
	}

	@RequestMapping(value = { "/groups/delete/{id}" }, method = RequestMethod.GET)
	public String groupsDelete(Model model, @PathVariable(value = "id") int id) {
		groupServise.delete(id);
		return "redirect:/groups";
	}
}
