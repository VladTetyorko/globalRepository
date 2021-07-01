package com.census.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GLOBAL_SETTINGS")
public class Setting {
	@Id
	@Column(name = "ID")
	private Integer id;

	@Column(name = "name")
	private String name;

	@OneToMany
	List<SettingValue> values;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SettingValue> getValues() {
		return values;
	}

	public void setValues(List<SettingValue> values) {
		this.values = values;
	}

}
