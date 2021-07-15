package com.census.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.census.entities.Item;
import com.census.entities.ItemTranslation;
import com.census.validation.FieldConstraint;

@Component
public class ItemForm {

	public ItemForm() {
	}

	public ItemForm(int id, String name, String description, int categoryId, int locationId, int ownerId) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.categoryId = categoryId;
		this.locationId = locationId;
		this.ownerId = ownerId;
	}

	private int id;

	@FieldConstraint
	private String name;

	private String description;

	private int categoryId;

	private int locationId;

	private int ownerId;

	private List<ItemTranslation> translations;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public List<ItemTranslation> getTranslations() {
		return translations;
	}

	public void setTranslations(List<ItemTranslation> list) {
		this.translations = list;
	}

	public static ItemForm formatEmpty() {
		ItemForm form = new ItemForm();
		{
			List<ItemTranslation> emptyTranslations = new ArrayList<ItemTranslation>();
			emptyTranslations.add(new ItemTranslation(0, "en", "", "", null));
			emptyTranslations.add(new ItemTranslation(0, "ua", "", "", null));
			emptyTranslations.add(new ItemTranslation(0, "ru", "", "", null));
			form.setTranslations(emptyTranslations);
		}
		return form;
	}

	public static ItemForm format(Item neededItem, String lang) {
		ItemForm form = new ItemForm();
		Map<String, ItemTranslation> translationList = neededItem.getTranslationList();
		form.setId(neededItem.getId());
		form.setLocationId(neededItem.getLocation().getId());
		form.setCategoryId(neededItem.getCategory().getId());
		form.setOwnerId(neededItem.getOwner().getId());
		if (!neededItem.getTranslations().isEmpty()) {
			form.setTranslations(neededItem.getTranslations());
			translationList.forEach((t, s) -> {
				if (t.equals(lang)) {
					form.setName(s.getName());
					form.setDescription(s.getDescription());
				}
			});
		} else {
			List<ItemTranslation> emptyTranslations = new ArrayList<ItemTranslation>();
			emptyTranslations.add(new ItemTranslation(0, "en", "", "", null));
			emptyTranslations.add(new ItemTranslation(0, "ua", "", "", null));
			emptyTranslations.add(new ItemTranslation(0, "ru", "", "", null));
			form.setTranslations(emptyTranslations);
		}
		return form;
	}

	@Override
	public String toString() {
		return "ItemForm [id=" + id + ", name=" + name + ", description=" + description + ", categoryId=" + categoryId
				+ ", locationId=" + locationId + ", ownerId=" + ownerId + "]" + "list=" + translations;
	}

}
