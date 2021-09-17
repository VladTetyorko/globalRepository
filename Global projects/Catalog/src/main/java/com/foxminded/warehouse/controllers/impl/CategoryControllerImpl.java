package com.foxminded.warehouse.controllers.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.foxminded.warehouse.controllers.CategoryController;
import com.foxminded.warehouse.entities.Category;
import com.foxminded.warehouse.exceptions.CantFindByCategoryException;
import com.foxminded.warehouse.services.CategoryService;

@RestController
public class CategoryControllerImpl implements CategoryController {

	private final CategoryService categoryService;

	public CategoryControllerImpl(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	public List<Category> getAllCategories() {
		List<Category> categories = categoryService.findAll();
		return categories;
	}

	@Override
	public Category postCategory(@Valid @RequestBody Category entity) {
		Category category = categoryService.save(entity);
		return category;
	}

	@Override
	public Category putCategory(@PathVariable(value = "categoryName") String categoryName,
			@Valid @RequestBody Category entity) throws CantFindByCategoryException {
		try {
			entity.setId(categoryService.findByName(categoryName).get().getId());
			Category category = categoryService.save(entity);
			return category;
		} catch (Exception e) {
			throw new CantFindByCategoryException(categoryName);
		}
	}

	@Override
	public boolean deleteCategory(@PathVariable(value = "categoryName") String categoryName)
			throws CantFindByCategoryException {
		Optional<Category> category = categoryService.findByName(categoryName);
		if (category.isPresent()) {
			categoryService.delete(category.get().getId());
			return true;
		}
		throw new CantFindByCategoryException(categoryName);

	}

}
