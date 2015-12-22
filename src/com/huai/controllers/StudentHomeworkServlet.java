package com.huai.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import sun.print.resources.serviceui;

import com.huai.beans.Homework;
import com.huai.beans.Student;
import com.huai.beans.Teacher;
import com.huai.service.HomeworkService;
import com.huai.service.StudentHomeworkService;
import com.huai.utils.RoleUtil;
import com.huai.utils.ServletUtil;

@WebServlet(urlPatterns = { "/StudentHomeworkServlet" })
public class StudentHomeworkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentHomeworkService studentHomeworkService = null;

	@Override
	public void init() throws ServletException {
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		studentHomeworkService = webApplicationContext
				.getBean(StudentHomeworkService.class);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Teacher teacher = (Teacher) request.getSession().getAttribute(
				RoleUtil.TEACHER_ROLE_NAME);
		int courseID = Integer.parseInt((String) request.getSession()
				.getAttribute(ServletUtil.COURSE_ID));
		Student student = (Student)request.getSession().getAttribute(RoleUtil.STUDENT_ROLE_NAME);
		int studentID = student.getStudentID();
		String operate = request.getParameter("operate");
		if ("getHomework".equals(operate)) {	//获取作业列表
			List<Homework> homeworks = studentHomeworkService
					.getHomeworks(courseID);
			JSONObject object = new JSONObject();
			JSONArray array = new JSONArray();
			for (Homework homework : homeworks) {
				JSONObject ob = new JSONObject();
				
				String content = studentHomeworkService.getContent(homework.getHomeworkID(),studentID);
				int score = studentHomeworkService.getScore(homework.getHomeworkID(), studentID);
				ob.element("content", homework.getContent())
				.element("homeworkID", homework.getHomeworkID())
				.element("buildDate", homework.getBuildDate())
				.element("deadline", homework.getDeadline());
				
				if (score != 0) {
					ob.element("score", score);
				} else {
					ob.element("score", "");
				}
				if(content == null){
					ob.element("studentHomeworkContent", "");					
				} else {
					ob.element("studentHomeworkContent", content);	
				}
				array.add(ob);
			}
			PrintWriter writer = response.getWriter();
			writer.print(array.toString());
		}
		else if ("commitHomework".equals(operate)) {	//提交学生作业，成功返回1，失败返回0
			String content = request.getParameter("content");
			int homeworkID = Integer.parseInt(request.getParameter("homeworkID"));
			String oldContent = studentHomeworkService.getContent(homeworkID, studentID);
			Date time = new Date();
			if (oldContent == null) {
				Boolean isSuccess = studentHomeworkService.doHomework(studentID, homeworkID, content, time);
				
				PrintWriter writer = response.getWriter();
				if (isSuccess) {
					writer.write(1);
				} else {
					writer.write(0);
				}
			} else {
				Boolean isSuccess = studentHomeworkService.modifyHomework(homeworkID, studentID, content, time);
				PrintWriter writer = response.getWriter();
				if (isSuccess) {
					writer.write(1);
				} else {
					writer.write(0);
				}
			}
		}
		/*else if ("modifyHomework".equals(operate)) {	//学生修改作业，成功返回1，失败返回0
			String content = request.getParameter("content");
			int studentHomeworkID = Integer.parseInt(request.getParameter("studentHomeworkID"));
			Boolean isSuccess = studentHomeworkService.modifyHomework(studentHomeworkID, content);
			PrintWriter writer = response.getWriter();
			if (isSuccess) {
				writer.write(1);
			} else {
				writer.write(0);
			}
		}*/
	}

}
