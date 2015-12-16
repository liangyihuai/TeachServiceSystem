package com.huai.service;

import java.util.List;

import com.huai.beans.LeaveWord;
import com.huai.utils.LeaveWordInfo;

public interface LeaveWordService {

	/**
	 * 获得留言，并包装成LeaveWordInfo，并安装留言时间降序排序
	 * @param page
	 * @param courseId
	 * @return
	 */
	public List<LeaveWordInfo> listLeaveWord(int page, int courseId);
	/**
	 * 获得总页数
	 * @param courseId
	 * @return
	 */
	public int getPageCount(int courseId);
	/**
	 * 添加留言
	 * @param leaveWord
	 * @return
	 */
	public int addLeaveWord(LeaveWord leaveWord);
}
