package com.huai.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.huai.beans.Schedule;
import com.huai.persistence.ScheduleMapper;
import com.huai.service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService{
	private ScheduleMapper scheduleMapper;

	@Autowired
	public void setScheduleMapper(ScheduleMapper scheduleMapper) {
		this.scheduleMapper = scheduleMapper;
	}


	/**
	 * @param courseId  课程ID
	 * @return  List<Schedule>课程进度
	 */
	@Override
	public List<Schedule> getScheduleByCourseId(int courseId) {
		Assert.notNull(courseId,"courseId must not be null");
		
		List<Schedule> list = scheduleMapper.getScheduleByCourseId(courseId);
		if(list!=null && list.size()>0){
			return list;
		}else{
			return null;
		}
		
	}


	@Override
	public void addToSchedule(Schedule sehedule) {
		scheduleMapper.addToSchedule(sehedule);
	}


	@Override
	public void deleteFromSchedule(Schedule sehedule) {
		scheduleMapper.deleteFromSchedule(sehedule);
	}



}
