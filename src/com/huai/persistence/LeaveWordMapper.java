package com.huai.persistence;

import java.util.List;

import com.huai.beans.LeaveWord;

public interface LeaveWordMapper {

	/**
	 * 添加留言
	 * @param leaveWord
	 * @throws Exception
	 */
	public void addLeaveWord(LeaveWord leaveWord) throws Exception;
	/**
	 * 分页查询该课程下的留言
	 * @param page
	 * @param courseID
	 * @return
	 */
	public List<LeaveWord> getLeaveWordByPageAndCourseId(int courseID, int start, int count);
	/**
	 * 根据老师ID查询老师name
	 * @param teacherID
	 * @return
	 */
	public String getTeacherNameByID(int teacherID);
	/**
	 * 获得该课程下的总留言数
	 * @param courseId
	 * @return
	 */
	public int getLeaveWordCount(int courseId);
}
