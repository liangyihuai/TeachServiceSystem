package com.huai.service.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.huai.beans.Course;
import com.huai.beans.Score;
import com.huai.persistence.ScoreMapper;
import com.huai.service.ScoreService;

@Service
public class ScoreServiceImpl implements ScoreService{
	private ScoreMapper scoreMapper;
	
	@Autowired
	public void setScoremapper(ScoreMapper scoreMapper) {
		this.scoreMapper = scoreMapper;
	}

	@Override
	public List<Map<String,Object>> getGradesByCourseID(int courseID) {
		Assert.notNull(courseID,"courseId must not be null");
		List<Map<String,Object>> graderList = scoreMapper.getGradesByCourseID(courseID);		
		return graderList;
	}

	@Override
	public boolean updateScore(Score score) throws Exception {
		int flag = scoreMapper.updateScore(score);
		if(flag>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean insertIntoScore(Score score) throws Exception {
		int flag = scoreMapper.insertIntoScore(score);		
		if(flag>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean setCoursePercent(Course course) throws Exception {
		int flag = scoreMapper.setCoursePercent(course);;		
		if(flag>0){
			return true;
		}
		return false;	
	}

	@Override
	public Course queryCoursePercent(int courseID) {
		
		return scoreMapper.queryCoursePercent(courseID);
	}

}
