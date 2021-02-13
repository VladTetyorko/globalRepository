package com.foxminded.university.forms;

import java.time.DayOfWeek;

public class LectureForm {
	private Integer lectureId;
	private Integer timeSlot;
	private int teacherId;
	private int courseId;
	private int groupId;
	private Integer roomNumber;
	private Integer week;
	private DayOfWeek weekday;

	public LectureForm(Integer lectureId, int timeSlot, int teacherId, int courseId, int groupId, int roomNumber,
			int week, DayOfWeek weekday) {
		this.setLectureId(lectureId);
		this.setTimeSlot(timeSlot);
		this.setRoomNumber(roomNumber);
		this.setTeacherId(teacherId);
		this.setCourseId(courseId);
		this.setGroupId(groupId);
		this.setWeek(week);
		this.setWeekday(weekday);
	}

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

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
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

	public void setWeekday(DayOfWeek weekday) {
		this.weekday = weekday;
	}

}
