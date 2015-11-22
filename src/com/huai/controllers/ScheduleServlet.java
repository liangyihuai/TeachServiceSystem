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
import com.huai.utils.ServletUtil;
import net.sf.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.huai.beans.Schedule;
import com.huai.service.ScheduleService;

@WebServlet(urlPatterns={"/schedule"})
public class ScheduleServlet extends HttpServlet {
	

	private static final long serialVersionUID = 3L;
	private ScheduleService scheduleService;
		
	@Override
	public void init() throws ServletException {
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext webAppContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		scheduleService = webAppContext.getBean(ScheduleService.class);
	}	
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String operate = request.getParameter("operate");
		System.out.println("operate = " + operate);
		request.setCharacterEncoding("utf-8");

		// 查询课程进度信息
		if ("getSchedule".equals(operate)) {
			querySchedule(request, response);
		} else if ("addSchedule".equals(operate)) {
			// 添加课程进度
			addSchedule(request, response);

		} else if ("modifySchedule".equals(operate)) {
			//修改课程进度
			modifySchedule(request, response);

		} else if ("deleteSchedule".equals(operate)) {
			// 删除课程进度
			//暫不允許刪除
		}

	}
	
	private void modifySchedule(HttpServletRequest request,
			HttpServletResponse response) throws IOException {		
		Schedule schedule = new Schedule();
		int courseID = Integer.parseInt((String) request.getSession()
				.getAttribute(ServletUtil.COURSE_ID));
		int scheduleID = Integer.parseInt(request.getParameter("scheduleID"));	
		String arrangement = request.getParameter("change_plan_text");	
		
		schedule.setArrangement(arrangement);
		schedule.setCourseID(courseID);
		schedule.setScheduleID(scheduleID);
		
		scheduleService.modifySchedule(schedule);
		
		List<Schedule> scheduleList =  scheduleService.getScheduleByCourseId(courseID);		
		if (scheduleList != null && scheduleList.size() > 0) {
			for(Schedule s : scheduleList){
				//在数据库查询到的信息与修改的信息一致，即修改成功，返回1
				if(s.getCourseID()==courseID && s.getScheduleID()==scheduleID && s.getArrangement().equals(arrangement)){
					PrintWriter writer = response.getWriter();
					writer.write("1");
					writer.close();
					return;
				}
				
			}			
		}
		//在数据库中未查询到与修改信息一致的结果，即修改失败，返回0
		PrintWriter writer = response.getWriter();
		writer.write("0");
		writer.close();
		
	}

	private void addSchedule(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Schedule schedule = new Schedule();
		int courseID = Integer.parseInt((String) request.getSession()
				.getAttribute(ServletUtil.COURSE_ID));
		
		String arrangement = request.getParameter("plan_text");
		String courseTime = request.getParameter("plan_time");		
		
		schedule.setArrangement(arrangement);
		schedule.setCourseID(courseID);
		schedule.setCourseTime(courseTime);
		
		scheduleService.addToSchedule(schedule);
		
		List<Schedule> scheduleList =  scheduleService.getScheduleByCourseId(courseID);		
		
		if (scheduleList != null && scheduleList.size() > 0) {
			for(Schedule s : scheduleList){
				//在数据库查询到的信息与添加的信息一致，即添加成功，返回1
				if(s.getCourseID()==courseID && s.getCourseTime().equals(courseTime) &&s.getArrangement().equals(arrangement)){
					PrintWriter writer = response.getWriter();
					writer.write("1");
					writer.close();
					return;
				}
			}			
		}
		//在数据库中未查询到与添加信息一致的结果，即添加失败，返回0
		PrintWriter writer = response.getWriter();
		writer.write("0");
		writer.close();
		
	}

	private void querySchedule(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int courseID = Integer.parseInt((String) request.getSession()
				.getAttribute(ServletUtil.COURSE_ID));
		List<Schedule> scheduleList = scheduleService
				.getScheduleByCourseId(courseID);
		if (scheduleList != null && scheduleList.size() > 0) {
			JSONObject jo = new JSONObject();
			jo.element("schedule", scheduleList);

			PrintWriter writer = response.getWriter();
			writer.write(jo.toString());
			writer.close();
		} else {
			// 未查询到课程进度信息
			// To be continued---

		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	

}
