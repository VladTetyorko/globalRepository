package com.foxminded.university.entities.personDetails;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.foxminded.university.entities.Lecture;
import com.foxminded.university.entities.Person;
import com.foxminded.university.entities.Role;

@Entity
@Table(name="TEACHERS")
@PrimaryKeyJoinColumn(name = "PERSON_ID")
public class Teacher extends Person {
	
	public Teacher() {}
	
	@Column(name="ACADEMIC_DEGREE", nullable = false)
	private String academicDegree;
	
	@OneToMany(cascade = CascadeType.MERGE,mappedBy = "teacher", fetch=FetchType.LAZY)
	private List<Lecture> lectures;

	public Teacher(int id, String name, String password, String faculty, Role role, String academicDegree) {
		super(id, name, password, faculty, role);
		this.academicDegree = academicDegree;
	}

	public String getAcademicDegree() {
		return academicDegree;
	}

	public void setAcademicDegree(String academicDegree) {
		this.academicDegree = academicDegree;
	}

	@Override
	public String toString() {
		return String.format("%s|%s \n", super.toString(), this.getAcademicDegree());
	}

}
