package com.test.calendar.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.test.calendar.CalendarImpl;

@Entity
@Table(name = "IMPORTANT_DATES")
public class DateEntity implements Comparable<DateEntity> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;

	@Column(name = "DAY")
	private int day;

	@Column(name = "MONTH")
	private int month;

	@Column(name = "YEAR")
	private int year;

	@OneToMany(mappedBy = "date", cascade = CascadeType.ALL)
	private List<Task> tasks;

	public DateEntity() {

	}

	public DateEntity(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getStringMonth() {
		return CalendarImpl.MONTHS[this.month - 1];
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public List<Task> getTasks() {
		if (this.tasks == null)
			return new ArrayList<Task>();
		Collections.sort(tasks, new Comparator<Task>() {
			@Override
			public int compare(Task o1, Task o2) {
				return o1.getTime().compareTo(o2.getTime());
			}
		});
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public int getTasksSize() {
		return this.tasks.size();
	}

	public String getFormatedDate() {
		return String.format("%d.%d.%d", this.day, this.month, this.year);
	}

	@Override
	public String toString() {
		return String.format("%d.%d.%d ----%s", this.day, this.month, this.year, this.tasks);
	}

	@Override
	public int compareTo(DateEntity date) {
		return (int) (this.day - date.getDay());
	}

}
