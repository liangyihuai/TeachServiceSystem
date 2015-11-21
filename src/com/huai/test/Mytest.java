package com.huai.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huai.beans.Course;
import com.huai.beans.Schedule;
import com.huai.service.CourseService;
import com.huai.service.LoginService;
import com.huai.service.ScheduleService;
import com.huai.utils.MyDateFormat;;

public class Mytest {

	public static void main(String[] args) {
		//得到spring容器的对象
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/applicationContext.xml");
		
		
        //测试  根据老师ID查询课程列表信息
//		CourseService  courseService = context.getBean(CourseService.class);
//		List<Course> courseList =  courseService.getCourseByTeacherId(1);		
//		if(courseList != null){
//			for(Course c:courseList){
//				System.out.println("课程ID为   ："+c.getCourseID());
//				System.out.println("老师ID为   ："+c.getTeacherID());
//				System.out.println("课程名称为："+c.getCourseName());
//				System.out.println("创建时间为："+c.getBuildDate());				
//				System.out.println("---------我是华丽丽的分割线------------");				
//			}			
//		}
//		
		
		//测试  根据课程ID查询课程进度安排
		ScheduleService scheduleService = context.getBean(ScheduleService.class);
		List<Schedule> list = scheduleService.getScheduleByCourseId(1);
		
		if(list!=null){
			for(Schedule s : list){
				System.out.println("courseID ："+s.getCourseID()+"   第"+s.getScheduleID()+"次安排");
				System.out.println("课程时间为 ："+s.getCourseTime());
				System.out.println("课程安排是 ："+s.getArrangement());
				System.out.println("---------我是华丽丽的分割线------------");
			}			
		}
		
		System.out.println("--------------------------------------------------");
//		测试 增加课程进度安排
//		Schedule schedule = new Schedule();
//		schedule.setCourseID(1);
//		schedule.setCourseTime("第一周");
//		schedule.setArrangement("这是测试增加进度安排");
//		scheduleService.addToSchedule(schedule);
////		
//		list = scheduleService.getScheduleByCourseId(1);
//		if(list!=null){
//			for(Schedule s : list){
//				System.out.println("courseID ："+s.getCourseID()+"   第"+s.getScheduleID()+"次安排");
//				System.out.println("课程时间为 ："+s.getCourseTime());
//				System.out.println("课程安排是 ："+s.getArrangement());
//				System.out.println("---------我是华丽丽的分割线------------");
//			}			
//		}
//		System.out.println("--------------------------------------------------");
		
		//测试删除课程进度安排(需要传入 courseID和scheduleID)
//		Schedule schedule = new Schedule();
//		schedule.setCourseID(1);
//		schedule.setScheduleID(1);
//		scheduleService.deleteFromSchedule(schedule);
//		list = scheduleService.getScheduleByCourseId(1);
//		if(list!=null){
//			for(Schedule s : list){
//				System.out.println("courseID ："+s.getCourseID()+"   第"+s.getScheduleID()+"次安排");
//				System.out.println("课程时间为 ："+s.getCourseTime());
//				System.out.println("课程安排是 ："+s.getArrangement());
//				System.out.println("---------我是华丽丽的分割线------------");
//			}
//		}
//		System.out.println("--------------------------------------------------");
//		
		//测试修改课程进度（传入修改scheduleID，和修改内容）
		Schedule schedule = new Schedule();
		schedule.setCourseID(1);
		schedule.setScheduleID(1);
		schedule.setArrangement("哈哈哈");
		schedule.setCourseTime("第第一周");
		scheduleService.modifySchedule(schedule);
		list = scheduleService.getScheduleByCourseId(1);
		if(list!=null){
			for(Schedule s : list){
				System.out.println("courseID ："+s.getCourseID()+"   第"+s.getScheduleID()+"次安排");
				System.out.println("课程时间为 ："+s.getCourseTime());
				System.out.println("课程安排是 ："+s.getArrangement());
				System.out.println("---------我是华丽丽的分割线------------");
			}
		}
		System.out.println("--------------------------------------------------");
	}
}
