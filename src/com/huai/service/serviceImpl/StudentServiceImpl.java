package com.huai.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huai.beans.Student;
import com.huai.persistence.StudentMapper;
import com.huai.service.StudentService;

import org.springframework.transaction.annotation.Transactional;

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
		List<Integer> studentIDs = studentMapper.getStudentIDByCourseId(courseId);
		for (int ID : studentIDs) {
			Student s = studentMapper.getStudentByStudentID(ID);
			students.add(s);
		}
		return students;
	}

	@Transactional
	@Override
	public int addStudentToTheCourse(Student student, int courseId){
		//判断学生是否已经在此课程中
		List<Student> students = getStudentsInTheCourse(courseId);
		for (Student s : students)
			if (student.getStudentNO().equals(s.getStudentNO()))
				return 2;//在
		//不在
		try {
			studentMapper.addStudent(student);
			int studentID = studentMapper.getMaxStudentID();
			studentMapper.addStudentToCourse(studentID, courseId);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Transactional
	@Override
	public int deleteStudentFromTheCourse(String studentNO, int courseId) {
		List<Student> students = getStudentsInTheCourse(courseId);
		for (Student s : students)
			if (studentNO.equals(s.getStudentNO())) {
				try {
					studentMapper.deleteStudent(s.getStudentID());
					studentMapper.deleteStudentFromCourse(s.getStudentID(), courseId);
					return 1;
				} catch (Exception e) {
					e.printStackTrace();
					return 0;
				}
			}
		return 0;
	}

	@Transactional
	@Override
	public boolean importStudents(List<ArrayList<String>> dyadic, int courseID) {
		int nameIndex = -1;
		int sexIndex = -1;
		int clazzIndex = -1;
		int majorIndex = -1;
		int studentNOIndex = -1;

		ArrayList<String> headline = dyadic.get(0);
		int colLen = headline.size();
		for(int i = 0; i < colLen; i++){
			switch (headline.get(i)){
				case "学号":
					studentNOIndex = i;
					break;
				case "专业":
					majorIndex = i;
					break;
				case "性别":
					sexIndex = i;
					break;
				case "班级":
					clazzIndex = i;
					break;
				case "姓名":
					nameIndex = i;
					break;
			}
		}

		if (studentNOIndex == -1 || nameIndex == -1) return false;
		//get all students in this course
		List<Student> students = studentMapper.getStudents(courseID);

		//remove the row from dyadic if exist in DB

		for (Student student: students){
			String stuNO = student.getStudentNO();
			for(int i = 1; i < dyadic.size(); i++){
				if (dyadic.get(i).get(studentNOIndex).equals(stuNO)){
					dyadic.remove(i);
					i--;
				}
			}
		}
		boolean runStatus = true;
		//insert into the db
		int rowLen = dyadic.size();
		for(int i = 1; i < rowLen; i++){
			Student stu = new Student();
			stu.setName(dyadic.get(i).get(nameIndex));
			stu.setStudentNO(dyadic.get(i).get(studentNOIndex));
			stu.setPassword("111111");
			if(clazzIndex != -1)
				stu.setClazz(dyadic.get(i).get(clazzIndex));
			if(sexIndex != -1)
				stu.setSex(dyadic.get(i).get(sexIndex));
			if(majorIndex != -1)
				stu.setMajor(dyadic.get(i).get(majorIndex));
			try{
				studentMapper.addStudent(stu);
				studentMapper.addStudentToCourse(stu.getStudentID(),courseID);
			}catch (Exception e){
				e.printStackTrace();
				runStatus = false;
			}

		}
		return runStatus;
	}
}
