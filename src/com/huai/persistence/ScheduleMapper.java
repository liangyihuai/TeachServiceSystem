package com.huai.persistence;

import java.util.List;

import com.huai.beans.Schedule;

public interface ScheduleMapper {
	/**
	 * 
	 * @param courseId
	 * @return  List<Schedule>课程进度表的集合
	 */
	List<Schedule> getScheduleByCourseId(int courseId);
	
	/**
	 * 增加课程进度
	 * @param sehedule
	 */
	void addToSchedule(Schedule sehedule);
	
	/**
	 * 删除课程进度
	 * @param courseId
	 */
	void deleteFromSchedule(Schedule sehedule);

}
