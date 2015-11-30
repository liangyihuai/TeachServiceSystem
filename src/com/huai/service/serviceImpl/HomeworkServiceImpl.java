package com.huai.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huai.beans.Homework;
import com.huai.beans.Student;
import com.huai.beans.StudentHomeWorkRelation;
import com.huai.persistence.HomeworkMapper;
import com.huai.persistence.TeacherMapper;
import com.huai.service.HomeworkService;

@Service
public class HomeworkServiceImpl implements HomeworkService{
	
	private HomeworkMapper homeworkMapper;
	
	@Autowired
	public void setHomeworkMapper(HomeworkMapper homeworkMapper) {
		this.homeworkMapper = homeworkMapper;
	}
	
	@Override
	public List<Homework> getHomework(int courseID) {
		
		List<Homework> homeworks = null;
		homeworks = homeworkMapper.getHomeworks(courseID);
		return homeworks;
	}

	@Override
	public void giveHomework(Homework homework) {
		
		homeworkMapper.giveHomework(homework);

	}

	@Override
	public Boolean isAddHomeworkSuccess(int courseID, String content) {
		
		List<Homework> homeworks = null;
		homeworks = homeworkMapper.getHomeworks(courseID);
		for(Homework homework : homeworks){
			if (homework.getContent().equals(content)) {
				return true;
			}
		}	
		return false;
	}

	@Override
	public List<StudentHomeWorkRelation> getStudentHomework(int homeworkID) {
		List<StudentHomeWorkRelation> studentHomeworks = homeworkMapper.getStudentHomework(homeworkID);
		return studentHomeworks;
	}

	@Override
	public Student getStudent(int studentID) {
		Student student = homeworkMapper.getStudent(studentID);
		return student;
	}

	@Override
	public List<Integer> getUncommitedStudents(int courseID, int homeworkID) {
		List<Integer> studentIDs = homeworkMapper.getUncommitedStudents(courseID, homeworkID);
		return studentIDs;
	}

	@Override
	public void correctHomework(String comment, int score, String studentNO, int homeworkID) {
		homeworkMapper.correctHomework(comment, score, studentNO, homeworkID);
	}

	@Override
	public Boolean isUpdateSuccess(String studentNO, int homeworkID) {
		String status = homeworkMapper.getStatus(studentNO, homeworkID);
		if (status.equals("corrected")) {
			return true;
		}
		return false;
	}
	
}
