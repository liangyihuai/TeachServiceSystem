package com.huai.service;

import java.util.List;

import com.huai.beans.Homework;


public interface HomeworkService {
	
	/**
	 * 根据课程ID获取作业列表
	 * @param courseID
	 * @return
	 */
	public List<Homework> getHomework(int courseID);
	
	/**
	 * 布置作业
	 * @param homework
	 * @return
	 */
	public void giveHomework(Homework homework);
	
	/**
	 * 判断布置作业是否成功
	 * @param courseID
	 * @param content
	 * @return
	 */
	public Boolean isAddHomeworkSuccess(int courseID, String content);
}
