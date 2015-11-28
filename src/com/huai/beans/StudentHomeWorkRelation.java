package com.huai.beans;

import java.util.Date;

public class StudentHomeWorkRelation {

	private int studentHomeworkID;
	private int studentID;
	private int homeworkID;
	private String comment;
	private int score;
	private String content;
	private Date time;
	private String status;
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getStudentHomeworkID() {
		return studentHomeworkID;
	}
	public void setStudentHomeworkID(int studentHomeworkID) {
		this.studentHomeworkID = studentHomeworkID;
	}
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public int getHomeworkID() {
		return homeworkID;
	}
	public void setHomeworkID(int homeworkID) {
		this.homeworkID = homeworkID;
	}
	
	
}
