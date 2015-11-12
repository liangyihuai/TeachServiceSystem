package com.huai.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.jws.WebService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.huai.utils.ServletUtil;

import net.sf.json.JSONObject;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huai.beans.Course;
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
		
		if("getCourses".equals(operate)){
			String teacherID = request.getParameter("teacherID");
			
			int tempID = -1;
			if(teacherID != null) 
				tempID = Integer.parseInt(teacherID);
			List<Course> courses = courseService.getCourseByTeacherId(tempID);
			
			JSONObject jo = new JSONObject();
			jo.element("courses", courses);
			
			PrintWriter writer = response.getWriter();
			writer.write(jo.toString());
			writer.close();
			
		}else if("".equals(operate)){
			
		}
	}

	
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}