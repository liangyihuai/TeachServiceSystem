package com.huai.persistence;

import java.util.Date;
import java.util.List;

import com.huai.beans.Homework;

public interface StudentHomeworkMapper {
	
	/**
	 * 提交作业
	 * @param studentID
	 * @param content
	 * @return
	 */
	void doHomework(int studentID, int homeworkID, String content, Date time);
	
	/**
	 * 获取学生作业内容
	 * @param studentID
	 * @param homeworkID
	 * @return
	 */
	String getContent(int studentID, int homeworkID);
	
	/**
	 * 获取学生作业内容以判断作业是否修改成功
	 * @param studentHomeworkID
	 * @return
	 */
	String getContentById(int studentHomeworkID);
	
	/**
	 * 学生修改作业
	 * @param studentHomeworkID
	 * @param studentID 
	 * @param content
	 */
	void modifyHomework(int studentHomeworkID, int studentID, String content);

	int getScore(int homeworkID, int studentID);
	
	
}
