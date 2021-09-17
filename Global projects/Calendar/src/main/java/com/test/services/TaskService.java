package com.test.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.calendar.entities.DateEntity;
import com.test.calendar.entities.Task;
import com.test.repositories.TaskRepository;

@Service
public class TaskService {

	private final TaskRepository repository;

	private static final Logger logger = LoggerFactory.getLogger(TaskRepository.class.getName());

	public TaskService(TaskRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public Task save(Task task) {
		Task saved = null;
		try {
			saved = repository.save(task);
			logger.info("Task {} saved", saved.toString());
			return saved;
		} catch (Exception e) {
			logger.warn(e.toString());
		}
		return saved;
	}

	@Transactional
	public Optional<Task> find(int id) {
		Optional<Task> founded = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching task with id {}", id);
		founded = repository.findById(id);
		if (founded.isPresent())
			logger.info("Task with id {} was found successfull", id);
		else
			logger.warn("Task with id {} doesnt exsist", id);
		return founded;
	}

	@Transactional
	public List<Task> findAll() {
		if (logger.isDebugEnabled())
			logger.debug("Searching for all tasks");
		List<Task> foundedList = repository.findAll();
		return foundedList;
	}

	@Transactional
	public List<Task> findTasksOnDate(DateEntity date) {
		if (logger.isDebugEnabled())
			logger.debug("Searching for {} schedule", date.getFormatedDate());
		List<Task> foundedList = repository.findByDate(date);
		return foundedList;
	}

	@Transactional
	public boolean delete(int id) {
		if (logger.isDebugEnabled())
			logger.debug("Deleting task with id {}", id);
		try {
			repository.deleteById(id);
			logger.info("Task with id={} was deleted", id);
			return true;
		} catch (Exception e) {
			logger.warn(e.toString());
			return false;
		}
	}

}
