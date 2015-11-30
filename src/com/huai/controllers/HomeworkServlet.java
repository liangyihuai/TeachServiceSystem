package com.huai.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.catalina.Session;
import org.apache.catalina.manager.util.SessionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import sun.tools.jar.resources.jar;

import com.huai.beans.Homework;
import com.huai.beans.Student;
import com.huai.beans.StudentHomeWorkRelation;
import com.huai.beans.Teacher;
import com.huai.service.HomeworkService;
import com.huai.utils.RoleUtil;
import com.huai.utils.ServletUtil;

@WebServlet(urlPatterns = { "/homework" })
public class HomeworkServlet extends HttpServlet {
	private HomeworkService homeworkService = null;

	@Override
	public void init() throws ServletException {
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		homeworkService = webApplicationContext.getBean(HomeworkService.class);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String operate = request.getParameter("operate");
		Teacher teacher = (Teacher) request.getSession().getAttribute(
				RoleUtil.TEACHER_ROLE_NAME);
		int teacherID = teacher.getTeacherID();
		int courseID = Integer.parseInt((String) request.getSession()
				.getAttribute(ServletUtil.COURSE_ID));
		System.out.println("coureID:" + courseID);

		// 如果操作为correct,则批改作业
		if ("correct".equals(operate)) {
			System.out.println("coureID:" + courseID);
			List<Homework> homeworks = homeworkService.getHomework(courseID);

			JSONArray jsonArray = new JSONArray();

			for (Homework homework : homeworks) {
				JSONObject jo = new JSONObject();

				jo.element("buildDate", homework.getBuildDate())
						.element("content", homework.getContent())
						.element("deadline", homework.getDeadline())
						.element("homeworkID", homework.getHomeworkID());

				jsonArray.add(jo);
			}
			PrintWriter writer = response.getWriter();
			writer.write(jsonArray.toString());
			// System.out.println(jsonArray.toString());
		} else if ("addHomework".equals(operate)) {

			SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
			Date buildDate = new Date();
			Date deadline;
			try {
				deadline = format.parse(request
						.getParameter("homeworkStopTime"));
				String content = request.getParameter("homeworkTitle");
				Homework homework = new Homework();
				homework.setBuildDate(buildDate);
				homework.setDeadline(deadline);
				homework.setHeadline(content);
				homework.setTeacherID(teacherID);
				homework.setContent(content);
				homework.setCourseID(courseID);

				homeworkService.giveHomework(homework);

				PrintWriter writer = response.getWriter();
				Boolean isSuccess = homeworkService.isAddHomeworkSuccess(
						courseID, content);
				if (isSuccess) {
					writer.print(1);
				} else {
					writer.print(0);
				}
			} catch (ParseException e) {
				e.printStackTrace();
				PrintWriter writer = response.getWriter();
				writer.print(-1);
			}
		} else if ("setHomeworkID".equals(operate)) {
			int homeworkID = Integer.parseInt(request.getParameter("homeworkID"));
			request.getSession().setAttribute("homeworkID", homeworkID);
		} else if ("listHomework".equals(operate)) {
			int homeworkID = (int) request.getSession().getAttribute("homeworkID");
			//int homeworkID = 1;
			List<StudentHomeWorkRelation> studentHomeworks = homeworkService
					.getStudentHomework(homeworkID);
			JSONArray commitedHomeworks = new JSONArray();
			JSONArray correctedHomeworks = new JSONArray();
			JSONArray unCommitedStudents = new JSONArray();
			JSONObject homeworkJson = new JSONObject();

			for (StudentHomeWorkRelation homework : studentHomeworks) {
				if (homework.getStatus() == null) {
					Student student = homeworkService.getStudent(homework
							.getStudentID());
					System.out.println("学生学号:" + student.getStudentNO());
					JSONObject commitedHomework = new JSONObject();
					commitedHomework
							.element("studentNO", student.getStudentNO())
							.element("name", student.getName())
							.element("content", homework.getContent())
							.element("studentHomeworkID", homework.getStudentHomeworkID());
					commitedHomeworks.add(commitedHomework);
				} else if (homework.getStatus().equals("corrected")) {
					Student student = homeworkService.getStudent(homework
							.getStudentID());
					JSONObject correctedHomework = new JSONObject();
					correctedHomework.element("studentNO",
							student.getStudentNO()).element("name",
							student.getName()).element("score", homework.getScore());
					correctedHomeworks.add(correctedHomework);
				}
			}
			
			List<Integer> studentIDs = homeworkService.getUncommitedStudents(courseID, homeworkID);
			for (Integer studentID : studentIDs) {
				Student student = homeworkService.getStudent(studentID);
				JSONObject jo = new JSONObject();
				jo.element("studentNO", student.getStudentNO()).element("name", student.getName());
				unCommitedStudents.add(jo);
			}
			
			homeworkJson.element("commited", commitedHomeworks)
					.element("corrected", correctedHomeworks)
					.element("unCommited", unCommitedStudents);
			
			PrintWriter writer = response.getWriter();
			writer.write(homeworkJson.toString());
		} else if ("correctHomework".equals(operate)) {
			int homeworkID = (int)request.getSession().getAttribute("homeworkID");
			String comment = request.getParameter("comment");
			int score = Integer.parseInt(request.getParameter("score"));
			String studentNO = request.getParameter("studentNO");
			homeworkService.correctHomework(comment, score, studentNO, homeworkID);
			Boolean isSuccess = homeworkService.isUpdateSuccess(studentNO, homeworkID);
			
			PrintWriter writer = response.getWriter();
			if (isSuccess) {
				writer.print(1);;
			} else {
				writer.print(0);
			}
		}

	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
