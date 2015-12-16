package com.huai.persistence;

import java.util.List;
import com.huai.beans.Source;

public interface SourceMapper {
	
	/**
	 * 添加文件
	 * @param source
	 * @return 
	 */
	int uploadFile(Source source);
	
	/**
	 * 查询文件列表
	 * @param courseID
	 * @return
	 */
	List<Source> getFileList(int courseID);
	
	/**
	 * 删除文件
	 * @param sourceID
	 * @return
	 */
	int deleteFile(int sourceID);
	
	/**
	 * 通过sourceID查询文件
	 * @param sourceID
	 * @return Source
	 */
	Source getSourceBySourceID(int sourceID);
	
}
