package com.huai.persistence;


import java.util.List;

import com.huai.beans.Course;

public interface CourseMapper {
	/**
	 * 根据老师ID得到课程对象列表
	 * @param id
	 * @return Course
	 */
	List<Course> getCourseByTeacherId(int teacherId); 
	
	/**
	 * 通过老师ID添加课程
	 * @param teacherID
	 * @return 
	 */
	int addCourseByTeacherID(Course course);
	
	/**
	 * 通过课程ID删除课程
	 * @param course
	 */
	int deleteCourseByCourseID(int courseID);
	
}
