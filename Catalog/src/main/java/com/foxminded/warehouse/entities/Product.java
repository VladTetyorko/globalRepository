package com.foxminded.warehouse.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "PRODUCTS")
@SecondaryTable(name = "CATEGORY_PRODUCT", pkJoinColumns = @PrimaryKeyJoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID"))
public class Product {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	int id;

	@NotNull
	@Column(name = "PRODUCT_NAME")
	String name;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	List<PropertyValue> properties;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "CATEGORY_ID", table = "CATEGORY_PRODUCT")
	Category category;

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

	public List<PropertyValue> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyValue> properties) {
		this.properties = properties;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
