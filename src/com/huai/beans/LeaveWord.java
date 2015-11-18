package com.huai.beans;

public class LeaveWord {

	private int leaveWordID;
	private String headline;
	private String content;
	private int teacherID;
	private int studentID;
	private int courseID;
	
	public int getCourseID() {
		return courseID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	public int getLeaveWordID() {
		return leaveWordID;
	}
	public void setLeaveWordID(int leaveWordID) {
		this.leaveWordID = leaveWordID;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getTeacherID() {
		return teacherID;
	}
	public void setTeacherID(int teacherID) {
		this.teacherID = teacherID;
	}
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	
	
}
