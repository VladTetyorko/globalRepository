package com.foxminded.university.entities.personDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.foxminded.university.entities.Person;
import com.foxminded.university.entities.Role;

@Entity
@Table(name="ADMINS")
@PrimaryKeyJoinColumn(name = "PERSON_ID")
public class Admin extends Person {
	
	public Admin() {}
	
	@Column(name="POSITION")
	private String position;

	public Admin(Integer id, String name, String password, String faculty, Role role, String position) {
		super(id, name, password, faculty, role);
		this.position = position;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return String.format("%s|%s", super.toString(), this.getPosition());
	}

}
