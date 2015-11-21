package com.huai.service;

import java.util.List;

import com.huai.beans.Schedule;

public interface ScheduleService {
	
	/**
	 * 根据课程ID返回课程进度表
	 * @param courseId
	 * @return
	 */
	List<Schedule> getScheduleByCourseId(int courseId);
	
	/**
	 * 
	 * @param sehedule
	 */
	void addToSchedule(Schedule sehedule);
	
	/**
	 * 
	 * @param courseId
	 * @param scheduleId
	 */
	void deleteFromSchedule(Schedule sehedule);
	/**
	 * 
	 * @param sehedule
	 */
	void modifySchedule(Schedule sehedule);
}
