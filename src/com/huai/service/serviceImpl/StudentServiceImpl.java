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
	public int addStudentToTheCourse(Student student, int courseId) {
		if (studentMapper.getStudentByStudentNO(student.getStudentNO()) == null) {
			studentMapper.addStudent(student);
			if (studentMapper.getStudentByStudentNO(student.getStudentNO()) == null)
				return 0;
			studentMapper.addStudentToCourse(student.getStudentNO(), courseId);
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
			studentMapper.addStudentToCourse(student.getStudentNO(), courseId);
			//判断是否添加成功
			List<String> studentNOs1 = studentMapper.getStudentNOByCourseId(courseId);
			if (studentNOs1.contains(student.getStudentNO()))
				return 1;//添加成功
			return 0;//添加失败
		}
	}

	@Override
	public int deleteStudentFromTheCourse(String studentNO, int courseId) {
		//删除
		studentMapper.deleteStudentFromCourse(studentNO, courseId);
		// 判断学生是否在该课程中
		List<String> studentNOs1 = studentMapper.getStudentNOByCourseId(courseId);
		if (!studentNOs1.contains(studentNO))
			return 1;// 删除成功
		return 0;
	}
}
