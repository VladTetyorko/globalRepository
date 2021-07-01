package com.census.forms;

import com.census.entities.SettingValue;

public class SettingValueForm {

	private int id;
	private int userId;
	private int globalSettingId;
	private String globalSettingName;

	private int value;

	public SettingValueForm() {
	}

	public SettingValueForm(int id, int userId, int globalSettingId, String globalSettingName, int value) {
		super();
		this.id = id;
		this.userId = userId;
		this.globalSettingId = globalSettingId;
		this.globalSettingName = globalSettingName;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getGlobalSettingId() {
		return globalSettingId;
	}

	public void setGlobalSettingId(int globalSettingId) {
		this.globalSettingId = globalSettingId;
	}

	public String getGlobalSettingName() {
		return globalSettingName;
	}

	public void setGlobalSettingName(String globalSettingName) {
		this.globalSettingName = globalSettingName;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static SettingValueForm format(SettingValue neededValue) {
		SettingValueForm form = new SettingValueForm(neededValue.getId(), neededValue.getUser().getId(),
				neededValue.getGlobalSetting().getId(), neededValue.getGlobalSetting().getName(),
				neededValue.getValue());
		return form;
	}

	@Override
	public String toString() {
		return String.format("|id=%s userId=%s globalSettingId=%s value=%s|", this.id, this.getUserId(),
				this.getGlobalSettingId(), this.getValue());
	}
}
