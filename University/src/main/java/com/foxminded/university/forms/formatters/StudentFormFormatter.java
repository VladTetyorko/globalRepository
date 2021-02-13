package com.foxminded.university.forms.formatters;

import org.springframework.stereotype.Component;

import com.foxminded.university.entities.Role;
import com.foxminded.university.entities.personDetails.Student;
import com.foxminded.university.forms.StudentForm;
import com.foxminded.university.services.GroupService;

@Component
public class StudentFormFormatter {

	private GroupService groupServise;

	public StudentFormFormatter(GroupService groupServise) {
		this.groupServise=groupServise;
	}

	public  Student getStudent(StudentForm form) {
		return new Student(form.getId(), form.getName(), form.getPassword(), form.getFaculty(), Role.STUDENT,
				groupServise.find(form.getGroupId()).get());
	}

}
