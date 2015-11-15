package com.huai.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huai.beans.Course;
import com.huai.service.CourseService;
import com.huai.service.LoginService;
import com.huai.utils.MyDateFormat;;

public class Mytest {

	public static void main(String[] args) {
		//得到spring容器的对象
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/applicationContext.xml");
		//得到LoginService这个bean，即LoginService的对象
		CourseService  courseService = context.getBean(CourseService.class);
		//调用LoginService对象的方法
		List<Course> courseList =  courseService.getCourseByTeacherId(1);
		
		for(Course c:courseList){
			System.out.println(c.getCourseID()+"   "+c.getCourseName()+"   "+c.getTeacherID()+"   "+c.getBuildDate());
			
		}
	}
}
