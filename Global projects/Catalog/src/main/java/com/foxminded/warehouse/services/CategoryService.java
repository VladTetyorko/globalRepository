package com.foxminded.warehouse.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.warehouse.entities.Category;
import com.foxminded.warehouse.repositorys.CategoryRepository;

@Service
public class CategoryService implements ModelService<Category> {

	private final CategoryRepository repository;

	private static final Logger logger = LoggerFactory.getLogger(CategoryService.class.getName());

	public CategoryService(CategoryRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Category save(Category category) {
		Category saved = null;
		try {
			saved = repository.save(category);
			logger.info("Category {} saved", saved.toString());
			return saved;
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return saved;
	}

	@Override
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

	@Override
	@Transactional
	public List<Category> findAll() {
		List<Category> list = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching all categories");
		list = repository.findAll();
		logger.info("Category list was found");
		return list;
	}

	@Override
	@Transactional
	public boolean delete(int id) {
		if (logger.isDebugEnabled())
			logger.debug("Deleting category with id {}", id);
		try {
			repository.deleteById(id);
			logger.info("Category with id={} was deleted", id);
			return true;
		} catch (Exception e) {
			logger.warn(e.toString());
			return false;
		}
	}

	@Transactional
	public Optional<Category> findByName(String name) {
		Optional<Category> founded = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching Category with name {}", name);
		founded = repository.findByName(name);
		if (founded.isPresent())
			logger.info("Category with name= {} was found successfull", name);
		else
			logger.warn("Category with name= {} doesnt exsist", name);
		return founded;
	}

}
