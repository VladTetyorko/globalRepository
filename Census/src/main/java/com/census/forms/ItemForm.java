package com.census.forms;

import com.census.entities.Item;
import com.census.validation.FieldConstraint;

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

	public static ItemForm format(Item neededItem) {
		ItemForm form = new ItemForm(neededItem.getId(), neededItem.getName(), neededItem.getDescription(),
				neededItem.getCategory().getId(), neededItem.getLocation().getId(), neededItem.getOwner().getId());
		return form;
	}

	@Override
	public String toString() {
		return "ItemForm [id=" + id + ", name=" + name + ", description=" + description + ", categoryId=" + categoryId
				+ ", locationId=" + locationId + ", ownerId=" + ownerId + "]";
	}

}
