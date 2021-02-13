package com.foxminded.warehouse.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxminded.warehouse.entities.PropertyValue;
import com.foxminded.warehouse.exceptions.CantFindByProductException;
import com.foxminded.warehouse.exceptions.CantFindByPropertyException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Product's Property Controller")
@RequestMapping(path = "/api/v1/warehouse/categories/{categoryName}/products/{productName}/properties")
public interface ProductPropertyController {

	@Operation(summary = "Get product's properties")
	@GetMapping
	public List<PropertyValue> getPropertiesOfProduct(
			@Parameter(description = "Name of the category. Cannot be empty.", required = true) @PathVariable(value = "categoryName") String categoryName,
			@Parameter(description = "Name of the product. Cannot be empty.", required = true) @PathVariable(value = "productName") String productName)
			throws CantFindByProductException;

	@Operation(summary = "Change an existed product's property")
	@PutMapping(value = "/{propertyName}")
	public PropertyValue setPropertyOfProduct(
			@Parameter(description = "Name of the category. Cannot be empty.", required = true) @PathVariable(value = "categoryName") String categoryName,
			@Parameter(description = "Name of the product. Cannot be empty.", required = true) @PathVariable(value = "productName") String productName,
			@Parameter(description = "Name of the property. Cannot be empty.", required = true) @PathVariable(value = "propertyName") String propertyName,
			@Valid @RequestBody PropertyValue property) throws CantFindByPropertyException;

	@Operation(summary = "Delete an existed product's property")
	@DeleteMapping(value = "/{propertyName}")
	public boolean deleteProductProperty(
			@Parameter(description = "Name of the category. Cannot be empty.", required = true) @PathVariable(value = "categoryName") String categoryName,
			@Parameter(description = "Name of the product. Cannot be empty.", required = true) @PathVariable(value = "productName") String productName,
			@Parameter(description = "Name of the property. Cannot be empty.", required = true) @PathVariable(value = "propertyName") String propertyName)
			throws CantFindByPropertyException;

}
