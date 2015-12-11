package com.huai.service;

import java.util.List;

import com.huai.beans.Course;

public interface CourseService {

	/**
	 * 根据老师ID查询课程列表
	 * @param teacherId
	 * @return
	 */
	List<Course> getCourseByTeacherId(int teacherId);
	
	/**
	 * 通过老师ID添加课程
	 * @param course
	 * @return
	 */
	boolean addCourseByTeacherID(Course course);
	
	
	/**
	 * 通过课程ID删除课程
	 * @param course
	 */
	boolean deleteCourseByCourseID(int courseID);

	/**
	 * 根据学号查询学生所选课程列表
	 * @param studentNO
	 * @return
	 */
	List<Course> getCourseByStudentNO(String studentNO);
}
