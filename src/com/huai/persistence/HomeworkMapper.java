package com.huai.persistence;

import java.util.List;

import com.huai.beans.Homework;


public interface HomeworkMapper {
	
	/**
	 * 通过课程ID获取作业列表
	 * @param courseID
	 * @return
	 */
	List<Homework> getHomeworks(int courseID);

	void giveHomework(Homework homework);
}
