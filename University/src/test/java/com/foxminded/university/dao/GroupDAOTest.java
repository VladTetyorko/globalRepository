package com.foxminded.university.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import com.foxminded.university.DAOs.GroupRepository;
import com.foxminded.university.entities.Group;

@DataJpaTest
class GroupDAOTest {
	@Autowired
	private GroupRepository groupDAO;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	@Sql("/createGroups.sql")
	final void shouldProcessWhenSave() {
		Group group1 = new Group();
		group1.setGroupName("g1");
		Group saved = groupDAO.save(group1);
		assertTrue(saved.getId()!=0);
	}

	@Test
	@Sql("/createGroups.sql")
	final void shouldProcessWhenFindById() {
		jdbcTemplate.execute("DELETE FROM UNIVERSITY_GROUPS;"
				+"INSERT INTO UNIVERSITY_GROUPS(GROUP_ID,GROUP_NAME) VALUES(12,'insertName')");
		Group founded = groupDAO.findById(12).get();
		assertTrue(founded.getGroupName().equals("insertName"));
	}

	@Test
	@Sql("/createGroups.sql")
	final void shouldProcessWhenDeleteById(){
		jdbcTemplate.execute("DELETE FROM UNIVERSITY_GROUPS;"
				+"INSERT INTO UNIVERSITY_GROUPS(GROUP_ID,GROUP_NAME) VALUES(18,'insertName')");
		groupDAO.deleteById(18);
		assertThrows(EmptyResultDataAccessException.class, () -> groupDAO.deleteById(18));
	}

}
