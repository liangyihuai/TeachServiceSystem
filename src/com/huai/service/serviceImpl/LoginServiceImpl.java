package com.huai.service.serviceImpl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.huai.beans.Student;
import com.huai.beans.Teacher;
import com.huai.persistence.StudentMapper;
import com.huai.persistence.TeacherMapper;
import com.huai.service.LoginService;
import com.huai.utils.RoleUtil;

@Service
public class LoginServiceImpl implements LoginService{
	 
	private StudentMapper studentMapper;
	
	private TeacherMapper teacherMapper;

	@Autowired
	public void setStudentMapper(StudentMapper studentMapper) {
		this.studentMapper = studentMapper;
	}
	
	@Autowired
	public void setTeacherMapper(TeacherMapper teacherMapper) {
		this.teacherMapper = teacherMapper;
	}

	/**
	 * 验证用户
	 * @param studentName 用户输入的用户名
	 * @param password 用户输入的密码
	 * @return
	 */
	@Override
	public boolean validateStudent(String studentName, String password, HttpServletRequest request){
		Assert.notNull(studentName, "studentName must not be null");
		Assert.notNull(password, "password must not be null");
		
		Student stu = studentMapper.getStudentByName(studentName);
		if(stu != null && password.equals(stu.getPassword())){
			if(request != null)
				RoleUtil.addStudentRole(request.getSession(), stu);
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean validateTeacher(String teacherName,String password, HttpServletRequest request){
		Assert.notNull(teacherName, "teacherName must not be null");
		Assert.notNull(password, "password must not be null");
		
		Teacher teacher = teacherMapper.getTeacherByName(teacherName);
		if(teacher != null && password.equals(teacher.getPassword())){
			RoleUtil.addTeacherRole(request.getSession(), teacher);
			return true;
		}else{
			return false;
		}
	}
}
