package com.foxminded.university.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.foxminded.university.DAOs.PersonRepository;
import com.foxminded.university.entities.Group;
import com.foxminded.university.entities.Person;
import com.foxminded.university.entities.Role;
import com.foxminded.university.entities.personDetails.Student;

@DataJpaTest
public class PersonDAOTest {

	@Autowired
	private PersonRepository personDAO;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	static Group testGroup = new Group();

	@Test
	final void shouldProcessWhenSave() {
		testGroup.setGroupName("g1");
		testGroup.setId(1);
		jdbcTemplate.execute(" INSERT INTO UNIVERSITY_GROUPS(Group_id,Group_Name) VALUES(1,'g1');");
		
		Student s1 = new Student(0, "S1", "pass1", "developing", Role.STUDENT, testGroup);
		personDAO.save((Person)s1);
		assertTrue(personDAO.count()>0);
	}

	@Test
	final void shouldProcessWhenFindById() {
		jdbcTemplate.execute("Insert INTO PERSONS(PERSON_ID,NAME,USERS_PASSWORD,FACULTY,ROLE) VALUES(20,'n','p','f','STUDENT');"
				+ " INSERT INTO UNIVERSITY_GROUPS(GROUP_ID,Group_Name) VALUES(1,'g1');"
				+ " INSERT INTO STUDENTS(PERSON_ID, GROUP_ID) VALUES (20,1);");
		Optional<Person> p1 = personDAO.findById(20);
		assertTrue(p1.get().getName().equals("n"));
		assertTrue(p1.get() instanceof Student);
	}

	@Test
	final void shouldProcessWhenDeleteById()  {
		jdbcTemplate.execute("Insert INTO PERSONS(PERSON_ID,NAME,USERS_PASSWORD,FACULTY,ROLE) VALUES(1,'n','p','f','STUDENT');"
				+ " INSERT INTO UNIVERSITY_GROUPS(GROUP_ID,Group_Name) VALUES(1,'g');"
				+ " INSERT INTO STUDENTS(PERSON_ID, GROUP_ID) VALUES (1,1);");
		personDAO.deleteById(1);
		assertFalse(personDAO.findById(1).isPresent());
		assertThrows(EmptyResultDataAccessException.class, () -> personDAO.deleteById(4));
	}
}
