package com.test.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.calendar.entities.DateEntity;
import com.test.calendar.entities.Task;
import com.test.calendar.exeptions.CalendarException;
import com.test.calendar.exeptions.IlegalDateException;
import com.test.calendar.exeptions.TaskDoesntBelongsToDayException;
import com.test.services.DateService;
import com.test.services.TaskService;

@Controller
public class TaskController {

	private final TaskService taskService;
	private final DateService dateService;

	private static final Logger logger = LoggerFactory.getLogger(TaskController.class.getName());

	public TaskController(TaskService taskService, DateService dateService) {
		this.taskService = taskService;
		this.dateService = dateService;
	}

	@RequestMapping(path = "save/task", method = RequestMethod.POST)
	public String saveGroups(Task task) {
		logger.info("Saving task {}", task);
		taskService.save(task);
		return "redirect:/";
	}

	@RequestMapping(value = "/{year}/{month}/{day}/editTask/{id}")
	public String editTask(Model model, @PathVariable(value = "year") int year,
			@PathVariable(value = "month") int month, @PathVariable(value = "day") int day,
			@PathVariable(value = "id") int id) {
		logger.info("Searching for task id={}  on {}.{}.{}", id, day, month, year);
		try {
			Task task = taskService.find(id).get();
			DateEntity date = dateService.findDay(day, month, year);
			if (date.getID() != task.getDate().getID()) {
				logger.warn("This task doesnt belongs to this date");
				throw new TaskDoesntBelongsToDayException(task.getName(), date);
			}
			model.addAttribute("task", task);
			model.addAttribute("date", date);
			return "editTask";
		} catch (IlegalDateException e) {
			logger.warn(e.getMessage());
			throw new CalendarException(e.getMessage());
		}
	}

	@RequestMapping(value = "/{year}/{month}/{day}/createTask")
	public String createTask(Model model, @PathVariable(value = "year") int year,
			@PathVariable(value = "month") int month, @PathVariable(value = "day") int day) {
		DateEntity date = dateService.findDay(day, month, year);
		if (date.getID() == 0)
			date = dateService.save(date);
		Task task = new Task();
		task.setDate(date);
		model.addAttribute("task", task);
		return "editTask";
	}

	@RequestMapping(value = "/{year}/{month}/{day}/deleteTask/{id}")
	public String deleteTask(Model model, @PathVariable(value = "year") int year,
			@PathVariable(value = "month") int month, @PathVariable(value = "day") int day,
			@PathVariable(value = "id") int id) {
		taskService.delete(id);
		return "redirect:/";
	}

}
