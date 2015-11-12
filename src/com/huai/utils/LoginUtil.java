package com.huai.utils;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;
/**
 * 处理用户登录的工具类
 * @author liangyihuai
 *
 */
public class LoginUtil {

	/**
	 * 判断用户是否已经登陆
	 * @param session
	 * @return
	 */
	public static boolean isLoginned(HttpSession session){
		Object obj = session.getAttribute(RoleUtil.ROLE_NAME_kEY);
		if(obj != null)
			return true;
		else
			return false;
	}
	
	/**
	 * 注销登陆
	 * @param session
	 */
	public static void logout(HttpSession session){
		Enumeration<String> enu = session.getAttributeNames();
		while(enu.hasMoreElements()){
			session.removeAttribute(enu.nextElement());
		}
	}
}
