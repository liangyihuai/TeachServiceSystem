package com.huai.test;

import com.huai.beans.Student;
import com.huai.beans.Teacher;
import com.huai.persistence.StudentMapper;
import com.huai.persistence.TeacherMapper;
import com.huai.service.StudentService;
import com.huai.service.TeacherService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import java.util.List;

public class FinleyTest {

	public static void main(String[] args) throws Exception {
		//得到spring容器的对象
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/applicationContext.xml");
		TeacherService teacherService = (TeacherService)context.getBean(TeacherService.class);
		Teacher teacher = new Teacher();
		teacher.setName("liangyihuai");
		teacher.setCollege("jisuanji");

		System.out.println(teacherService.signIn(teacher));

	}
}
