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
	public int addStudentToTheCourse(String studentNO, int courseId) {
		//判断学生是否已经在该课程中
		List<String> studentNOs = studentMapper.getStudentNOByCourseId(courseId);
		if (studentNOs.contains(studentNO))
			return 2;//已存在
		// 不在则添加
		studentMapper.addStudentToCourse(studentNO, courseId);
		//判断是否添加成功
		List<String> studentNOs1 = studentMapper.getStudentNOByCourseId(courseId);
		if (studentNOs1.contains(studentNO))
			return 1;//添加成功
		return 0;//添加失败
	}

	@Override
	public int deleteStudentFromTheCourse(String studentNO, int courseId) {
		// 判断学生是否在该课程中
		List<String> studentNOs = studentMapper.getStudentNOByCourseId(courseId);
		if (!studentNOs.contains(studentNO))
			return 3;// 不存在
		//删除
		studentMapper.deleteStudentFromCourse(studentNO, courseId);
		// 判断学生是否在该课程中
		List<String> studentNOs1 = studentMapper.getStudentNOByCourseId(courseId);
		if (!studentNOs1.contains(studentNO))
			return 1;// 删除成功
		return 0;
	}

	@Override
	public boolean validate(String studentNO, int courseId) {
		//判断学生是否已经在该课程中
		List<String> studentNOs = studentMapper.getStudentNOByCourseId(courseId);
		if (studentNOs.contains(studentNO))
			return true;//已存在
		return false;
	}
}
