package com.huai.persistence;

import java.util.List;

import com.huai.beans.Homework;


public interface HomeworkMapper {
	
	/**
	 * 通过教师ID获取作业列表
	 * @param teacherID
	 * @return
	 */
	List<Homework> getHomeworks(int teacherID);

	void giveHomework(Homework homework);
}
