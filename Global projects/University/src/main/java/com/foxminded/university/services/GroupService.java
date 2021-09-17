package com.foxminded.university.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.university.DAOs.GroupRepository;
import com.foxminded.university.entities.Group;
import com.foxminded.university.exeptions.UniversityDaoDeleteException;
import com.foxminded.university.exeptions.UniversityDaoSaveException;
import com.foxminded.university.exeptions.UniversityDaoUpdateException;

@Service
public class GroupService implements ModelService<Group> {
	@Autowired
	private GroupRepository groupDAO;

	private Logger logger = LoggerFactory.getLogger(GroupService.class.getName());

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Group save(Group model) {
		Group saved = null;
		if (logger.isDebugEnabled())
			logger.debug("Saving Group {}", model);
		try {
			saved = groupDAO.save(model);
			logger.info("Group {} saved", saved.toString());
			return saved;
		} catch (UniversityDaoSaveException | UniversityDaoUpdateException e) {
			logger.error(e.toString());
		}
		return saved;
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Optional<Group> find(int id) {
		Optional<Group> founded;
		if (logger.isDebugEnabled())
			logger.debug("Searching for Group with id {}", id);
		founded = groupDAO.findById(id);
		logger.info("Group with id {} was found successfull", id);
		return founded;
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public List<Group> findAll() {
		List<Group> list = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching for all groups");
		list = groupDAO.findAll();
		logger.info("Group list was found");
		return list;

	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public boolean delete(int id) {
		if (logger.isDebugEnabled())
			logger.debug("Deleting lecture with id {}", id);
		try {
			groupDAO.deleteById(id);
			logger.info("Lecture with id={} was deleted", id);
			return true;
		} catch (UniversityDaoDeleteException e) {
			logger.warn("You tried to delete unexisted group");
			return false;
		}
	}

}
