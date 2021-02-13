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

import com.foxminded.warehouse.entities.Product;
import com.foxminded.warehouse.exceptions.CantFindByCategoryException;
import com.foxminded.warehouse.exceptions.CantFindByProductException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Product Controller")
@RequestMapping(path = "/api/v1/warehouse/categories/{categoryName}/products")
public interface ProductController {

	@Operation(summary = "Find all products in category")
	@GetMapping
	public List<Product> getItemsByCategory(@PathVariable(value = "categoryName") String categoryName);

	@Operation(summary = "Create new product in category")
	@PostMapping
	public Product postItem(@PathVariable(value = "categoryName") String categoryName,
			@Valid @RequestBody Product entity) throws CantFindByCategoryException;

	@Operation(summary = "Change an existed product in category")
	@PutMapping(value = "/{productName}")
	public Product putItem(
			@Parameter(description = "Name of the category. Cannot be empty.", required = true) @PathVariable(value = "categoryName") String categoryName,
			@Parameter(description = "Name of the property. Cannot be empty.", required = true) @PathVariable(value = "productName") String productName,
			@RequestBody Product entity) throws CantFindByCategoryException;

	@Operation(summary = "Delete product in category")
	@DeleteMapping(value = "/{productName}")
	public boolean deleteItem(
			@Parameter(description = "Name of the category. Cannot be empty.", required = true) @PathVariable(value = "categoryName") String categoryName,
			@Parameter(description = "Name of the product. Cannot be empty.", required = true) @PathVariable(value = "productName") String productName)
			throws CantFindByProductException;

}
