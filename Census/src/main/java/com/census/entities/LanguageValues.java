package com.census.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "I18N_SETTINGS")
public class LanguageValues {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "LANGUAGE")
	private String language;

	@Column(name = "TRANSLATION")
	private String value;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "SETTING_ID", referencedColumnName = "ID")
	private Setting globalSetting;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Setting getGlobalSetting() {
		return globalSetting;
	}

	public void setGlobalSetting(Setting globalSetting) {
		this.globalSetting = globalSetting;
	}

	@Override
	public String toString() {
		return String.format(" %s %s ", this.getLanguage(), this.getValue());
	}

}
