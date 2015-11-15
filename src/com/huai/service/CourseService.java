package com.huai.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.huai.beans.Course;

public interface CourseService {

	List<Course> getCourseByTeacherId(int teacherId);
}
