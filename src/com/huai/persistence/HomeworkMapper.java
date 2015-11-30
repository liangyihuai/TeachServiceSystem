package com.huai.persistence;

import java.util.List;

import com.huai.beans.Homework;
import com.huai.beans.Student;
import com.huai.beans.StudentHomeWorkRelation;


public interface HomeworkMapper {
	
	/**
	 * 通过课程ID获取作业列表
	 * @param courseID
	 * @return
	 */
	List<Homework> getHomeworks(int courseID);
	
	/**
	 * 添加老师布置的作业
	 * @param homework
	 */
	void giveHomework(Homework homework);
	
	/*
	 * 通过作业ID获取学生已提交的作业列表
	 */
	List<StudentHomeWorkRelation> getStudentHomework(int homeworkID);
	
	/**
	 * 通过学生ID获取学生对象
	 * @param studentID
	 * @return
	 */
	Student getStudent(int studentID);
	
	/**
	 * 获取未提交作业的学生
	 * @return
	 */
	List<Student> getUncommitedStudents();
	
	/**
	 * 批改作业
	 * @param comment
	 * @param score
	 */
	void correctHomework(String comment, int score ,String studentNO, int homeworkID);
	
	/**
	 * 获取作业的状态
	 * @param studentNO
	 * @return
	 */
	String getStatus(String studentNO, int homeworkID);
}
