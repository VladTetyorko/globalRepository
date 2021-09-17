package com.census.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SETTING_VALUE")
public class SettingValue {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "VALUE")
	private int value;

	@ManyToOne()
	@JoinColumn(name = "SETTING_ID", referencedColumnName = "ID")
	private Setting globalSetting;

	@ManyToOne()
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private User user;

	public SettingValue() {

	}

	public SettingValue(int id, Setting globalSetting, User user, int value) {
		this.id = id;
		this.globalSetting = globalSetting;
		this.user = user;
		this.value = value;
	}

	public SettingValue(Setting globalSetting, User user, int value) {
		this.globalSetting = globalSetting;
		this.user = user;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Setting getGlobalSetting() {
		return globalSetting;
	}

	public void setGlobalSetting(Setting globalSetting) {
		this.globalSetting = globalSetting;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return String.format("id=%s User=%s | name= %s value=%s", this.getId(), this.user.getUsername(),
				this.globalSetting.getName(), this.value);
	}

}
