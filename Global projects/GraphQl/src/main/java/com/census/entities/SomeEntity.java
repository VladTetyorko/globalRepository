package com.census.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class SomeEntity implements Serializable {
	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "type", nullable = false)
	private String type;
	@Column(name = "string_field2", nullable = false)
	private String string_field2;
	@Column(name = "string_field3")
	private String string_field3;
	@Column(name = "local_date")
	private LocalDate launchDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getString_field2() {
		return string_field2;
	}

	public void setString_field2(String string_field2) {
		this.string_field2 = string_field2;
	}

	public String getString_field3() {
		return string_field3;
	}

	public void setString_field3(String string_field3) {
		this.string_field3 = string_field3;
	}

	public LocalDate getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(LocalDate launchDate) {
		this.launchDate = launchDate;
	}
}
