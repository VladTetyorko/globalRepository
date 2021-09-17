package com.foxminded.university.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foxminded.university.entities.Course;
import com.foxminded.university.services.CourseService;
import com.foxminded.university.services.PersonService;

@Controller
public class CourseController {

	@Autowired
	private CourseService courseService;
	@Autowired
	private PersonService personService;

	@RequestMapping(value = { "/courses" }, method = RequestMethod.GET)
	public String courses(Model model) {
		model.addAttribute("loginedPerson", personService.find(5).get());
		model.addAttribute("courses", courseService.findAll());
		return "courses";
	}

	@RequestMapping(path = "courses/save", method = RequestMethod.POST)
	public String saveCourse(@Valid Course course, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "editCourses";
		}
		courseService.save(course);
		return "redirect:/courses";
	}

	@RequestMapping(value = { "/courses/createCourse" }, method = RequestMethod.GET)
	public String createCourse(Model model) {
		model.addAttribute("course", new Course());
		return "editCourses";
	}

	@RequestMapping(value = { "/courses/editCourses/{id}" }, method = RequestMethod.GET)
	public String editCourse(Model model, @PathVariable(value = "id") int id) {
		model.addAttribute("course", courseService.find(id));
		return "editCourses";
	}

	@RequestMapping(value = { "/courses/delete/{id}" }, method = RequestMethod.GET)
	public String coursesDelete(Model model, @PathVariable(value = "id") int id) {
		courseService.delete(id);
		return "redirect:/courses";
	}
}
