package com.foxminded.university.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foxminded.university.entities.Person;
import com.foxminded.university.entities.Role;
import com.foxminded.university.entities.personDetails.Admin;
import com.foxminded.university.entities.personDetails.Student;
import com.foxminded.university.entities.personDetails.Teacher;
import com.foxminded.university.forms.StudentForm;
import com.foxminded.university.forms.formatters.StudentFormFormatter;
import com.foxminded.university.services.GroupService;
import com.foxminded.university.services.PersonService;

@Controller
public class PersonController {

	@Autowired
	private PersonService personService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private StudentFormFormatter studentFormFormater;

	@RequestMapping(value = { "/persons" }, method = RequestMethod.GET)
	public String persons(Model model) {
		model.addAttribute("loginedPerson", personService.find(5).get());
		model.addAttribute("persons", personService.findAll());
		return "persons";
	}

	@RequestMapping(path = "persons/save/student", method = RequestMethod.POST)
	public String saveStudent(Model model, @Valid @ModelAttribute("person") StudentForm person,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("groups", groupService.findAll());
			return "editStudent";
		}
		personService.save(studentFormFormater.getStudent(person));
		return "redirect:/persons";
	}

	@RequestMapping(path = "persons/save/teacher", method = RequestMethod.POST)
	public String saveTeacher(Teacher person) {
		personService.save(person);
		return "redirect:/persons";
	}

	@RequestMapping(path = "persons/save/admin", method = RequestMethod.POST)
	public String saveAdmin(Admin person) {
		personService.save(person);
		return "redirect:/persons";
	}

	@RequestMapping(value = { "/persons/editStudent/{id}" }, method = RequestMethod.GET)
	public String editStudent(Model model, @PathVariable(value = "id") int id) {
		if (id != 0) {
			Student thisOne = (Student) personService.find(id).get();
			StudentForm form = new StudentForm(thisOne.getId(), thisOne.getName(), thisOne.getPassword(),
					thisOne.getFaculty(), thisOne.getGroup().getId());
			model.addAttribute("person", form);
		} else {
			StudentForm form = new StudentForm(0, null, null, null, 0);
			model.addAttribute("person", form);
		}
		model.addAttribute("groups", groupService.findAll());
		return "editStudent";
	}

	@RequestMapping(value = { "/persons/editTeacher/{id}" }, method = RequestMethod.GET)
	public String editTeacher(Model model, @PathVariable(value = "id") int id) {
		if (id != 0)
			model.addAttribute("person", personService.find(id).get());
		else {
			Person p = new Teacher(0, null, null, null, Role.TEACHER, null);
			model.addAttribute("person", p);
		}
		return "editTeacher";
	}

	@RequestMapping(value = { "/persons/editAdmin/{id}" }, method = RequestMethod.GET)
	public String editAdmin(Model model, @PathVariable(value = "id") int id) {
		if (id != 0)
			model.addAttribute("person", personService.find(id).get());
		else {
			Person p = new Admin(0, null, null, null, Role.ADMIN, null);
			model.addAttribute("person", p);
		}
		return "editAdmin";
	}

	@RequestMapping(value = { "/persons/delete/{id}" }, method = RequestMethod.GET)
	public String personsDelete(Model model, @PathVariable(value = "id") int id) {
		personService.delete(id);
		return "redirect:/persons";
	}
}
