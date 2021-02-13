package com.foxminded.university.controllers;

import java.time.DayOfWeek;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foxminded.university.entities.Calendar;
import com.foxminded.university.entities.Lecture;
import com.foxminded.university.entities.Person;
import com.foxminded.university.entities.personDetails.Student;
import com.foxminded.university.entities.personDetails.Teacher;
import com.foxminded.university.forms.LectureForm;
import com.foxminded.university.forms.formatters.LectureFormFormatter;
import com.foxminded.university.services.CourseService;
import com.foxminded.university.services.GroupService;
import com.foxminded.university.services.PersonService;
import com.foxminded.university.services.ScheduleService;

@Controller
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private PersonService personService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private Calendar calendar;
	@Autowired
	private LectureFormFormatter letureFormatter;

	@RequestMapping(value = { "/schedule", "/schedule/forDay/{day}/{id}" }, method = RequestMethod.GET)
	public String scheduleForDay(Model model, @PathVariable(value = "id") Optional<Integer> id,
			@PathVariable(value = "day") Optional<DayOfWeek> day) {
		model.addAttribute("calendar", calendar.getCalendar());
		if (!id.isPresent() || id.get() == 0)
			model.addAttribute("schedule", scheduleService.findAll());
		else {
			Person p = personService.find(id.get()).get();
			if (p instanceof Student)
				model.addAttribute("schedule",
						scheduleService.findForStudentOnDay(((Student) p).getGroup(), day.get()));
			else if (p instanceof Teacher)
				model.addAttribute("schedule", scheduleService.findForTeacherOnDay(p.getId(), day.get()));
			else
				model.addAttribute("schedule", scheduleService.findAll());
			model.addAttribute("loginedPerson", p);
		}
		model.addAttribute("persons", personService.findAll());
		model.addAttribute("daysOfWeek", calendar.getDayList());
		return "schedule";
	}

	@RequestMapping(value = { "/schedule/forWeek/{week}/{id}" }, method = RequestMethod.GET)
	public String scheduleForWeek(Model model, @PathVariable(value = "id") Optional<Integer> id,
			@PathVariable(value = "week") Optional<Integer> week) {
		model.addAttribute("calendar", calendar.getCalendar());
		if (id.get() == 0)
			model.addAttribute("schedule", scheduleService.findAll());
		else {
			Person p = personService.find(id.get()).get();
			if (p instanceof Student)
				model.addAttribute("schedule",
						scheduleService.findForStudentOnWeek(((Student) p).getGroup(), week.get()));
			else if (p instanceof Teacher)
				model.addAttribute("schedule", scheduleService.findForTeacherOnWeek(p.getId(), week.get()));
			else
				model.addAttribute("schedule", scheduleService.findAll());
			model.addAttribute("loginedPerson", p);
		}
		model.addAttribute("persons", personService.findAll());
		model.addAttribute("daysOfWeek", calendar.getDayList());
		return "schedule";
	}

	@RequestMapping(value = { "/schedule/editLecture/{id}" }, method = RequestMethod.GET)
	public String scheduleEdit(Model model, @PathVariable(value = "id") int id) {
		Lecture l = scheduleService.find(id).get();
		LectureForm lectureForm = new LectureForm(l.getLectureId(), l.getTimeSlot(), l.getTeacher().getId(),
				l.getCourse().getCourseId(), l.getGroup().getId(), l.getAudience(), l.getWeek(), l.getWeekday());
		model.addAttribute("lecture", lectureForm);
		model.addAttribute("teachers", personService.findTeachers());
		model.addAttribute("courses", courseService.findAll());
		model.addAttribute("groups", groupService.findAll());
		model.addAttribute("daysOfWeek", calendar.getDayList());
		return "editLecture";
	}

	@RequestMapping(value = { "/schedule/createLecture" }, method = RequestMethod.GET)
	public String scheduleCreate(Model model) {
		LectureForm lectureForm = new LectureForm(0, 0, 0, 0, 0, 0, 0, null);
		model.addAttribute("lecture", lectureForm);
		model.addAttribute("teachers", personService.findTeachers());
		model.addAttribute("courses", courseService.findAll());
		model.addAttribute("groups", groupService.findAll());
		model.addAttribute("daysOfWeek", calendar.getDayList());
		return "editLecture";
	}

	@RequestMapping(value = { "/schedule/save" }, method = RequestMethod.POST)
	public String scheduleSave(Model model, LectureForm lectureForm) {
		scheduleService.save(letureFormatter.getLecture(lectureForm));
		return "redirect:/schedule";
	}

	@RequestMapping(value = { "/schedule/delete/{id}" }, method = RequestMethod.GET)
	public String scheduleDelete(Model model, @PathVariable(value = "id") int id) {
		scheduleService.delete(id);
		return "redirect:/schedule";
	}
}
