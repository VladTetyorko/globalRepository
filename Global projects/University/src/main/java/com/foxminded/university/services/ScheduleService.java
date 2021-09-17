package com.foxminded.university.services;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.university.DAOs.ScheduleRepository;
import com.foxminded.university.entities.Calendar;
import com.foxminded.university.entities.Group;
import com.foxminded.university.entities.Lecture;
import com.foxminded.university.exeptions.UniversityDaoDeleteException;
import com.foxminded.university.exeptions.UniversityDaoSaveException;
import com.foxminded.university.exeptions.UniversityDaoUpdateException;

@Service
public class ScheduleService implements ModelService<Lecture> {

	private Logger logger = LoggerFactory.getLogger(ScheduleService.class.getName());

	@Autowired
	private ScheduleRepository scheduleDAO;

	@Autowired
	Calendar calendar;

	@Override
	@Transactional
	public Lecture save(Lecture model) {
		if (logger.isDebugEnabled()) {
			logger.debug("Saving lecture {}", model);
		}
		try {
			Lecture saved = scheduleDAO.save(model);
			logger.info("Lecture {} saved successfull", model.toString());
			return saved;
		} catch (UniversityDaoSaveException | UniversityDaoUpdateException e) {
			logger.error(e.toString());
		}
		return model;
	}

	@Override
	@Transactional
	public Optional<Lecture> find(int id) {
		Optional<Lecture> founded;
		if (logger.isDebugEnabled())
			logger.debug("Searching lecture with id {}", id);
		founded = scheduleDAO.findById(id);
		logger.info("Lecture with id {} was found successfull", id);
		return founded;
	}

	@Override
	@Transactional
	public List<Lecture> findAll() {
		List<Lecture> list = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching all lectures");
		list = scheduleDAO.findAll();
		logger.info("Lectures list was found");
		return list;
	}

	@Override
	@Transactional
	public boolean delete(int id) {
		if (logger.isDebugEnabled())
			logger.debug("Deleting lecture with id {}", id);
		try {
			scheduleDAO.deleteById(id);
			logger.info("Lecture with id={} was deleted successfull", id);
			return true;
		} catch (UniversityDaoDeleteException e) {
			logger.warn(e.toString());
			return false;
		}
	}

	@Transactional
	public List<Lecture> findForStudentOnWeek(Group groupId, int week) {
		List<Lecture> list = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching lectures for group on week");
		list = scheduleDAO.findByGroupAndWeek(groupId, week);
		logger.info("Lectures list was found");
		return list;
	}

	@Transactional
	public List<Lecture> findForStudentOnDay(Group groupId, DayOfWeek weekday) {
		List<Lecture> list = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching lectures for group on day");
		list = scheduleDAO.findByGroupAndWeekday(groupId, weekday);
		logger.info("Lectures list was found");
		return list;
	}

	@Transactional
	public List<Lecture> findForTeacherOnWeek(int teaherId, int week) {
		List<Lecture> list = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching lectures for teacher on week");
		list = scheduleDAO.findByTeacherAndWeek(teaherId, week);
		logger.info("Lectures list was found");
		return list;
	}

	@Transactional
	public List<Lecture> findForTeacherOnDay(int teacherId, DayOfWeek weekday) {
		List<Lecture> list = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching lectures for teacher on day");
		list = scheduleDAO.findByTeacherAndWeekday(teacherId, weekday);
		logger.info("Lectures list was found");
		return list;
	}

	@Transactional
	public List<Lecture> findForRoomOnDay(int room, DayOfWeek weekday) {
		List<Lecture> list = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching lectures for room on day");
		list = scheduleDAO.findByRoomNumberAndWeekday(room, weekday);
		logger.info("Lectures list was found");
		return list;
	}

}
