package com.foxminded.warehouse.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.warehouse.entities.Category;
import com.foxminded.warehouse.entities.Product;
import com.foxminded.warehouse.repositorys.ProductRepository;

@Service
public class ProductService implements ModelService<Product> {

	private final ProductRepository repository;

	private static final Logger logger = LoggerFactory.getLogger(ProductService.class.getName());

	public ProductService(ProductRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Product save(Product product) {
		Product saved = null;
		try {
			saved = repository.save(product);
			logger.info("Product {} saved", saved.toString());
			return saved;
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return saved;
	}

	@Override
	@Transactional
	public Optional<Product> find(int id) {
		Optional<Product> founded = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching product with id {}", id);
		founded = repository.findById(id);
		if (founded.isPresent())
			logger.info("Product with id {} was found successfull", id);
		else
			logger.warn("Product with id {} doesnt exsist", id);
		return founded;
	}

	@Override
	@Transactional
	public List<Product> findAll() {
		List<Product> list = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching all products");
		list = repository.findAll();
		logger.info("Product list was found");
		return list;
	}

	@Override
	@Transactional
	public boolean delete(int id) {
		if (logger.isDebugEnabled())
			logger.debug("Deleting product with id {}", id);
		try {
			repository.deleteById(id);
			logger.info("Product with id={} was deleted", id);
			return true;
		} catch (Exception e) {
			logger.warn(e.toString());
			return false;
		}
	}

	@Transactional
	public List<Product> findAllInCategory(Category category) {
		List<Product> list = null;
		if (logger.isDebugEnabled())
			logger.debug("");
		list = repository.findByCategory(category);
		logger.info("Product list was found");
		return list;
	}

	@Transactional
	public Optional<Product> findInCategoryByName(Category category, String name) {
		Optional<Product> founded = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching product with name {}", name);
		founded = repository.findByCategoryAndName(category, name);
		if (founded.isPresent())
			logger.info("Product with name= {} was found successfull", name);
		else
			logger.warn("Product with name= {} doesnt exsist", name);
		return founded;
	}

}
