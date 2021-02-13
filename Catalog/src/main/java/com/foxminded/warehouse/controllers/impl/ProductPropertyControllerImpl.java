package com.foxminded.warehouse.controllers.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.foxminded.warehouse.controllers.ProductPropertyController;
import com.foxminded.warehouse.entities.Category;
import com.foxminded.warehouse.entities.Product;
import com.foxminded.warehouse.entities.Property;
import com.foxminded.warehouse.entities.PropertyValue;
import com.foxminded.warehouse.exceptions.CantFindByProductException;
import com.foxminded.warehouse.exceptions.CantFindByPropertyException;
import com.foxminded.warehouse.services.CategoryService;
import com.foxminded.warehouse.services.ProductService;
import com.foxminded.warehouse.services.ProductsPropertyService;
import com.foxminded.warehouse.services.PropertyService;

@RestController
public class ProductPropertyControllerImpl implements ProductPropertyController {

	private final CategoryService categoryService;

	private final ProductService productService;

	private final PropertyService propertyService;

	private final ProductsPropertyService productsPropertyService;

	public ProductPropertyControllerImpl(CategoryService categoryService, ProductService productService,
			PropertyService propertyService, ProductsPropertyService productsPropertyService) {
		this.categoryService = categoryService;
		this.productService = productService;
		this.productsPropertyService = productsPropertyService;
		this.propertyService = propertyService;
	}

	@Override
	public List<PropertyValue> getPropertiesOfProduct(@PathVariable(value = "categoryName") String categoryName,
			@PathVariable(value = "productName") String productName) throws CantFindByProductException {
		try {
			Category category = categoryService.findByName(categoryName).get();
			Product item = productService.findInCategoryByName(category, productName).get();
			List<PropertyValue> properties = item.getProperties();
			return properties;
		} catch (Exception e) {
			throw new CantFindByProductException(categoryName, productName);
		}
	}

	@Override
	public PropertyValue setPropertyOfProduct(@PathVariable(value = "categoryName") String categoryName,
			@PathVariable(value = "productName") String productName,
			@PathVariable(value = "propertyName") String propertyName, @Valid @RequestBody PropertyValue property)
			throws CantFindByPropertyException {
		try {
			Category category = categoryService.findByName(categoryName).get();
			Product item = productService.findInCategoryByName(category, productName).get();
			property.setProduct(item);
			property.setGlobalProperty(propertyService.findByName(propertyName).get());
			productsPropertyService.save(property);
			return property;
		} catch (Exception e) {
			throw new CantFindByPropertyException(categoryName, productName, propertyName);
		}
	}

	@Override
	public boolean deleteProductProperty(@PathVariable(value = "categoryName") String categoryName,
			@PathVariable(value = "productName") String productName,
			@PathVariable(value = "propertyName") String propertyName) throws CantFindByPropertyException {
		try {
			Category category = categoryService.findByName(categoryName).get();
			Product product = productService.findInCategoryByName(category, productName).get();
			Property globalProperty = propertyService.findByName(propertyName).get();
			PropertyValue founded = productsPropertyService.findByProductAndGlobalProperty(product, globalProperty)
					.get();
			productsPropertyService.delete(founded.getId());
			return true;
		} catch (Exception e) {
			throw new CantFindByPropertyException(categoryName, productName, propertyName);
		}
	}
}
