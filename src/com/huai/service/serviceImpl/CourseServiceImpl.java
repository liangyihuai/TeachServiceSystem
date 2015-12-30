package com.huai.service.serviceImpl;

import java.util.List;
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
	public void setCourseMapper(CourseMapper courseMapper) {
		this.courseMapper = courseMapper;
	}
	
	
	/**
	 * 根据老师ID查询课程列表
	 * @param teacherId  老师ID
	 * @return 
	 */
	@Override
	public List<Course> getCourseByTeacherId(int teacherId) {
		Assert.notNull(teacherId, "teacherId must not be null");
		List<Course> courseList= courseMapper.getCourseByTeacherId(teacherId);
		return courseList;
	}

	@Override
	public boolean addCourseByTeacherID(Course course) {
		int flag = courseMapper.addCourseByTeacherID(course);
		if(flag > 0 ){
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteCourseByCourseID(int courseID) {
		int flag = courseMapper.deleteCourseByCourseID(courseID);
		if(flag > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<Course> getCourseByStudentNO(String studentNO) {
		return courseMapper.getCourseByStudentNO(studentNO);
	}

}
