package com.huai.persistence;

import java.util.List;

import com.huai.beans.Student;

public interface StudentMapper {

	/**
	 * 根据学生名字获取学生对象
	 * @param name
	 * @return
	 */
	Student getStudentByName(String name);
	/**
	 * 根据学号获取学生对象
	 * @param studentNO
	 * @return
	 */
	Student getStudentByStudentNO(String studentNO);
	/**
	 * 往数据库中添加一个学生
	 * @param student
	 */
	void addStudent(Student student);
	/**
	 * 将学生添加到一个课程中
	 * @param student
	 * @param courseId
	 */
	void addStudentToCourse(String studentNO, int courseId);
	/**
	 * 根据课程号得到在此门课程中的学生的学号
	 * @param studentNO
	 * @param courseId
	 * @return
	 */
	List<String> getStudentNOByCourseId(int courseId);
	/**
	 * 把学生从一个课程中删除
	 * @param studentNO
	 * @param courseId
	 */
	void deleteStudentFromCourse(String studentNO, int courseId);
}
