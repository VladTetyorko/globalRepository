package com.census.entities;

import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "globalSetting")
	List<SettingValue> values;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "globalSetting")
	List<LanguageValues> translations;

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

	public List<LanguageValues> getTranslations() {
		return translations;
	}

	public void setTranslations(List<LanguageValues> translations) {
		this.translations = translations;
	}

	public HashMap<String, String> getAllTranslationsMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		this.translations.forEach(translation -> {
			map.put(translation.getLanguage(), translation.getValue());
		});
		return map;
	}

	@Override
	public String toString() {
		return String.format("Setting %s {%s}", this.getName(), this.getTranslations().toString());
	}

}
