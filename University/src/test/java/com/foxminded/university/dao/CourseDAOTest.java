package com.foxminded.university.dao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.foxminded.university.DAOs.CourseRepository;
import com.foxminded.university.entities.Course;
import com.foxminded.university.exeptions.UniversityDaoDeleteException;

@DataJpaTest
class CourseDAOTest {

	@Autowired
	private CourseRepository courseDAO;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	final void shouldProcessWhenSave() {
		Course course1 = new Course();
		course1.setCourseName("course1");
		course1.setCourseDescription("d1addda");
		courseDAO.save(course1);
		assertTrue(courseDAO.count() > 0);
	}

	@Test
	final void shouldProcessWhenFindById() {
		jdbcTemplate
				.execute("DELETE FROM COURSES;" + "INSERT INTO Courses(Course_id,Course_name) VALUES(10,'insertName')");
		assertTrue(courseDAO.findById(10).isPresent());
	}

	@Test
	final void shouldProcessWhenDeleteById() throws UniversityDaoDeleteException {
		jdbcTemplate
				.execute("DELETE FROM COURSES;" + "INSERT INTO Courses(Course_id,Course_name) VALUES(20,'insertNAME')");
		courseDAO.deleteById(20);
		assertFalse(courseDAO.findById(20).isPresent());
	}

}
