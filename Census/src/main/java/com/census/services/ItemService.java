package com.census.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.census.entities.Category;
import com.census.entities.Item;
import com.census.entities.Location;
import com.census.entities.User;
import com.census.exceptions.AlreadyExsistException;
import com.census.exceptions.SQLUnexpectedException;
import com.census.repositories.ItemRepository;

@Service
public class ItemService {

	private final ItemRepository repository;

	private static final Logger logger = LoggerFactory.getLogger(ItemService.class.getName());

	public ItemService(ItemRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public Item save(Item item) throws AlreadyExsistException {
		Item saved = null;
		try {
			saved = repository.save(item);
			logger.info("Item {} saved", saved.toString());
			return saved;
		} catch (JpaSystemException e) {
			logger.warn(e.getRootCause().getLocalizedMessage());
			throw new SQLUnexpectedException(e.getRootCause().getLocalizedMessage());
		}
	}

	@Transactional
	public Optional<Item> find(int id) {
		Optional<Item> founded;
		if (logger.isDebugEnabled())
			logger.debug("Searching item with id {}", id);
		founded = repository.findById(id);
		if (founded.isPresent())
			logger.info("Item with id '{}' was found successfull", id);
		else
			logger.warn("Item with id '{}' doesnt exsist", id);
		return founded;
	}

	@Transactional
	public List<Item> findForUser(User user) {
		List<Item> list = new ArrayList<Item>();
		if (logger.isDebugEnabled())
			logger.debug("Searching item for user {}", user.getUsername());
		list = repository.findByOwner(user);
		if (!list.isEmpty())
			logger.info("List for user '{}' found successfull", user.getUsername());
		else
			logger.warn("List for user '{}' doesnt exsist", user.getUsername());
		return list;
	}

	@Transactional
	public List<Item> findAll() {
		List<Item> list = new ArrayList<Item>();
		if (logger.isDebugEnabled())
			logger.debug("Searching all items");
		list = repository.findAll();
		logger.info("Item list was found");
		return list;
	}

	@Transactional
	public List<Item> findForLocation(Location location) {
		List<Item> list = new ArrayList<Item>();
		if (logger.isDebugEnabled())
			logger.debug("Searching all items for location {}", location.getName());
		list = repository.findByLocation(location);
		logger.info("Item list in location '{}' was found", location.getName());
		return list;
	}

	@Transactional
	public boolean delete(int id) {
		if (logger.isDebugEnabled())
			logger.debug("Deleting item with id {}", id);
		try {
			repository.deleteById(id);
			logger.info("Item with id={} was deleted", id);
			return true;
		} catch (Exception e) {
			logger.warn(e.toString());
			return false;
		}
	}

	@Transactional
	public List<Item> findForCategory(Category category) {
		List<Item> list = new ArrayList<Item>();
		if (logger.isDebugEnabled())
			logger.debug("Searching all items for category {}", category.getName());
		list = repository.findByCategory(category);
		logger.info("Item list for category '{}' was found", category.getName());
		return list;
	}

	@Transactional
	public List<Item> findByPartOfNameInLocation(Location l, String partOfName) {
		List<Item> list = new ArrayList<Item>();
		if (logger.isDebugEnabled())
			logger.debug("Searching all items contains {}", partOfName);
		list = repository.findByLocationAndTranslations_NameIsContaining(l, partOfName);
		logger.info("Item list contains '{}' was found", partOfName);
		return list;
	}

	@Transactional
	public List<Item> findByPartOfNameInCategory(Category c, String partOfName) {
		List<Item> list = new ArrayList<Item>();
		if (logger.isDebugEnabled())
			logger.debug("Searching all items contains {}", partOfName);
		list = repository.findByCategoryAndTranslations_NameIsContaining(c, partOfName);
		logger.info("Item list contains '{}' was found", partOfName);
		return list;
	}

	@Transactional
	public List<Item> findByPartOfNameForUser(User principal, String partOfName) {
		List<Item> list = new ArrayList<Item>();
		if (logger.isDebugEnabled())
			logger.debug("Searching all items contains {}", partOfName);
		list = repository.findByOwnerAndTranslations_NameIsContaining(principal, partOfName);
		logger.info("Item list contains '{}' was found", partOfName);
		return list;
	}

	@Transactional
	public List<Item> findByPartOfName(String partOfName) {
		List<Item> list = new ArrayList<Item>();
		if (logger.isDebugEnabled())
			logger.debug("Searching all items contains {}", partOfName);
		list = repository.findByTranslations_NameIsContaining(partOfName);
		logger.info("Item list contains '{}' was found", partOfName);
		return list;
	}

	@Transactional
	public List<Item> findByPartOfNameAndDesc(String namePart) {
		List<Item> list = new ArrayList<Item>();
		if (logger.isDebugEnabled())
			logger.debug("Searching all items contains in name and description {}", namePart);
		list = repository.findByNameDescription(namePart);
		logger.info("Item list desc and name contains '{}' was found", namePart);
		return list;
	}

	@Transactional
	public List<Item> findByPartOfNameAndDescForUser(String namePart, User user) {
		List<Item> list = new ArrayList<Item>();
		if (logger.isDebugEnabled())
			logger.debug("Searching all items contains in name and description {} for user {}", namePart, user);
		list = repository.findByNameDescription(namePart);
		logger.info("Item list desc and name contains '{}' was found", namePart);
		return list;
	}

}
