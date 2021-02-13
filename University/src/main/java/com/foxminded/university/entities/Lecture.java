package com.foxminded.university.entities;

import java.time.DayOfWeek;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.foxminded.university.entities.personDetails.Teacher;

@Entity
@Table(name = "LECTURES")
public class Lecture {

	@Id
	@Column(name = "LECTURE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer lectureId;

	@Column(name = "TIMESLOT")
	private int timeSlot;

	@ManyToOne()
	@JoinColumn(name = "TEACHER_ID")
	private Teacher teacher;

	@ManyToOne()
	@JoinColumn(name = "COURSE_ID")
	private Course course;

	@ManyToOne()
	@JoinColumn(name = "GROUP_ID")
	private Group group;

	@Column(name = "ROOM_NUMBER")
	private Integer roomNumber;

	@Column(name = "WEEK")
	private Integer week;

	@Enumerated(EnumType.STRING)
	@Column(name = "WEEKDAY")
	private DayOfWeek weekday;

	public Integer getLectureId() {
		return lectureId;
	}

	public void setLectureId(Integer lectureId) {
		this.lectureId = lectureId;
	}

	public int getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(int timeSlot) {
		this.timeSlot = timeSlot;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Integer getAudience() {
		return roomNumber;
	}

	public void setAudience(int audience) {
		this.roomNumber = audience;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public DayOfWeek getWeekday() {
		return weekday;
	}

	public void setWeekday(String dayOfWeek) {
		this.weekday = DayOfWeek.valueOf(dayOfWeek);
	}

	public void setWeekday(DayOfWeek weekday2) {
		this.weekday = weekday2;
	}

	@Override
	public String toString() {
		return String.format("%d) %s| %s| %s| on %s|timeslot:%d|room number:%d |", this.lectureId,
				this.course.getCourseName(), this.teacher.getName(), this.group.getGroupName(), this.weekday,
				this.timeSlot, this.roomNumber);
	}

}
