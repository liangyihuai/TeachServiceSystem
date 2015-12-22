package com.huai.service.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huai.beans.Homework;
import com.huai.persistence.HomeworkMapper;
import com.huai.persistence.StudentHomeworkMapper;
import com.huai.service.StudentHomeworkService;

@Service
public class StudentHomeworkServiceImpl implements StudentHomeworkService{
	private StudentHomeworkMapper studentHomeworkMapper;
	private HomeworkMapper homeworkMapper;
	
	@Autowired
	public void setStudentHomeworkMapper(
			StudentHomeworkMapper studentHomeworkMapper) {
		this.studentHomeworkMapper = studentHomeworkMapper;
	}
	
	@Autowired
	public void setHomeworkMapper(HomeworkMapper homeworkMapper){
		this.homeworkMapper = homeworkMapper;
	}
	
	public List<Homework> getHomeworks(int courseID) {
		List<Homework> homeworks = homeworkMapper.getHomeworks(courseID);
		return homeworks;
	}

	@Override
	public Boolean doHomework(int studentID, int homeworkID, String content, Date time) {
		studentHomeworkMapper.doHomework(studentID, homeworkID, content, time);
		String string = studentHomeworkMapper.getContent(studentID, homeworkID);
		if (string != null) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean modifyHomework(int homeworkID, int studentID, String content, Date time) {
		studentHomeworkMapper.modifyHomework(homeworkID, studentID, content);
		String content1 = studentHomeworkMapper.getContent(studentID, homeworkID);
		if(content.equals(content1)){
			return true;
		}
		return false;
	}

	@Override
	public String getContent(int homeworkID, int studentID) {
		String content;
		content = studentHomeworkMapper.getContent(studentID, homeworkID);
		if (content == null) {
			return null;
		}
		return content;
	}

}
