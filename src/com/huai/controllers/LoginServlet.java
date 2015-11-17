package com.huai.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huai.service.LoginService;
import com.huai.utils.ServletUtil;

import net.sf.json.JSONObject;

@WebServlet(urlPatterns={"/login"})
public class LoginServlet extends HttpServlet{

	private LoginService loginService = null;
	
	@Override
	public void init() throws ServletException {
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext webAppContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		loginService = webAppContext.getBean(LoginService.class);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operate = request.getParameter("operate");
		
		System.out.println("operate = "+operate);
		
		if("teacherLogin".equals(operate)){
			String name = request.getParameter("username");
			String password = request.getParameter("password");
			
			
			boolean flag = loginService.validateTeacher(name, password, request);
			
			JSONObject jo = new JSONObject();
			if(flag){
//				path = "TeachServiceSystem/html/course-list.html";
				jo.element("status", "1");
			}else{
				jo.element("status", "0");
//				String path = "html/teacher-login.html";
			}
			PrintWriter writer = response.getWriter();
			writer.write(jo.toString());
			writer.close();
			return ;
		}if("studentLogin".equals(operate)){
			
		}
	}

	
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
