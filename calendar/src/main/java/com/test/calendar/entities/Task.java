package com.test.calendar.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity
@Table(name = "TASKS")
@SecondaryTable(name = "DAY_TASKS", pkJoinColumns = @PrimaryKeyJoinColumn(name = "TASK_ID", referencedColumnName = "ID"))
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;

	@Column(name = "TASK_TIME")
	private String time;

	@Column(name = "NAME")
	private String name;

	@ManyToOne
	@JoinColumn(table = "DAY_TASKS", name = "DATE_ID")
	private Date date;

	public Task() {

	}

	public Task(String time, String name, Date date) {
		this.time = time;
		this.name = name;
		this.date = date;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return String.format("task: %s on %s  id=%s ", this.getName(), this.getTime(), this.getID());
	}
}
