package com.huai.service;

import java.util.ArrayList;
import java.util.List;

import com.huai.beans.Student;

public interface StudentService {

	/**
	 * 获得此课程中的所有学生
	 * @param courseId
	 * @return List<Student>
	 */
	public List<Student> getStudentsInTheCourse(int courseId);
	/**
	 * 在此课程中增加一个学生
	 * @param student
	 * @param courseId
	 */
	public int addStudentToTheCourse(Student student, int courseId) throws Exception;
	/**
	 * 把一个学生从此课程中删除
	 * @param studentNo
	 * @param courseId
	 */
	public int deleteStudentFromTheCourse(String studentNO, int courseId);

	/**
	 * import students from excel from
	 * @return
	 * LiangYH
	 */
	boolean importStudents(List<ArrayList<String>> dyadic, int courseID);
}
