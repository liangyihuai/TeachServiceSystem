package com.huai.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huai.beans.Student;
import com.huai.persistence.StudentMapper;
import com.huai.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{
	
	private StudentMapper studentMapper;
	
	@Autowired
	public void setStudentMapper(StudentMapper studentMapper) {
		this.studentMapper = studentMapper;
	}
	
	@Override
	public List<Student> getStudentsInTheCourse(int courseId) {
		List<Student> students = new ArrayList<Student>();
		List<String> studentNOs = studentMapper.getStudentNOByCourseId(courseId);
		for (String NO : studentNOs) {
			Student s = studentMapper.getStudentByStudentNO(NO);
			students.add(s);
		}
		return students;
	}

	@Override
	public void addStudentToTheCourse(Student student, int courseId) {
		//判断学生是否存在
		if (studentMapper.getStudentByStudentNO(student.getStudentNO()) == null) {
			//不存在-->则创建后再添加到课程中
			studentMapper.addStudent(student);
			studentMapper.addStudentToCourse(student.getStudentNO(), courseId);
		} else {
			//存在-->再判断学生是否已经在该课程中
			List<String> studentNOs = studentMapper.getStudentNOByCourseId(courseId);
			//在课程中则什么都不做
			if (studentNOs.contains(student.getStudentNO()))
				return ;
			//不在则添加
			studentMapper.addStudentToCourse(student.getStudentNO(), courseId);
		}
	}

	@Override
	public void deleteStudentFromTheCourse(String studentNO, int courseId) {
		studentMapper.deleteStudentFromCourse(studentNO, courseId);
	}
}
