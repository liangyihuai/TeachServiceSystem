package com.huai.beans;

import java.util.Date;

public class Schedule {

	private int scheduleID;
	private int courseID;
	private Date courseTime;
	private String arrangement;
	public int getScheduleID() {
		return scheduleID;
	}
	public void setScheduleID(int scheduleID) {
		this.scheduleID = scheduleID;
	}
	public int getCourseID() {
		return courseID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	public Date getCourseTime() {
		return courseTime;
	}
	public void setCourseTime(Date courseTime) {
		this.courseTime = courseTime;
	}
	public String getArrangement() {
		return arrangement;
	}
	public void setArrangement(String arrangement) {
		this.arrangement = arrangement;
	}
	
	
}
