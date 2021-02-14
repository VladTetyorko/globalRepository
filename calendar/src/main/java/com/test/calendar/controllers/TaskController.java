package com.test.calendar.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.calendar.entities.Date;
import com.test.calendar.entities.Task;
import com.test.calendar.services.DateService;
import com.test.calendar.services.TaskService;

@Controller
public class TaskController {

	private final TaskService taskService;
	private final DateService dateService;

	public TaskController(TaskService taskService, DateService dateService) {
		this.taskService = taskService;
		this.dateService = dateService;
	}

	@RequestMapping(path = "save/task", method = RequestMethod.POST)
	public String saveGroups(Task task) {
		taskService.save(task);
		return "redirect:/";
	}

	@RequestMapping(value = "/calendar/{year}/{month}/{day}/editTask/{id}")
	public String editTask(Model model, @PathVariable(value = "year") int year,
			@PathVariable(value = "month") int month, @PathVariable(value = "day") int day,
			@PathVariable(value = "id") int id) {
		Task task = taskService.find(id).get();
		Date date = dateService.findDay(day, month, year);
		if (date.getID() == 0)
			date = dateService.save(date);
		model.addAttribute("task", task);
		model.addAttribute("date", date);
		return "editTask";
	}

	@RequestMapping(value = "/calendar/{year}/{month}/{day}/createTask")
	public String createTask(Model model, @PathVariable(value = "year") int year,
			@PathVariable(value = "month") int month, @PathVariable(value = "day") int day) {
		Date date = dateService.findDay(day, month, year);
		if (date.getID() == 0)
			date = dateService.save(date);
		Task task = new Task();
		model.addAttribute("date", date);
		task.setDate(date);
		model.addAttribute("task", task);
		return "editTask";
	}

	@RequestMapping(value = "/calendar/{year}/{month}/{day}/deleteTask/{id}")
	public String deleteTask(Model model, @PathVariable(value = "year") int year,
			@PathVariable(value = "month") int month, @PathVariable(value = "day") int day,
			@PathVariable(value = "id") int id) {
		taskService.delete(id);
		return "redirect:/";
	}

}
