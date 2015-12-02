package com.huai.persistence;

import java.util.List;
import java.util.Map;

import com.huai.beans.Course;
import com.huai.beans.Score;

public interface ScoreMapper {
	
	/**
	 * 通過courseID查詢成績
	 * @param courseID
	 * @return
	 */
	List<Map<String,Object>> getGradesByCourseID(int courseID);
	
	/**
	 * 更新成績
	 * @param score
	 * @return 
	 * @throws Exception
	 */
	int updateScore(Score score) throws Exception;
	
	/**
	 * 插入成績
	 * @param score
	 * @return 
	 * @throws Exception
	 */
	int insertIntoScore(Score score) throws Exception;
	
	/**
	 * 設置成績所佔百分比
	 * @param course
	 * @return 
	 * @throws Exception
	 */
	int setCoursePercent(Course course) throws Exception;
	
	/**
	 * 查詢成績所佔百分比
	 * @param courseID
	 * @return Course
	 */			
	Course queryCoursePercent(int courseID);

}
