package com.huai.persistence;

import com.huai.beans.Student;

public interface StudentMapper {

	/**
	 * 根据学生名字获取学生对象
	 * @param name
	 * @return
	 */
	Student getStudentByName(String name);
}
