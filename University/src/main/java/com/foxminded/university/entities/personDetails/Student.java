package com.foxminded.university.entities.personDetails;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.foxminded.university.entities.Group;
import com.foxminded.university.entities.Person;
import com.foxminded.university.entities.Role;

@Entity
@Table(name = "STUDENTS")
@PrimaryKeyJoinColumn(name = "PERSON_ID")
public class Student extends Person {

	public Student() {
	}

	@ManyToOne(cascade = CascadeType.MERGE, optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_ID", nullable = true)
	private Group group;

	public Student(int id, String name, String password, String faculty, Role role, Group group) {
		super(id, name, password, faculty, role);
		this.group = group;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return String.format("%s|%d \n", super.toString(), this.getGroup().getId());
	}

}
