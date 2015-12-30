package com.huai.controllers;

import com.huai.beans.Student;
import com.huai.beans.Teacher;
import com.huai.core.ExcelOperation;
import com.huai.core.UploadFile;
import com.huai.service.StudentService;
import com.huai.service.TeacherService;
import com.huai.utils.DownloadUtils;
import com.huai.utils.ServletUtil;
import net.sf.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@WebServlet(urlPatterns={"/teacher"})
public class TeacherServlet extends HttpServlet{
	
	
	private TeacherService teacherService;
	
	@Override
	public void init() throws ServletException {
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		teacherService = webAppContext.getBean(TeacherService.class);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operate = request.getParameter("operate");
		if("signIn".equals(operate)){
			String name = request.getParameter("username");
			String password = request.getParameter("password");
			String sex = request.getParameter("sex");
			String school = request.getParameter("school");
			String college = request.getParameter("college");

			Teacher teacher = new Teacher();
			teacher.setName(name);
			teacher.setCollege(college);
			teacher.setSchool(school);
			teacher.setSex(sex);
			teacher.setPassword(password);

			boolean runStatus = false;
			PrintWriter writer = response.getWriter();
			try {
				runStatus = teacherService.signIn(teacher);
				if(runStatus)
					writer.write(1);
				else
					writer.write(0);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				writer.close();
			}
		}
	}


	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
