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
	 * 根据studentID获取学生对象
	 * @param studentNO
	 * @return
	 */
	Student getStudentByStudentID(int studentID);
	/**
	 * 往student表中添加一个学生
	 * @param student
	 */
	int addStudent(Student student) throws Exception;
	/**
	 * 将学生添加到一个课程中
	 * @param student
	 * @param courseId
	 */
	void addStudentToCourse(int studentID, int courseId) throws Exception;
	/**
	 * 根据课程号得到在此门课程中的学生的studentID
	 * @param courseId
	 * @return
	 */
	List<Integer> getStudentIDByCourseId(int courseId);
	/**
	 * 把学生从一个课程中删除
	 * @param studentID
	 * @param courseId
	 */
	void deleteStudentFromCourse(int studentID, int courseId) throws Exception;

	/**
	 * 根据课程id查询学生
	 * @param courseID
	 * @return
	 * liangyihuai
	 */
	List<Student> getStudents(int courseID);
	/**
	 * 获得当前最大的studentID
	 * @return
	 */
	int getMaxStudentID();
	/**
	 * 删除student表中的学生记录
	 * @param studentID
	 */
	void deleteStudent(int studentID) throws Exception;
}
