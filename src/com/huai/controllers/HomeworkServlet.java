package com.huai.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.huai.beans.Teacher;
import com.huai.service.HomeworkService;
import com.huai.utils.RoleUtil;

@WebServlet(urlPatterns={"/homework"})
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
		
		//如果操作为correct,则批改作业
		if ("correct".equals(operate)) {
			List<Homework> homeworks = homeworkService.getHomework(teacherID);
			
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
			System.out.println(jsonArray.toString());
		} else if("addHomework".equals(operate)){
			
			SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
			Date buildDate = new Date();
			Date deadline;
			try {
				deadline = format.parse(request.getParameter("homeworkStopTime"));
				String content = request.getParameter("homeworkTitle");
				Homework homework = new Homework();
				homework.setBuildDate(buildDate);
				homework.setDeadline(deadline);
				homework.setHeadline(content);
				homework.setTeacherID(teacherID);
				homeworkService.giveHomework(homework);
				
				PrintWriter writer = response.getWriter();
				Boolean isSuccess = homeworkService.isAddHomeworkSuccess(teacherID, content);
				if(isSuccess){
					writer.write(1);
				} else {
					writer.write(0);
				}
			} catch (ParseException e) {
				e.printStackTrace();
				PrintWriter writer = response.getWriter();
				writer.write(-1);
			}
			/*
			Homework homework = new Homework();
			homework.setBuildDate(new Date());
			homework.setDeadline(new Date());
			homework.setContent("this is a test,too");
			homework.setTeacherID(teacher.getTeacherID());
			*/
			
		}
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
