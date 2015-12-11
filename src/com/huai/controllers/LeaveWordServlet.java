package com.huai.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Date;
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

import com.huai.beans.LeaveWord;
import com.huai.beans.Student;
import com.huai.beans.Teacher;
import com.huai.service.LeaveWordService;
import com.huai.utils.LeaveWordInfo;
import com.huai.utils.RoleUtil;
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
		} else if ("add".equals(operate)) {
			add(request, response, courseId);
		}
	}

	//显示留言
	private void list(HttpServletRequest request, HttpServletResponse response, int courseId) throws ServletException, IOException {
		int page = Integer.parseInt((String)request.getParameter("page"));
		
		List<LeaveWordInfo> leaveWordInfos = leaveWordService.listLeaveWord(page, courseId);
		
		if(leaveWordInfos!=null && leaveWordInfos.size()>0){
			JSONObject jo = new JSONObject();
			jo.element("leaveWords", leaveWordInfos);
			
			PrintWriter writer = response.getWriter();
			writer.write(jo.toString());
			writer.close();
		}
	}
	
	//添加留言
	private void add(HttpServletRequest request, HttpServletResponse response, int courseId) throws ServletException, IOException {
		Teacher teacher = (Teacher)request.getSession().getAttribute(RoleUtil.TEACHER_ROLE_NAME);
		Student student= (Student)request.getSession().getAttribute(RoleUtil.STUDENT_ROLE_NAME);
		
		int teacherID = teacher==null ? -1 : teacher.getTeacherID();
		int studentID = student==null ? -1 : student.getStudentID();
		String content = request.getParameter("content");
		Date time = new Date();
		
		LeaveWord leaveWord = new LeaveWord();
		leaveWord.setContent(content);
		leaveWord.setCourseID(courseId);
		leaveWord.setStudentID(studentID);
		leaveWord.setTeacherID(teacherID);
		leaveWord.setTime(time);
		
		int result = leaveWordService.addLeaveWord(leaveWord);
		response.getWriter().print(result);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
