package com.foxminded.warehouse.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.warehouse.entities.Property;
import com.foxminded.warehouse.repositorys.PropertyRepository;

@Service
public class PropertyService implements ModelService<Property> {

	private final PropertyRepository globalPropertRepository;

	private static final Logger logger = LoggerFactory.getLogger(PropertyService.class.getName());

	public PropertyService(PropertyRepository repository) {
		this.globalPropertRepository = repository;
	}

	@Override
	@Transactional
	public Property save(Property property) {
		Property saved = null;
		try {
			saved = globalPropertRepository.save(property);
			logger.info("Property {} saved", saved.toString());
			return saved;
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return saved;
	}

	@Override
	@Transactional
	public Optional<Property> find(int id) {
		Optional<Property> founded = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching property with id {}", id);
		founded = globalPropertRepository.findById(id);
		if (founded.isPresent())
			logger.info("Property with id {} was found successfull", id);
		else
			logger.warn("Property with id {} doesnt exsist", id);
		return founded;
	}

	@Override
	@Transactional
	public List<Property> findAll() {
		List<Property> list = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching all products");
		list = globalPropertRepository.findAll();
		logger.info("Property list was found");
		return list;
	}

	@Override
	@Transactional
	public boolean delete(int id) {
		if (logger.isDebugEnabled())
			logger.debug("Deleting property with id {}", id);
		try {
			globalPropertRepository.deleteById(id);
			logger.info("Property with id={} was deleted", id);
			return true;
		} catch (Exception e) {
			logger.warn(e.toString());
			return false;
		}
	}

	@Transactional
	public Optional<Property> findByName(String propertyName) {
		Optional<Property> founded = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching property with name {}", propertyName);
		founded = globalPropertRepository.findByName(propertyName);
		if (founded.isPresent())
			logger.info("Property with name {} was found successfull", propertyName);
		else
			logger.warn("Property with name {} doesnt exsist", propertyName);
		return founded;
	}

}
