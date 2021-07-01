package com.census.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "ITEMS")
@SecondaryTables({
		@SecondaryTable(name = "ITEM_USER", pkJoinColumns = @PrimaryKeyJoinColumn(name = "ITEM_ID", referencedColumnName = "ID")),
		@SecondaryTable(name = "ITEM_CATEGORY", pkJoinColumns = @PrimaryKeyJoinColumn(name = "ITEM_ID", referencedColumnName = "ID")),
		@SecondaryTable(name = "ITEM_LOCATION", pkJoinColumns = @PrimaryKeyJoinColumn(name = "ITEM_ID", referencedColumnName = "ID")) })
public class Item extends TableEntity {

	public Item() {
	}

	public Item(int id, @NotBlank(message = "Name cannot be empty.") String name, String description, Category category,
			Location location, User owner) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.location = location;
		this.owner = owner;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Name cannot be empty.")
	@Column(name = "NAME")
	private String name;

	@Column(name = "Description")
	private String description;

	@ManyToOne
	@JoinColumn(table = "ITEM_CATEGORY", name = "CATEGORY_ID")
	private Category category;

	@ManyToOne
	@JoinColumn(table = "ITEM_LOCATION", name = "LOCATION_ID")
	private Location location;

	@ManyToOne
	@JoinColumn(table = "ITEM_USER", name = "USER_ID")
	private User owner;

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", category=" + category.getName()
				+ ", location=" + location.getName() + ", owner=" + owner.getUsername() + "]";
	}

	@Override
	public String[] toTableRow() {
		Integer id = getId();
		String row[] = { id.toString(), this.getName(), this.getDescription(), this.getCategory().getName(),
				this.getLocation().getName(), this.getOwner().getUsername() };
		return row;
	}

	@Override
	public String[] getTableHeader() {
		String row[] = { "ID", "NAME", "Description", "Category", "Location", "Owner" };
		return row;
	}

}
