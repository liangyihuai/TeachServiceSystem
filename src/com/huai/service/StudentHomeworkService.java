package com.huai.service;

import java.util.Date;
import java.util.List;

import com.huai.beans.Homework;
import com.huai.beans.StudentHomeWorkRelation;

public interface StudentHomeworkService {
	/**
	 * 根据courseID和teacherID获取作业列表
	 */
	public List<Homework> getHomeworks(int courseID);
	
	/**
	 * 学生提交作业
	 * @param studentID
	 * @param content
	 * @return
	 */
	public Boolean doHomework(int studentID, int homeworkID, String content, Date time);
	
	/**
	 * 学生修改作业
	 * @param studentID 
	 * @param studentHomeworkID
	 * @param content
	 * @param time 
	 * @return
	 */
	public Boolean modifyHomework(int homeworkID, int studentID, String content, Date time);
	
	/**
	 * 获取学生作业内容，如果未提交则返回null
	 * @param homeworkID
	 * @param studentID
	 * @return 
	 */
	public String getContent(int homeworkID, int studentID);

	public int getScore(int homeworkID, int studentID);

	public String getComment(int homeworkID, int studentID);

	public StudentHomeWorkRelation getStudentHomework(int homeworkID,
			int studentID);
}
