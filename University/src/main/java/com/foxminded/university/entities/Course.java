package com.foxminded.university.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "COURSES")
public class Course {

	@Id
	@Column(name = "COURSE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer courseId;

	@Column(name = "COURSE_NAME")
	private String courseName;

	@NotNull
	@Size(min = 4, max = 10)
	@Column(name = "COURSE_DESCRIPTION")
	private String courseDescription;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "course", fetch = FetchType.LAZY)
	private List<Lecture> lectures;

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public List<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}

	public String toString() {
		return String.format("#%d - %s -%s", this.courseId, this.courseName, this.courseDescription);
	}

}
