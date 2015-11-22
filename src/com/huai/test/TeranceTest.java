package com.huai.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huai.beans.Student;
import com.huai.beans.Teacher;
import com.huai.persistence.StudentMapper;
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
		/*List<Student> s = ss.getStudentsInTheCourse("");
		for (Student s1 : s) {
			System.out.println(s1.getName());
		}*/
		
		Student s = new Student();
		s.setName("kate");
		s.setPassword("1234567");
		s.setSex("女");
		s.setStudentNO("12346789");
		s.setClazz("0491301");
		
//		ss.addStudentToTheCourse(s, 3);
		
//		ss.deleteStudentFromTheCourse("42341343", 2);
		
		List<Student> students = ss.getStudentsInTheCourse(1);
		for (Student student : students)
			System.out.println(student.getName()+", "+student.getStudentNO());
		
		
		
		
	}
}
