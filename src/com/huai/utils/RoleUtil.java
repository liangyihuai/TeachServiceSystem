package com.huai.utils;

import javax.servlet.http.HttpSession;

import org.springframework.util.Assert;

/**
 * 用户角色管理工具类
 * @author LiangYH
 * 2015年10月15日
 */
public class RoleUtil {
	/**
	 * 学生角色名
	 */
	public static final String STUDENT_ROLE_NAME = "student";
	
	/**
	 * 老师角色名
	 */
	public static final String TEACHER_ROLE_NAME = "teacher";

	/**
	 * 管理员角色
	 */
	public static final String MANAGER_ROLE_NAME = "manager";
	
	/**
	 * 可以这个key来获取相应的角色的名字
	 */
	public static final String ROLE_NAME_kEY = "role";
	
	/**
	 * 判断当前登陆的用户是否为老师
	 * @param session
	 * @return
	 */
	public static boolean isTeacher(HttpSession session){
		Object teacher = session.getAttribute(TEACHER_ROLE_NAME);
		if(teacher == null)
			return false;
		else
			return true;
	}
	
	/**
	 * 判断当前登录的用户是否为学生
	 * @param session
	 * @return
	 */
	public static boolean isStudent(HttpSession session){
		Object student = session.getAttribute(STUDENT_ROLE_NAME);
		if(student == null)
			return false;
		else
			return true;
	}
	
	/**
	 * 判断当前用户是否为管理员
	 * @param session
	 * @return
	 */
	public static boolean isManager(HttpSession session){
		Object manager = session.getAttribute(MANAGER_ROLE_NAME);
		if(manager == null)
			return false;
		else
			return true;
	}
	
	
	
	/**
	 * 增加一个老师角色
	 * @param session
	 * @param teacher
	 */
	public static void addTeacherRole(HttpSession session, Object teacher){
		Assert.notNull(teacher, "the teacher must not be null");
		
		addRole(session, teacher, TEACHER_ROLE_NAME);
	}
	
	/**
	 * 增加学生角色
	 * @param session
	 * @param student
	 */
	public static void addStudentRole(HttpSession session, Object student){
		Assert.notNull(student, "the student must not be null");
		
		addRole(session, student, STUDENT_ROLE_NAME);
	}
	
	/**
	 * 增加管理员角色
	 * @param session
	 * @param manager
	 */
	public static void addManagerRold(HttpSession session, Object manager){
		Assert.notNull(manager, "the manager must not be null");
		
		addRole(session, manager, MANAGER_ROLE_NAME);
	}
	
	private static void addRole(HttpSession session, Object obj, String roleName){
		session.setAttribute(roleName, obj);
		session.setAttribute(ROLE_NAME_kEY, roleName);
	}
	
	
}
