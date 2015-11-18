package com.huai.controllers;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huai.service.ScheduleService;
import com.huai.service.StudentService;



@WebServlet(urlPatterns={"/student"})
public class StudentServlet extends HttpServlet{
	
	
	private StudentService studentService;
	
	@Override
	public void init() throws ServletException {
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		studentService = webAppContext.getBean(StudentService.class);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operate = request.getParameter("operate");
		
		System.out.println("operate = "+operate);
		
		if("".equals(operate)){
			
			
		}else if("".equals(operate)){
			
		}
	}

	
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
