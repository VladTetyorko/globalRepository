package com.foxminded.university.forms;

import com.foxminded.university.entities.Person;
import com.foxminded.university.entities.Role;
import com.foxminded.university.validators.StudentsGroupConstraint;

public class StudentForm extends Person {

	@StudentsGroupConstraint
	private int groupId;

	public StudentForm(Integer id, String name, String password, String faculty, int groupId) {
		super(id, name, password, faculty, Role.STUDENT);
		this.groupId = groupId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

}
