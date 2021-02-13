package com.foxminded.university.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "PERSONS")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {

	public Person() {

	}

	@Id
	@Column(name = "PERSON_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "USERS_PASSWORD")
	private String password;

	@Column(name = "FACULTY")
	private String faculty;

	@Enumerated(EnumType.STRING)
	@Column(name = "ROLE")
	private Role role;

	public Person(String name, String password, String faculty, Role role) {
		this.faculty = faculty;
		this.name = name;
		this.password = password;
		this.role = role;
	}

	public Person(int id, String name, String password, String faculty, Role role) {
		this.id = id;
		this.faculty = faculty;
		this.name = name;
		this.password = password;
		this.role = role;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return String.format("%s %d| %s %s %s", this.getRole().toString(), this.getId(), this.getName(),
				this.getPassword(), this.getFaculty());
	}

}
