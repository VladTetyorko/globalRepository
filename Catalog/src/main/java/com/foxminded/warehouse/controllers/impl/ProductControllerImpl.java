package com.foxminded.warehouse.controllers.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.foxminded.warehouse.controllers.ProductController;
import com.foxminded.warehouse.entities.Category;
import com.foxminded.warehouse.entities.Product;
import com.foxminded.warehouse.exceptions.CantFindByCategoryException;
import com.foxminded.warehouse.exceptions.CantFindByProductException;
import com.foxminded.warehouse.services.CategoryService;
import com.foxminded.warehouse.services.ProductService;

@RestController
public class ProductControllerImpl implements ProductController {

	private final ProductService productService;
	private final CategoryService categoryService;

	public ProductControllerImpl(CategoryService categoryService, ProductService productService) {
		this.categoryService = categoryService;
		this.productService = productService;
	}

	@Override
	public List<Product> getItemsByCategory(@PathVariable(value = "categoryName") String categoryName) {
		Category category = categoryService.findByName(categoryName).get();
		List<Product> items = productService.findAllInCategory(category);
		return items;
	}

	@Override
	public Product postItem(@PathVariable(value = "categoryName") String categoryName,
			@Valid @RequestBody Product entity) throws CantFindByCategoryException {
		try {
			Category category = categoryService.findByName(categoryName).get();
			entity.setCategory(category);
			return productService.save(entity);
		} catch (Exception e) {
			throw new CantFindByCategoryException(categoryName);
		}
	}

	@Override
	public Product putItem(@PathVariable(value = "categoryName") String categoryName,
			@PathVariable(value = "productName") String productName, @RequestBody Product entity)
			throws CantFindByCategoryException {
		try {
			Category category = categoryService.findByName(categoryName).get();
			Optional<Product> item = productService.findInCategoryByName(category, productName);
			if (item.isPresent())
				entity.setId(item.get().getId());
			return productService.save(entity);
		} catch (Exception e) {
			throw new CantFindByCategoryException(categoryName);
		}
	}

	@Override
	public boolean deleteItem(@PathVariable(value = "categoryName") String categoryName,
			@PathVariable(value = "productName") String productName) throws CantFindByProductException {
		try {
			Category category = categoryService.findByName(categoryName).get();
			Optional<Product> item = productService.findInCategoryByName(category, productName);
			productService.delete(item.get().getId());
			return true;
		} catch (Exception e) {
			throw new CantFindByProductException(categoryName, productName);
		}
	}

}
