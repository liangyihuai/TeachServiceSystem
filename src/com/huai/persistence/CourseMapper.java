package com.huai.persistence;


import java.util.List;

import com.huai.beans.Course;

public interface CourseMapper {
	/**
	 * 根据老师ID得到课程对象列表
	 * @param id
	 * @return Course
	 */
//	ArrayList<Course> getCourseByTeacherID(int teacherID);
	List<Course> getCourseByTeacherId(int teacherId);
	
	
}
