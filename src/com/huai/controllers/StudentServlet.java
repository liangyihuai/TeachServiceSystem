package com.huai.controllers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huai.core.ExcelOperation;
import com.huai.core.UploadFile;
import com.huai.utils.DownloadUtils;
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
		int courseId = Integer.parseInt((String)request.getSession().getAttribute(ServletUtil.COURSE_ID));
		System.out.println("operate = "+operate);
		
		if("list".equals(operate)){
			list(request, response,courseId);
		}else if("add".equals(operate)){
			add(request, response,courseId);
		}else if("delete".equals(operate)){
			delete(request, response,courseId);
		}else if("import".equals(operate)){
			//从Excel文件进行导入的操作
			importStudents(request, response, courseId);
		}else if("download".equals(operate)){
			download(request,response);
		}
	}

	//列出此课程中所有学生
	private void list(HttpServletRequest request, HttpServletResponse response,int courseId) throws ServletException, IOException {
		List<Student> students = studentService.getStudentsInTheCourse(courseId);
		
		if(students!=null && students.size()>0){
			JSONObject jo = new JSONObject();
			jo.element("students", students);
			
			PrintWriter writer = response.getWriter();
			writer.write(jo.toString());
			writer.close();
		}
	}
	
	//添加学生到课程
	private void add(HttpServletRequest request, HttpServletResponse response,int courseId) throws ServletException, IOException {
		String studentNO = request.getParameter("stuNum");
		String name = request.getParameter("stuName");
		String sex = request.getParameter("sex");
		sex = "male".equals(sex) ? "男" : "女";
		String clazz = request.getParameter("classNum");
		
		Student student = new Student();
		student.setStudentNO(studentNO);
		student.setClazz(clazz);
		student.setName(name);
		student.setPassword("111111");
		student.setSex(sex);

		int flag = 0;
		try {
			flag = studentService.addStudentToTheCourse(student, courseId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().print(flag);
	}
	
	//从课程中删除学生
	private void delete(HttpServletRequest request, HttpServletResponse response,int courseId) throws ServletException, IOException {
		String studentNo = request.getParameter("stuNum");
		int flag = studentService.deleteStudentFromTheCourse(studentNo, courseId);
		response.getWriter().print(flag);
	}

	/**
	 * //从Excel文件进行导入的操作
	 * @param request
	 * @param response
	 * @param courseId
	 * LiangYH
	 */
	private void importStudents(HttpServletRequest request, HttpServletResponse response,int courseId){
		//upload excel file to server
		UploadFile uploadFile = new UploadFile();
		ExcelOperation excelOperation = new ExcelOperation();

		Map<String,String> params = uploadFile.uploadFile(request, response);
		if (params.size() == 0) return;
		//get the file absolute path
		String path = params.get(UploadFile.ABSOLUTE_PATH);
		//get the content in the excel file
		List<ArrayList<String>> dyadic = null;
		dyadic = excelOperation.importForm(path, 0);
		//the first row is headline
		if(dyadic == null || dyadic.size() < 2)return ;

		boolean runStatus = studentService.importStudents(dyadic,courseId);
		//删除刚刚上传的excel表
		uploadFile.delect(path);

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			if(runStatus)
				writer.write("1");
			else
				writer.write("0");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}

	public void download(HttpServletRequest request, HttpServletResponse response){
		String fileName = "学生信息导入文件模板.xlsx";
		// 防止因为浏览器不同导致文件名乱码
		fileName = DownloadUtils.getNormalFilename(request, fileName);
		String realPath = DownloadUtils.getRealPath(DownloadUtils.TEMPLET_EXCEL);
		// 改变响应头，发起下载流
		DownloadUtils.launchDownloadStream(response, realPath, fileName);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
