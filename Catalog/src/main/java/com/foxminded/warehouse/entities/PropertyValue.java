package com.foxminded.warehouse.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PROPERTY_VALUES")
public class PropertyValue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	@JsonIgnore
	int Id;

	@ManyToOne()
	@JoinColumn(name = "PROPERTY_ID", referencedColumnName = "ID")
	Property globalProperty;

	@NotNull
	@Column(name = "PROPERTY_VALUE")
	String value;

	@ManyToOne()
	@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
	@JsonBackReference
	Product product;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public Property getGlobalProperty() {
		return globalProperty;
	}

	public void setGlobalProperty(Property globalProperty) {
		this.globalProperty = globalProperty;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
