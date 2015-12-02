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
}
