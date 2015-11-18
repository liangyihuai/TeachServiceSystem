package com.huai.beans;

/**
 * 课程进度表
 * @author Cedrus
 *
 */

public class Schedule {

	private int scheduleID;//课程进度ID
	private int courseID;//课程ID
	private String courseTime;//上课时间
	private String arrangement;//课程安排

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

	public String getCourseTime() {
		return courseTime;
	}

	public void setCourseTime(String courseTime) {
		this.courseTime = courseTime;
	}

	public String getArrangement() {
		return arrangement;
	}

	public void setArrangement(String arrangement) {
		this.arrangement = arrangement;
	}

}
