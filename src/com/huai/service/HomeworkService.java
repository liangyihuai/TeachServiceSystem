package com.huai.service;

import java.util.List;

import com.huai.beans.Homework;
import com.huai.beans.Student;
import com.huai.beans.StudentHomeWorkRelation;


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
	
	/**
	 * 通过homeworkID获取学生作业
	 * @param homeworkID
	 * @return
	 */
	public List<StudentHomeWorkRelation> getStudentHomework(int homeworkID);
	
	/**
	 * 通过学生ID获取学生对象
	 * @param studentID
	 * @return
	 */
	public Student getStudent(int studentID);

	public List<Student> getUncommitedStudents();
}
