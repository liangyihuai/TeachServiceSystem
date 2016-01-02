package com.huai.service;

import com.huai.beans.Teacher;

public interface TeacherService {

	/**
	 * 根据名字得到“老师”对象
	 * @param name
	 * @return
	 */
	Teacher getTeacherByName(String name);
	/**
	 * 修改密码
	 * @param teacher 老师对象
	 */
	boolean changePassword(Teacher teacher);

	/**
	 * 老师注册
	 * @return
	 * @throws Exception
	 */
	boolean signIn(Teacher teacher) throws Exception;
}
