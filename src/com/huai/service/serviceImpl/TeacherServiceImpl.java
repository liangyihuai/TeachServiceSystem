package com.huai.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huai.beans.Teacher;
import com.huai.persistence.TeacherMapper;
import com.huai.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService{
	
	private TeacherMapper teacherMapper;
	
	@Autowired
	public void setTeacherMapper(TeacherMapper teacherMapper) {
		this.teacherMapper = teacherMapper;
	}

	@Override
	public void changePassword(Teacher teacher, String newPassword) {
		teacher.setPassword(newPassword);
		teacherMapper.update(teacher);
	}

	@Override
	public Teacher getTeacherByName(String name) {
		Teacher teacher = teacherMapper.getTeacherByName(name);
		return teacher;
	}

}
