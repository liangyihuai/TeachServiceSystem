package com.huai.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huai.beans.Student;
import com.huai.service.ScheduleService;
import com.huai.service.StudentService;
import com.huai.utils.ServletUtil;



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
		
		if("list".equals(operate)){
			list(request, response);
		}else if("add".equals(operate)){
			add(request, response);
		}else if("delete".equals(operate)){
			delete(request, response);
		}else if("validate".equals(operate)) {
			validate(request, response);
		}else if("import".equals(operate)){
			//从Excel文件进行导入的操作
		}
	}

	//列出此课程中所有学生
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int courseId = Integer.parseInt((String)request.getSession().getAttribute(ServletUtil.COURSE_ID));
		List<Student> students = studentService.getStudentsInTheCourse(courseId);
		
		if(students!=null && students.size()>0){
			JSONObject jo = new JSONObject();
			jo.element("students", students);
			
			PrintWriter writer = response.getWriter();
			writer.write(jo.toString());
			writer.close();
		}else{
			//此课程中没有学生
		}
	}
	
	//添加学生到课程
	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int courseId = Integer.parseInt((String)request.getSession().getAttribute(ServletUtil.COURSE_ID));
		String studentNO = request.getParameter("stuNum");
		
		int flag = studentService.addStudentToTheCourse(studentNO, courseId);
		response.getWriter().print(flag);
	}
	
	//从课程中删除学生
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int courseId = Integer.parseInt((String)request.getSession().getAttribute(ServletUtil.COURSE_ID));
		String studentNo = request.getParameter("stuNum");
		int flag = studentService.deleteStudentFromTheCourse(studentNo, courseId);
		response.getWriter().print(flag);
	}
	
	//输入验证
	private void validate(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		int courseId = Integer.parseInt((String)request.getSession().getAttribute(ServletUtil.COURSE_ID));
		String studentNo = request.getParameter("stuNum");
		boolean flag = studentService.validate(studentNo, courseId);
		response.getWriter().print(flag);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
