package com.huai.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.huai.beans.Course;
import com.huai.persistence.CourseMapper;
import com.huai.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService{
	private CourseMapper courseMapper;

	@Autowired
	public void setStudentMapper(CourseMapper courseMapper) {
		this.courseMapper = courseMapper;
	}
	
	/**
	 * @param teacherId  老师ID
	 * @return 
	 */
	@Override
	public List<Course> getCourseByTeacherId(int teacherId,HttpServletRequest request) {
		Assert.notNull(teacherId, "teacherId must not be null");

		
		List<Course> courseList= courseMapper.getCourseByTeacherId(teacherId);
		
		return courseList;
	}

}
