package com.census.forms.formatters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.census.entities.Item;
import com.census.entities.ItemTranslation;
import com.census.forms.ItemForm;
import com.census.services.CategoryService;
import com.census.services.ItemService;
import com.census.services.LocationService;
import com.census.services.UserService;

@Component
public class ItemFormFormatter {

	private final UserService userService;
	private final LocationService locationService;
	private final CategoryService categoryService;
	private final ItemService itemService;

	public ItemFormFormatter(ItemService itemService, UserService userService, LocationService locationService,
			CategoryService categoryService, List<String> languages) {
		this.userService = userService;
		this.locationService = locationService;
		this.categoryService = categoryService;
		this.itemService = itemService;
	}

	public Item format(ItemForm form, String lang) {
		Optional<Item> optional = itemService.find(form.getId());
		Item item;
		if (optional.isPresent())
			item = optional.get();
		else {
			item = new Item();
			item.setId(0);
		}
		item.setName(form.getName());
		item.setCategory(categoryService.find(form.getCategoryId()).get());
		item.setLocation(locationService.find(form.getLocationId()).get());
		item.setOwner(userService.find(form.getOwnerId()).get());
		List<ItemTranslation> translations = form.getTranslations();
		translations.forEach(s -> {
			if (s.getLanguage().equals(lang)) {
				s.setName(form.getName());
				s.setDescription(form.getDescription());
			} else {
				if (s.getName().equals(""))
					s.setName(form.getName());
				if (s.getDescription().equals(""))
					s.setDescription(form.getDescription());
			}
			s.setItem(item);
		});
		item.setTranslations(translations);
		return item;
	}

}
