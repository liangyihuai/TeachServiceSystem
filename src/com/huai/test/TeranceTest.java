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
		s.setName("faith");
		s.setPassword("111111");
		s.setSex("女");
		s.setStudentNO("12346789");
		s.setClazz("0491301");
		
		try {
			System.out.println(ss.addStudentToTheCourse(s, 3));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		System.out.println(ss.deleteStudentFromTheCourse("12346789", 3));
		
		List<Student> students = ss.getStudentsInTheCourse(3);
		for (Student student : students)
			System.out.println(student.getName()+", "+student.getStudentNO());
		
		
		/*----------------------------------------my cache--------------------------------------------*/
		/*
		public int addStudentToTheCourse(Student student, int courseId){
			if (studentMapper.getStudentByStudentNO(student.getStudentNO()) == null) {
				try {
					studentMapper.addStudent(student);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (studentMapper.getStudentByStudentNO(student.getStudentNO()) == null)
					return 0;
				try {
					studentMapper.addStudentToCourse(student.getStudentNO(), courseId);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//判断是否添加成功
				List<String> studentNOs1 = studentMapper.getStudentNOByCourseId(courseId);
				if (studentNOs1.contains(student.getStudentNO()))
					return 1;//添加成功
				else
					return 0;
			} else {
				//判断学生是否已经在该课程中
				List<String> studentNOs = studentMapper.getStudentNOByCourseId(courseId);
				if (studentNOs.contains(student.getStudentNO()))
					return 2;//已存在
				// 不在则添加
				try {
					studentMapper.addStudentToCourse(student.getStudentNO(), courseId);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//判断是否添加成功
				List<String> studentNOs1 = studentMapper.getStudentNOByCourseId(courseId);
				if (studentNOs1.contains(student.getStudentNO()))
					return 1;//添加成功
				return 0;//添加失败
			}
		}*/
		
		/*
		public int deleteStudentFromTheCourse(String studentNO, int courseId) {
			//删除
			studentMapper.deleteStudentFromCourse(studentNO, courseId);
			// 判断学生是否在该课程中
			List<String> studentNOs1 = studentMapper.getStudentNOByCourseId(courseId);
			if (!studentNOs1.contains(studentNO))
				return 1;// 删除成功
			return 0;
		}*/
	}
}
