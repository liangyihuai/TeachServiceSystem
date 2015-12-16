package com.huai.service;

import java.util.List;

import com.huai.beans.Source;

public interface SourceService {
	
	/**
	 * 添加文件
	 * @param source
	 * @return 成功true 失败false
	 */
	boolean uploadFile(Source source);
	
	/**
	 * 查询文件列表
	 * @param courseID
	 * @return List<Source>
	 */
	List<Source> getFileList(int courseID);
	
	/**
	 * 删除文件
	 * @param sourceID
	 * @return 成功true 失败false
	 */
	boolean deleteFile(int sourceID);
	
	/**
	 * 通过sourceID查询文件
	 * @param sourceID
	 * @return Source
	 */
	Source getSourceBySourceID(int sourceID);

}
