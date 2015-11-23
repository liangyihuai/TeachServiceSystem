package com.huai.service;

import java.util.List;

import com.huai.beans.Homework;


public interface HomeworkService {
	
	/**
	 * 根据教师ID获取作业列表
	 * @param teacherID
	 * @return
	 */
	public List<Homework> getHomework(int teacherID);
	
	/**
	 * 布置作业
	 * @param homework
	 * @return
	 */
	public void giveHomework(Homework homework);
	
	/**
	 * 判断布置作业是否成功
	 * @param teacherID
	 * @param content
	 * @return
	 */
	public Boolean isAddHomeworkSuccess(int teacherID, String content);
}
