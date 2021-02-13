package com.foxminded.university.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.university.DAOs.PersonRepository;
import com.foxminded.university.entities.Person;
import com.foxminded.university.entities.Role;
import com.foxminded.university.exeptions.UniversityDaoDeleteException;
import com.foxminded.university.exeptions.UniversityDaoSaveException;
import com.foxminded.university.exeptions.UniversityDaoUpdateException;

@Service
public class PersonService implements ModelService<Person> {

	@Autowired
	private PersonRepository personDAO;

	private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Person save(Person model) {
		Person saved;
		if (logger.isDebugEnabled())
			logger.debug("Saving person {}", model.toString());
		try {
			saved = personDAO.save(model);
			logger.info("Person {} saved", saved.toString());
			return saved;
		} catch (UniversityDaoSaveException | UniversityDaoUpdateException e) {
			logger.error("Unexpected error when saving person", e);
			return model;
		}

	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Optional<Person> find(int id) {
		Optional<Person> founded;
		if (logger.isDebugEnabled())
			logger.debug("Searching for person with id{}", id);
		founded = personDAO.findById(id);
		if(founded.isPresent())
		logger.info("Person with id {} was found successfull", id);
		return founded;
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public List<Person> findAll() {
		List<Person> list = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching for all persons");
		list = personDAO.findAll();
		logger.info("Persons list was found");
		return list;

	}
	
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public List<Person> findTeachers() {
		List<Person> list = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching for all persons");
		list = personDAO.findAll().stream().filter(p->p.getRole()==Role.TEACHER).collect(Collectors.toList());
		logger.info("Teachers list was found");
		return list;

	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public boolean delete(int id) {
		if (logger.isDebugEnabled())
			logger.debug("Deleting person with id{}", id);
		try {
			personDAO.deleteById(id);
			logger.info("Person with id={} was deleted", id);
			return true;
		} catch (UniversityDaoDeleteException e) {
			logger.warn("You tried to delete unexisted Person");
			return false;
		}
	}

}
