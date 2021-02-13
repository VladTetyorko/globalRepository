package com.foxminded.university.entities;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.foxminded.university.entities.personDetails.Student;
import com.foxminded.university.validators.GroupNameConstraint;

@Entity
@Table(name = "UNIVERSITY_GROUPS")
public class Group {

	public Group() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GROUP_ID")
	private int groupId;

	@GroupNameConstraint
	@Column(name = "GROUP_NAME")
	private String groupName;

	@OneToMany(fetch = FetchType.LAZY, targetEntity = Student.class, cascade = CascadeType.ALL, mappedBy = "group")
	private List<Student> students;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "group", fetch = FetchType.LAZY)
	private List<Lecture> lectures;

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getId() {
		return groupId;
	}

	public void setId(Integer id) {
		this.groupId = id;
	}

	@Override
	public String toString() {
		return String.format("#%d - %s", this.groupId, this.groupName);
	}

	public int countStudents() {
		AtomicInteger atomicInt = new AtomicInteger(0);
		this.students.stream().forEach(s -> {
			atomicInt.incrementAndGet();
		});
		return atomicInt.get();
	}

}
