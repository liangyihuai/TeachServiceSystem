package com.huai.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huai.beans.Source;
import com.huai.persistence.SourceMapper;
import com.huai.service.SourceService;

@Service
public class SourceServiceImpl implements SourceService{
	private SourceMapper sourceMapper;
	
	@Autowired
	public void setSourceMapper(SourceMapper sourceMapper) {
		this.sourceMapper = sourceMapper;
	}

	@Override
	public boolean uploadFile(Source source) {
		if(sourceMapper.uploadFile(source) > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<Source> getFileList(int courseID) {
		List<Source> sourceList = sourceMapper.getFileList(courseID);
		
		if(sourceList!=null && sourceList.size()>0){
			return sourceList;
		}else{
			return null;
		}
	}

	@Override
	public boolean deleteFile(int sourceID) {
		if(sourceMapper.deleteFile(sourceID) > 0){
			return true;
		}
		return false;
	}

	@Override
	public Source getSourceBySourceID(int sourceID) {
		
		return sourceMapper.getSourceBySourceID(sourceID);
	}

}
