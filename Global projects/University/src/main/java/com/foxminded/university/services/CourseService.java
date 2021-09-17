package com.foxminded.university.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.university.DAOs.CourseRepository;
import com.foxminded.university.entities.Course;
import com.foxminded.university.exeptions.UniversityDaoDeleteException;
import com.foxminded.university.exeptions.UniversityDaoSaveException;
import com.foxminded.university.exeptions.UniversityDaoUpdateException;

@Service
public class CourseService implements ModelService<Course> {
	@Autowired
	private CourseRepository courseDAO;

	private Logger logger = LoggerFactory.getLogger(CourseService.class.getName());

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Course save(Course course) {
		Course saved = null;
		try {
			saved = courseDAO.save(course);
			logger.info("Сourse {} saved", saved.toString());
			return saved;
		} catch (UniversityDaoSaveException | UniversityDaoUpdateException e) {
			logger.error(e.toString());
		}
		return course;
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Optional<Course> find(int id) {
		Optional<Course> founded;
		if (logger.isDebugEnabled())
			logger.debug("Searching lecture with id {}", id);
		founded = courseDAO.findById(id);
		logger.info("Lecture with id {} was found successfull", id);
		return founded;
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public List<Course> findAll() {
		List<Course> list = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching all lectures");
		list = courseDAO.findAll();
		logger.info("course list was found");
		return list;
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public boolean delete(int id) {
		if (logger.isDebugEnabled())
			logger.debug("Deleting lecture with id {}", id);
		try {
			courseDAO.deleteById(id);
			logger.info("Lecture with id={} was deleted", id);
			return true;
		} catch (UniversityDaoDeleteException e) {
			logger.warn(e.toString());
			return false;
		}
	}

}
