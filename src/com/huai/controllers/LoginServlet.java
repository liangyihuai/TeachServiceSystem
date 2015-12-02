package com.huai.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huai.service.LoginService;

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
				jo.element("status", "1");
			}else{
				jo.element("status", "0");
			}
			PrintWriter writer = response.getWriter();
			writer.write(jo.toString());
			writer.close();
			return ;
		}else if("studentLogin".equals(operate)){
			String name = request.getParameter("username");
			String password = request.getParameter("password");

			boolean flag = loginService.validateStudent(name, password,request);
			JSONObject jo = new JSONObject();
			if(flag){
				jo.element("status","1");
			}else{
				jo.element("status","0");
			}
			PrintWriter writer = response.getWriter();
			writer.write(jo.toString());
			writer.close();
			return ;
		}else if("logout".equals(operate)){
			HttpSession session = request.getSession(false);
			if(session != null){
				java.util.Enumeration<String> attris = session.getAttributeNames();
				while(attris.hasMoreElements()) {
					String element = attris.nextElement();
					session.removeAttribute(element);
				}
			}
			response.sendRedirect(request.getServletContext().getContextPath()+"/html/index.html");
			return;
		}
	}

	
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
