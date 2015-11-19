package com.huai.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huai.beans.Student;
import com.huai.beans.Teacher;
import com.huai.service.LoginService;
import com.huai.service.StudentService;
import com.huai.service.TeacherService;

public class TeranceTest {

	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/applicationContext.xml");
		
		/*TeacherService ts = context.getBean(TeacherService.class);
		Teacher t = ts.getTeacherByName("terance");
		System.out.println(t);
		ts.changePassword(t, "654321");*/
		
		StudentService ss = context.getBean(StudentService.class);
		List<Student> s = ss.getStudentsInTheCourse("");
		for (Student s1 : s) {
			System.out.println(s1.getName());
		}
	}
}
