package com.huai.controllers;

import com.huai.beans.Student;
import com.huai.beans.Teacher;
import com.huai.core.ExcelOperation;
import com.huai.core.UploadFile;
import com.huai.service.StudentService;
import com.huai.service.TeacherService;
import com.huai.utils.DownloadUtils;
import com.huai.utils.RoleUtil;
import com.huai.utils.ServletUtil;
import net.sf.json.JSON;
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
	
	//http://localhost:8080/TeachServiceSystem/teacher?operate=signIn
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operate = request.getParameter("operate");
		if("info".equals(operate)){
			Teacher teacher = (Teacher)request.getSession().getAttribute(RoleUtil.TEACHER_ROLE_NAME);

			PrintWriter writer = response.getWriter();
			if(teacher != null){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("college", teacher.getCollege());
				jsonObject.element("username",teacher.getName());
				jsonObject.element("school",teacher.getSchool());
				jsonObject.element("sex", teacher.getSex());
				writer.write(jsonObject.toString());
			}else{
				writer.write("0");
			}
			writer.close();
			return;
		} else if("changePass".equals(operate)){
			Teacher teacher = (Teacher)request.getSession().getAttribute(RoleUtil.TEACHER_ROLE_NAME);
			String oldPassword = request.getParameter("oldPass");
			String newPassword = request.getParameter("newPass");

			PrintWriter writer = response.getWriter();
			boolean runStatus = false;
			try {
				if(oldPassword != null && newPassword != null){
					if(teacher != null && oldPassword.equals(teacher.getPassword())){
						teacher.setPassword(newPassword);
						runStatus = teacherService.changeInfo(teacher);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(runStatus)
				writer.write("1");
			else
				writer.write("0");
			writer.close();
		}else if("changeInfo".equals(operate)){
			String name = request.getParameter("teacherNumber");
			String sex = request.getParameter("teacherSex");
			String school = request.getParameter("teacherSchool");
			String college = request.getParameter("teacherAcademy");

			Teacher teacher = (Teacher)request.getSession().getAttribute(RoleUtil.TEACHER_ROLE_NAME);
			boolean runStatus = false;
			PrintWriter writer = response.getWriter();
			try{
				if(teacher != null){
					if(name != null)teacher.setName(name);
					if(sex != null)teacher.setSex(sex);
					if(school != null) teacher.setSchool(school);
					if(college != null)teacher.setCollege(college);
					runStatus = teacherService.changeInfo(teacher);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
			if(runStatus)
				writer.write("1");
			else
				writer.write("0");
			writer.close();
		}
	}


	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
