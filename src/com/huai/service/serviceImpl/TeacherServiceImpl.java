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
	public boolean changePassword(Teacher teacher, String newPassword) {
		teacher.setPassword(newPassword);
		int count = teacherMapper.update(teacher);
		if(count > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean signIn(Teacher teacher) throws Exception {
		int count = teacherMapper.addTeacher(teacher);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Teacher getTeacherByName(String name) {
		Teacher teacher = teacherMapper.getTeacherByName(name);
		return teacher;
	}


}
