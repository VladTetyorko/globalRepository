package com.census.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "LOCATIONS")
public class Location extends TableEntity {

	public Location() {
	}

	public Location(String name) {
		this.name = name;
	}

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Name cannot be empty.")
	@Column(name = "NAME")
	private String name;

	@OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
	private List<Item> items;

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

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return String.format("Location id=%d name=%s", this.id, this.name);
	}

	@Override
	public String[] toTableRow() {
		Integer id = getId();
		String[] row = { id.toString(), getName() };
		return row;
	}

	@Override
	public String[] getTableHeader() {
		String[] row = { "ID", "NAME" };
		return row;
	}
}
