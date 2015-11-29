package com.huai.beans;

public class Student {

	private int studentID;
	private String name;
	private String password;
	private String sex;
	private String studentNO;
	//班级，注意和课程（course）的区别
	private String clazz;
	private String major;
	//学院
	private String college;

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getClazz(){
		return this.clazz;
	}
	public void setClazz(String clazz){
		this.clazz = clazz;
	}
	
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getStudentNO() {
		return studentNO;
	}
	public void setStudentNO(String studentNO) {
		this.studentNO = studentNO;
	}
	
}
