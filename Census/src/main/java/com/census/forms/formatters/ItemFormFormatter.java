package com.census.forms.formatters;

import org.springframework.stereotype.Component;

import com.census.entities.Item;
import com.census.forms.ItemForm;
import com.census.services.CategoryService;
import com.census.services.LocationService;
import com.census.services.UserService;

@Component
public class ItemFormFormatter {

	private final UserService userService;
	private final LocationService locationService;
	private final CategoryService categoryService;

	public ItemFormFormatter(UserService userService, LocationService locationService,
			CategoryService categoryService) {
		this.userService = userService;
		this.locationService = locationService;
		this.categoryService = categoryService;
	}

	public Item format(ItemForm form) {
		Item item = new Item();
		item.setId(form.getId());
		item.setName(form.getName());
		item.setDescription(form.getDescription());
		item.setCategory(categoryService.find(form.getCategoryId()).get());
		item.setLocation(locationService.find(form.getLocationId()).get());
		item.setOwner(userService.find(form.getOwnerId()).get());
		return item;
	}

}
