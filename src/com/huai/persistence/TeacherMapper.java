package com.huai.persistence;

import com.huai.beans.Teacher;

public interface TeacherMapper {
	/**
	 * 根据名字得到“老师”对象
	 * @param name
	 * @return
	 */
	Teacher getTeacherByName(String name);
	/**
	 * 更新记录
	 */
	void update(Teacher teacher);

	/**
	 * 增加一个老师
	 * @param teacher
	 * @return
	 */
	int addTeacher(Teacher teacher);
}
