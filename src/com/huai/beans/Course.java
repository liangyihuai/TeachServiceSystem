package com.huai.beans;

import java.util.*;

public class Course {

	private int courseID;
	private String courseName;
	private int teacherID;
	private Date buildDate;
	private String courseDescription;
	private int commonPercent;
	private int finalPercent;
	
	
	public int getCommonPercent() {
		return commonPercent;
	}
	public void setCommonPercent(int commonPercent) {
		this.commonPercent = commonPercent;
	}
	public int getFinalPercent() {
		return finalPercent;
	}
	public void setFinalPercent(int finalPercent) {
		this.finalPercent = finalPercent;
	}
	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
	public int getCourseID() {
		return courseID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getTeacherID() {
		return teacherID;
	}
	public void setTeacherID(int teacherID) {
		this.teacherID = teacherID;
	}
	public Date getBuildDate() {
		return buildDate;
	}
	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}
	
	
}
