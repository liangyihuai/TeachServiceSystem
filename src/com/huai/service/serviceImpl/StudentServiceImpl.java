package com.huai.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.huai.beans.Student;
import com.huai.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Override
	public List<Student> getStudentsInTheCourse(String courseId) {
		List<Student> students = new ArrayList<Student>();
		
		return students;
	}

	@Override
	public void addStudent(Student student, String courseId) {
		
	}

	@Override
	public void deleteStudentFromTheCourse(String studentNo, String courseId) {
		
	}

}
