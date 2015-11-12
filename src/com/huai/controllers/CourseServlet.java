package com.huai.controllers;

import java.io.IOException;
import javax.jws.WebService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.huai.utils.ServletUtil;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huai.service.CourseService;


@WebServlet(urlPatterns={"/course"})
public class CourseServlet extends HttpServlet{

	private CourseService courseService = null;	
	
	@Override
	public void init() throws ServletException {
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext webAppContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		courseService = webAppContext.getBean(CourseService.class);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operate = request.getParameter("operate");
		
		System.out.println("operate = "+operate);
		
		if("queryCourseId".equals(operate)){
			
			
			response.getWriter().write("");
			
			
		}if("".equals(operate)){
			
		}
	}

	
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}