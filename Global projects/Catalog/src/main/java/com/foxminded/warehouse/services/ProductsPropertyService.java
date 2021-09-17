package com.foxminded.warehouse.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.warehouse.entities.Product;
import com.foxminded.warehouse.entities.Property;
import com.foxminded.warehouse.entities.PropertyValue;
import com.foxminded.warehouse.repositorys.PropertyValueRepository;

@Service
public class ProductsPropertyService implements ModelService<PropertyValue> {

	private final PropertyValueRepository repository;

	private static final Logger logger = LoggerFactory.getLogger(ProductsPropertyService.class.getName());

	public ProductsPropertyService(PropertyValueRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public PropertyValue save(PropertyValue property) {
		PropertyValue saved = null;
		try {
			Optional<PropertyValue> foundedProductProperty = findByProductAndGlobalProperty(property.getProduct(),
					property.getGlobalProperty());
			if (foundedProductProperty.isPresent()) {
				property.setId(foundedProductProperty.get().getId());
				saved = repository.save(property);
			} else
				saved = repository.save(property);
			logger.info("Property {} saved", saved.toString());
			return saved;
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return saved;
	}

	@Override
	@Transactional
	public Optional<PropertyValue> find(int id) {
		Optional<PropertyValue> founded = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching property with id {}", id);
		founded = repository.findById(id);
		if (founded.isPresent())
			logger.info("Property with id {} was found successfull", id);
		else
			logger.warn("Property with id {} doesnt exsist", id);
		return founded;
	}

	@Override
	@Transactional
	public List<PropertyValue> findAll() {
		List<PropertyValue> list = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching all products");
		list = repository.findAll();
		logger.info("Property list was found");
		return list;
	}

	@Override
	@Transactional
	public boolean delete(int id) {
		if (logger.isDebugEnabled())
			logger.debug("Deleting property with id {}", id);
		try {
			repository.deleteById(id);
			logger.info("Property with id={} was deleted", id);
			return true;
		} catch (Exception e) {
			logger.warn(e.toString());
			return false;
		}
	}

	@Transactional
	public List<PropertyValue> findByProduct(Product product) {
		List<PropertyValue> founded = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching property with product {}", product);
		founded = repository.findByProduct(product);
		if (!founded.isEmpty())
			logger.info("Properties of product{} was found successfull", product);
		else
			logger.warn("Properties of product {} doesnt exsist", product);
		return founded;
	}

	@Transactional
	public Optional<PropertyValue> findByProductAndGlobalProperty(Product product, Property property) {
		Optional<PropertyValue> founded = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching property with product {}", product);
		founded = repository.findByGlobalPropertyAndProduct(property, product);
		if (founded.isPresent())
			logger.info("Property of product {} with name {} was found successfull", product, property.getName());
		else
			logger.warn("Property of product {}  with name {} doesnt exsist", product, property.getName());
		return founded;
	}

}
