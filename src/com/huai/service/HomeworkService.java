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
	
	/**
	 * 获取未提交作业的学生
	 * @return
	 */
	public List<Student> getUncommitedStudents();
	
	/**
	 * 批改作业
	 * @param comment
	 * @param score
	 */
	public void correctHomework(String comment, int score, String studentNO, int homeworkID);
	
	/**
	 * 判断作业批改是否成功
	 * @param studentNO
	 * @return
	 */
	public Boolean isUpdateSuccess(String studentNO, int homeworkID);
}
