package com.foxminded.warehouse.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.foxminded.warehouse.entities.Category;
import com.foxminded.warehouse.entities.Product;
import com.foxminded.warehouse.entities.Property;
import com.foxminded.warehouse.entities.PropertyValue;
import com.foxminded.warehouse.services.CategoryService;
import com.foxminded.warehouse.services.ProductService;
import com.foxminded.warehouse.services.ProductsPropertyService;
import com.foxminded.warehouse.services.PropertyService;

@SpringBootTest
class PropertyControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private CategoryService categoryService;

	@MockBean
	private ProductService productService;

	@MockBean
	private PropertyService propertyService;

	@MockBean
	ProductsPropertyService productsPropertyService;

	@MockBean
	Product product;

	@MockBean
	Property globalProperty;

	@MockBean
	PropertyValue localProperty;

	@BeforeEach
	void setup(WebApplicationContext wac) {
		Category category = new Category();
		Mockito.when(categoryService.findByName("categoryName")).thenReturn(Optional.of(category));
		Mockito.when(productService.findInCategoryByName(category, "productName")).thenReturn(Optional.of(product));
		Mockito.when(propertyService.findByName("CPU")).thenReturn(Optional.of(globalProperty));
		Mockito.when(productsPropertyService.findByProductAndGlobalProperty(product, globalProperty))
				.thenReturn(Optional.of(localProperty));
		Mockito.when(productsPropertyService.save(Mockito.any())).thenReturn(localProperty);

		Mockito.when(localProperty.getId()).thenReturn(1);
		Mockito.when(localProperty.getValue()).thenReturn("value");

		Mockito.when(globalProperty.getPropertyId()).thenReturn(1);
		Mockito.when(globalProperty.getName()).thenReturn("CPU");

		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	final void shouldGetAllGlobalProperties() throws Exception {
		List<Property> list = new ArrayList<Property>();
		Mockito.when(propertyService.findAll()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/warehouse/properties")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		verify(propertyService, times(1)).findAll();
	}

	@Test
	final void shouldGetPropertiesOfProduct() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/warehouse/categories/categoryName/products/productName/properties")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		verify(product, times(1)).getProperties();
	}

	@Test
	final void shouldPutProperty() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/warehouse/properties/CPU")
				.accept(MediaType.APPLICATION_JSON).content("{\"name\":\"myValue\"}")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		verify(propertyService, times(1)).save(Mockito.any());
	}

	@Test
	final void shouldDeleteGlobalProperty() throws Exception {
		Mockito.when(propertyService.delete(1)).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/warehouse/properties/CPU")
				.accept(MediaType.ALL);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		verify(propertyService, times(1)).delete(1);
	}

	@Test
	final void shouldDeleteProductProperty() throws Exception {
		Mockito.when(productsPropertyService.delete(1)).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/v1/warehouse/categories/categoryName/products/productName/properties/CPU")
				.accept(MediaType.ALL);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		verify(productsPropertyService, times(1)).delete(1);
	}

}
