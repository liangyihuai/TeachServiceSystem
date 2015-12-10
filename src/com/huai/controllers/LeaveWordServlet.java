package com.huai.controllers;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huai.service.LeaveWordService;
import com.huai.utils.ServletUtil;



@WebServlet(urlPatterns={"/leaveword"})
public class LeaveWordServlet extends HttpServlet{
	
	
	private LeaveWordService leaveWordService;
	
	@Override
	public void init() throws ServletException {
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		leaveWordService = webAppContext.getBean(LeaveWordService.class);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operate = request.getParameter("operate");
		int courseId = Integer.parseInt((String)request.getSession().getAttribute(ServletUtil.COURSE_ID));
		System.out.println("operate = "+operate);
		
		if("list".equals(operate)){
			list(request, response, courseId);
		}
	}

	//显示留言
	private void list(HttpServletRequest request, HttpServletResponse response, int courseId) throws ServletException, IOException {
		
//		if(students!=null && students.size()>0){
//			JSONObject jo = new JSONObject();
//			jo.element("students", students);
//			
//			PrintWriter writer = response.getWriter();
//			writer.write(jo.toString());
//			writer.close();
//		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
