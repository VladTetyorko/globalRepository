package com.foxminded.warehouse.controllers.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.foxminded.warehouse.controllers.GlobalPropertyController;
import com.foxminded.warehouse.entities.Property;
import com.foxminded.warehouse.exceptions.AlreadyExsistException;
import com.foxminded.warehouse.exceptions.CantFindByPropertyException;
import com.foxminded.warehouse.services.PropertyService;

@RestController
public class GlobalPropertyControllerImpl implements GlobalPropertyController {

	private final PropertyService propertyService;

	public GlobalPropertyControllerImpl(PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	@Override
	public List<Property> getAllGlobalProperties() {
		List<Property> properties = propertyService.findAll();
		return properties;
	}

	@Override
	public Property postGlobalProperty(@Valid @RequestBody Property property) {
		Optional<Property> foundedProperty = propertyService.findByName(property.getName());
		if (foundedProperty.isPresent())
			throw new AlreadyExsistException(property.getName());
		else
			propertyService.save(property);
		return property;
	}

	@Override
	public Property putGlobalProperty(@PathVariable(value = "propertyName") String propertyName,
			@Valid @RequestBody Property property) throws CantFindByPropertyException {
		try {
			Property founded = propertyService.findByName(propertyName).get();
			property.setPropertyId(founded.getPropertyId());
			propertyService.save(property);
			return property;
		} catch (Exception e) {
			throw new CantFindByPropertyException(null, null, property.getName());
		}
	}

	@Override
	public boolean deleteGlobalProperty(@PathVariable(value = "propertyName") String propertyName)
			throws CantFindByPropertyException {
		Optional<Property> property = propertyService.findByName(propertyName);
		if (property.isPresent()) {
			propertyService.delete(property.get().getPropertyId());
			return true;
		}
		throw new CantFindByPropertyException(null, null, property.get().getName());

	}
}
