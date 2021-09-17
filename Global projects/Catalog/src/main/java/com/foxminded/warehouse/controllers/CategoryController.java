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

import com.foxminded.warehouse.entities.Category;
import com.foxminded.warehouse.exceptions.CantFindByCategoryException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Category Controller")
@RequestMapping(path = { "/api/v1/warehouse/categories" })
public interface CategoryController {

	@Operation(summary = "Find all categories")
	@GetMapping
	public List<Category> getAllCategories();

	@Operation(summary = "Post category")
	@PostMapping
	public Category postCategory(@Valid @RequestBody Category entity);

	@Operation(summary = "Put category")
	@PutMapping(value = "/{categoryName}")
	public Category putCategory(
			@Parameter(description = "Name of the category. Cannot be empty.", required = true) @PathVariable(value = "categoryName") String categoryName,
			@Valid @RequestBody Category entity) throws CantFindByCategoryException;

	@Operation(summary = "Delete category")
	@DeleteMapping(value = "/{categoryName}")
	public boolean deleteCategory(
			@Parameter(description = "Name of the category. Cannot be empty.", required = true) @PathVariable(value = "categoryName") String categoryName)
			throws CantFindByCategoryException;

}
