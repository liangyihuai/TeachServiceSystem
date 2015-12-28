package com.huai.controllers;

import com.huai.beans.Course;
import com.huai.beans.Score;
import com.huai.beans.Student;
import com.huai.beans.Teacher;
import com.huai.service.ScoreService;
import com.huai.service.StudentService;
import com.huai.utils.RoleUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liangyihuai on 15-11-18.
 */
@WebServlet(urlPatterns = "/score")
public class ScoreServlet extends HttpServlet {

	private static final long serialVersionUID = 6L;
	private ScoreService scoreService = null;
	private StudentService studentservice = null;

	public void init() throws ServletException {
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext webAppContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		scoreService = webAppContext.getBean(ScoreService.class);
		studentservice = webAppContext.getBean(StudentService.class);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String operate = request.getParameter("operate");
		System.out.println("operate = " + operate);

		if ("getScore".equals(operate)) {
			// 查询成绩
			if (request.getSession().getAttribute(RoleUtil.TEACHER_ROLE_NAME) != null) {
				getScoreList(request, response);
			} else if (request.getSession().getAttribute(
					RoleUtil.STUDENT_ROLE_NAME) != null) {
				getScore(request, response);
			}
		} else if ("updateScore".equals(operate)) {
			// 更新成绩
			updateScore(request, response);
		} else if ("updateCoursePercent".equals(operate)) {
			// 设置成绩所占比例
			setCoursePercent(request, response);
		}
	}

	private void getScore(HttpServletRequest request,
			HttpServletResponse response) {
		Student student = (Student) request.getSession().getAttribute(
				RoleUtil.STUDENT_ROLE_NAME);
		String studentNo = student.getStudentNO();
		int courseID = Integer.parseInt((String) request.getSession()
				.getAttribute(ServletUtil.COURSE_ID));
		List<Map<String, Object>> scoreList = scoreService
				.getGradesByCourseID(courseID);

		JSONObject jo = new JSONObject();
		for (Map<String, Object> score : scoreList) {
			if (score.get("studentNO").equals(studentNo)) {
				jo.element("score", score);
				break;
			}
		}
		
		PrintWriter writer;
		try {
			writer = response.getWriter();
			if (!jo.isEmpty()) {
				Course course = new Course();
				course = scoreService.queryCoursePercent(courseID);
				jo.element("commonPercent", course.getCommonPercent());
				jo.element("finalPercent", course.getFinalPercent());
				writer.write(jo.toString());
			} else {
				writer.write("0");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void setCoursePercent(HttpServletRequest request,
			HttpServletResponse response) {
		int commonPercent = Integer.parseInt(request
				.getParameter("commonPercent"));
		int finalPercent = Integer.parseInt(request
				.getParameter("finalPercent"));
		int courseID = Integer.parseInt((String) request.getSession()
				.getAttribute(ServletUtil.COURSE_ID));
		Course course = new Course();
		course.setCourseID(courseID);
		course.setCommonPercent(commonPercent);
		course.setFinalPercent(finalPercent);
		try {
			if (scoreService.setCoursePercent(course)) {
				PrintWriter writer = response.getWriter();
				writer.write("1");
				writer.close();
			} else {
				PrintWriter writer = response.getWriter();
				writer.write("0");
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateScore(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		int courseID = Integer.parseInt((String) request.getSession()
				.getAttribute(ServletUtil.COURSE_ID));

		Teacher teacher = (Teacher) request.getSession().getAttribute(
				RoleUtil.TEACHER_ROLE_NAME);
		int teacherID = teacher.getTeacherID();
		int studentID = 0;
		int counter = 0;

		List<Map<String, Object>> scoreList = scoreService
				.getGradesByCourseID(courseID);
		List<Student> studentlist = studentservice
				.getStudentsInTheCourse(courseID);
		Score score = new Score();

		String studentNO = request.getParameter("studentNO");
		int commonScore = Integer.parseInt(request.getParameter("commonScore"));
		int finalScore = Integer.parseInt(request.getParameter("finalScore"));
		int totalScore = Integer.parseInt(request.getParameter("totalScore"));

		for (Student student : studentlist) {
			if (student.getStudentNO().equals(studentNO)) {
				studentID = student.getStudentID();
			}
		}
		score.setCommonScore(commonScore);
		score.setFinalScore(finalScore);
		score.setStudentID(studentID);
		score.setTotalScore(totalScore);
		score.setTeacherID(teacherID);

		for (Map<String, Object> map : scoreList) {
			if (map.get("studentNO").equals(studentNO)) {
				try {
					if (scoreService.updateScore(score)) {
						PrintWriter writer = response.getWriter();
						writer.write("1");
						writer.close();
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				counter = 1;
			}
		}
		if (counter != 1) {
			try {
				if (scoreService.insertIntoScore(score)) {
					PrintWriter writer = response.getWriter();
					writer.write("1");
					writer.close();
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		PrintWriter writer = response.getWriter();
		writer.write("0");
		writer.close();
	}

	public void getScoreList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int courseID = Integer.parseInt((String) request.getSession()
				.getAttribute(ServletUtil.COURSE_ID));
		List<Map<String, Object>> scoreList = scoreService
				.getGradesByCourseID(courseID);
		List<Student> studentlist = studentservice
				.getStudentsInTheCourse(courseID);
		List<Student> studentlist1 = studentservice
				.getStudentsInTheCourse(courseID);

		for (Student student : studentlist) {
			for (Map<String, Object> score : scoreList) {
				if (student.getStudentNO().equals(score.get("studentNO"))) {
					studentlist1.remove(student);
				}
			}
		}
		for (Student student : studentlist1) {
			Map<String, Object> score = new HashMap<String, Object>();
			score.put("studentNO", student.getStudentNO());
			score.put("name", student.getName());
			score.put("commonScore", 0);
			score.put("finalScore", 0);
			score.put("totalScore", 0);
			scoreList.add(score);
		}
		Course course = new Course();
		course = scoreService.queryCoursePercent(courseID);

		JSONObject jo = new JSONObject();
		jo.element("score", scoreList);
		jo.accumulate("commonPercent", course.getCommonPercent());
		jo.accumulate("finalPercent", course.getFinalPercent());

		PrintWriter writer = response.getWriter();
		writer.write(jo.toString());
		writer.close();

	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
