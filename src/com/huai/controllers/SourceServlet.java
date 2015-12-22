package com.huai.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huai.beans.Source;
import com.huai.beans.Student;
import com.huai.beans.Teacher;
import com.huai.service.SourceService;
import com.huai.utils.DownloadUtils;
import com.huai.utils.RoleUtil;
import com.huai.utils.ServletUtil;

@WebServlet("/source")
public class SourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SourceService sourceService;

	public SourceServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext webAppContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		sourceService = webAppContext.getBean(SourceService.class);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String operate = request.getParameter("operate");
		int courseID = Integer.parseInt((String) request.getSession()
				.getAttribute(ServletUtil.COURSE_ID));
		System.out.println("operate = " + operate);

		if ("download".equals(operate)) {
			int sourceID = Integer.parseInt(request.getParameter("sourceID"));
			Source source = sourceService.getSourceBySourceID(sourceID);
			String path = source.getPath();
			String headline = source.getHeadline();
			String filename = DownloadUtils.getNormalFilename(request, headline);
			System.out.println("path:"+path);
			DownloadUtils.launchDownloadStream(response, path, filename);
		} else if ("getFileList".equals(operate)) {
			getFileList(request, response, courseID);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String operate = request.getParameter("operate");
		int courseID = Integer.parseInt((String) request.getSession()
				.getAttribute(ServletUtil.COURSE_ID));
		System.out.println("operate = " + operate);

		if ("uploadFile".equals(operate)) {
			uploadFile(request, response, courseID);
		} else if ("deleteFile".equals(operate)) {
			int sourceID = Integer.parseInt(request.getParameter("sourceID"));
			deleteFile(request, response, sourceID);
		}
	}

	private void getFileList(HttpServletRequest request,
			HttpServletResponse response, int courseID) throws IOException {
		List<Source> sourseList = sourceService.getFileList(courseID);

		JSONObject jo = new JSONObject();
		jo.element("sourseList", sourseList);
		PrintWriter writer = response.getWriter();
		writer.write(jo.toString());
		writer.close();
	}

	private void uploadFile(HttpServletRequest request,
			HttpServletResponse response, int courseID) {
		com.huai.core.UploadFile upload = new com.huai.core.UploadFile();
		java.util.Map<String,String> resultMap = upload.uploadFile(request, response);
		String path = resultMap.get(com.huai.core.UploadFile.ABSOLUTE_PATH);
		int teacherID = 0;
		int studentID = 0;
		String headline = resultMap.get(com.huai.core.UploadFile.FILE_NAME);
		Date uploaddate = new Date(System.currentTimeMillis());

		Source source = new Source();
		source.setCourseID(courseID);
		source.setHeadline(headline);
		source.setUploadDate(uploaddate);
		source.setPath(path);
		source.setTeacherID(teacherID);
		source.setStudentID(studentID);

		Teacher teacher = (Teacher) request.getSession().getAttribute(
				RoleUtil.TEACHER_ROLE_NAME);
		Student student = (Student) request.getSession().getAttribute(
				RoleUtil.STUDENT_ROLE_NAME);
		if (teacher != null) {
			teacherID = teacher.getTeacherID();
			source.setTeacherID(teacherID);
		} else if (student != null) {
			studentID = student.getStudentID();
			source.setStudentID(studentID);
		}
		boolean runStatus = sourceService.uploadFile(source);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			if (runStatus)
				writer.write("1");
			else
				writer.write("0");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}

	private void deleteFile(HttpServletRequest request,
			HttpServletResponse response, int sourceID) throws IOException {
		Teacher teacher = (Teacher) request.getSession().getAttribute(
				RoleUtil.TEACHER_ROLE_NAME);
		Student student = (Student) request.getSession().getAttribute(
				RoleUtil.STUDENT_ROLE_NAME);
		Source source = sourceService.getSourceBySourceID(sourceID);
		String path = source.getPath();
		File file = new File(path);
		boolean runStatus = false;
		if (teacher != null) {
			file.delete();
			runStatus = sourceService.deleteFile(sourceID);
			System.out.println(runStatus);

		} else if (student != null) {
			source = sourceService.getSourceBySourceID(sourceID);
			int studentID = student.getStudentID();

			if (source.getStudentID() == studentID) {
				file.delete();
				runStatus = sourceService.deleteFile(sourceID);
				System.out.println(runStatus);
			} else {
				PrintWriter writer = response.getWriter();
				writer.write("2");
				writer.close();
				return;
			}
		}
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			if (runStatus)
				writer.write("1");
			else
				writer.write("0");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}

	}

}
