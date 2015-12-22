package com.huai.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huai.utils.RoleUtil;
import com.huai.utils.ServletUtil;

import net.sf.json.JSONObject;

import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huai.beans.Course;
import com.huai.beans.Student;
import com.huai.beans.Teacher;
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
		Teacher teacher = (Teacher)request.getSession().getAttribute(RoleUtil.TEACHER_ROLE_NAME);
		
		System.out.println("operate = "+operate);
		
		if("getCourses".equals(operate)){
			if (teacher != null)
				getcourses(request,response);
			else
				getCoursesForStu(request, response);
			
		}else if("addCourse".equals(operate)){
			addCourse(request,response);
			
		}else if("deleteCourse".equals(operate)){
			deleteCourse(request,response);
			
		}else if("choose".equals(operate)){
			choose(request,response);
			
		}
	}

	
	private void choose(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String courseID = request.getParameter("courseID");
		Assert.notNull(courseID,"courser ID from Web must not be null !!!!");
		//把courseID放到session中
		request.getSession().setAttribute(ServletUtil.COURSE_ID, courseID);
		
		PrintWriter writer = response.getWriter();
		writer.write("1");
		writer.close();
	}

	private void deleteCourse(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int courseID = Integer.parseInt(request.getParameter("courseID"));
		
		if(courseService.deleteCourseByCourseID(courseID)){
			PrintWriter writer = response.getWriter();
			writer.write("1");
			writer.close();
		}else{
			PrintWriter writer = response.getWriter();
			writer.write("0");
			writer.close();
		}
	}

	private void addCourse(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Teacher teacher = (Teacher) request.getSession().getAttribute(RoleUtil.TEACHER_ROLE_NAME);
		int teacherID = teacher.getTeacherID();
		String courseName = "";
		String courseDescription = "";
		Date buildDate = new Date(System.currentTimeMillis());
		courseDescription = request.getParameter("courseDec");
		courseName = request.getParameter("courseName");
		
		Course course = new Course();
		course.setBuildDate(buildDate);
		course.setTeacherID(teacherID);
		course.setCourseName(courseName);
		course.setCourseDescription(courseDescription);
		
		if(courseService.addCourseByTeacherID(course)){
			PrintWriter writer = response.getWriter();
			writer.write("1");
			writer.close();
		}else{
			PrintWriter writer = response.getWriter();
			writer.write("0");
			writer.close();
		}
		
	}

	private void getcourses(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Teacher teacher = (Teacher) request.getSession().getAttribute(RoleUtil.TEACHER_ROLE_NAME);
		int TeacherID = teacher.getTeacherID();
		
		List<Course> courses = courseService.getCourseByTeacherId(TeacherID);
		
		JSONObject jo = new JSONObject();
		jo.element("courses", courses);
		
		PrintWriter writer = response.getWriter();
		writer.write(jo.toString());
		writer.close();
		
	}
	
	private void getCoursesForStu(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Student student= (Student)request.getSession().getAttribute(RoleUtil.STUDENT_ROLE_NAME);
		
		List<Course> courses = courseService.getCourseByStudentNO(student.getStudentNO());
		
		JSONObject jo = new JSONObject();
		jo.element("courses", courses);
		
		PrintWriter writer = response.getWriter();
		writer.write(jo.toString());
		writer.close();
		
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}