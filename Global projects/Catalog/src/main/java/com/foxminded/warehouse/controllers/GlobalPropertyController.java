package com.foxminded.warehouse.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxminded.warehouse.entities.Property;
import com.foxminded.warehouse.exceptions.CantFindByPropertyException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Global Property Controller")
@RequestMapping(path = "/api/v1/warehouse/properties")
public interface GlobalPropertyController {

	@Operation(summary = "Find all global properties")
	@GetMapping
	public List<Property> getAllGlobalProperties();

	@Operation(summary = "Create new property")
	@PostMapping
	public Property postGlobalProperty(@Valid @RequestBody Property property);

	@Operation(summary = "Change an existing property")
	@PutMapping(value = "/{propertyName}")
	public Property putGlobalProperty(
			@Parameter(description = "Name of the property. Cannot be empty.", required = true) @PathVariable(value = "propertyName") String propertyName,
			@Valid @RequestBody Property property) throws CantFindByPropertyException;

	@Operation(summary = "Delete global property")
	@DeleteMapping(value = "/{propertyName}")
	public boolean deleteGlobalProperty(
			@Parameter(description = "Name of the property. Cannot be empty.", required = true) @PathVariable(value = "propertyName") String propertyName)
			throws CantFindByPropertyException;

}
