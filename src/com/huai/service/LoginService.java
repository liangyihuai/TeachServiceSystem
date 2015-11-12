package com.huai.service;

import javax.servlet.http.HttpServletRequest;
/**
 * 登陆
 * @author LiangYH
 * 2015年10月15日
 */
public interface LoginService {

	/**
	 * 验证学生
	 * @param studentName
	 * @param password
	 * @param request
	 * @return
	 */
	boolean validateStudent(String studentName, String password,
			HttpServletRequest request);

	/**
	 * 验证老师
	 * @param teacherName
	 * @param password
	 * @param request
	 * @return
	 */
	boolean validateTeacher(String teacherName, String password,
			HttpServletRequest request);
}
