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
import com.census.exceptions.AlreadyExsistException;
import com.census.exceptions.CategoryAlreadyExsistException;
import com.census.exceptions.SQLUnexpectedException;
import com.census.repositories.CategoryRepository;

@Service
public class CategoryService {

	private final CategoryRepository repository;

	private static final Logger logger = LoggerFactory.getLogger(CategoryRepository.class.getName());

	public CategoryService(CategoryRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public Category save(Category category) throws AlreadyExsistException {
		Category saved = null;
		try {
			saved = repository.save(category);
			logger.info("Category {} saved", saved.toString());
			return saved;
		} catch (JpaSystemException e) {
			logger.warn(e.getRootCause().getLocalizedMessage());
			if (e.getRootCause().getLocalizedMessage().contains("SQLITE_CONSTRAINT_UNIQUE"))
				throw new CategoryAlreadyExsistException(category.getName());
			else
				throw new SQLUnexpectedException(e.getRootCause().getLocalizedMessage());
		}
	}

	@Transactional
	public Optional<Category> find(int id) {
		Optional<Category> founded = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching category with id {}", id);
		founded = repository.findById(id);
		if (founded.isPresent())
			logger.info("Category with id {} was found successfull", id);
		else
			logger.warn("Category with id {} doesnt exsist", id);
		return founded;
	}

	@Transactional
	public Optional<Category> findByName(String name) {
		Optional<Category> founded = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching category with name {}", name);
		founded = repository.findByName(name);
		if (founded.isPresent())
			logger.info("Category with name {} was found successfull", name);
		else
			logger.warn("Category with name {} doesnt exsist", name);
		return founded;
	}

	@Transactional
	public List<Category> findAll() {
		List<Category> list = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching all categories");
		list = repository.findAll();
		logger.info("Category list was found");
		return list;
	}

	public boolean delete(int id) {
		if (logger.isDebugEnabled())
			logger.debug("Deleating category with id={}", id);
		try {
			repository.deleteById(id);
			logger.info("Category with id={} was deleted", id);
			return true;
		} catch (Exception e) {
			logger.warn(e.toString());
			return false;
		}
	}

	public List<Category> findByPartOfName(String partOfName) {
		List<Category> list = new ArrayList<Category>();
		if (logger.isDebugEnabled())
			logger.debug("Searching all categories contains {}", partOfName);
		list = repository.findTop10ByNameIsContaining(partOfName);
		logger.info("Category list contains '{}' was found", partOfName);
		return list;
	}

	public Object searchByNameAndLimitCustomQuerry(String categoryNamePart, int limit) {
		List<Category> list = new ArrayList<Category>();
		list = repository.findLocationsCustomByOrder(categoryNamePart, limit);
		return list;
	}
}