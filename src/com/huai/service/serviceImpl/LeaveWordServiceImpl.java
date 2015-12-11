package com.huai.service.serviceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huai.beans.LeaveWord;
import com.huai.persistence.LeaveWordMapper;
import com.huai.persistence.StudentMapper;
import com.huai.service.LeaveWordService;
import com.huai.utils.LeaveWordInfo;
import com.huai.utils.MyDateFormat;

@Service
public class LeaveWordServiceImpl implements LeaveWordService{
	
	private LeaveWordMapper leaveWordMapper;
	private StudentMapper studentMapper;
	
	@Autowired
	public void setLeaveWordMapper(LeaveWordMapper leaveWordMapper) {
		this.leaveWordMapper = leaveWordMapper;
	}
	@Autowired
	public void setStudentMapper(StudentMapper studentMapper) {
		this.studentMapper = studentMapper;
	}

	@Override
	@Transactional
	public int addLeaveWord(LeaveWord leaveWord) {
		try {
			leaveWordMapper.addLeaveWord(leaveWord);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public List<LeaveWordInfo> listLeaveWord(int page, int courseId) {
		List<LeaveWordInfo> leaveWordInfos = new ArrayList<LeaveWordInfo>();
		
		List<LeaveWord> leaveWords = leaveWordMapper.getLeaveWordByPageAndCourseId(courseId, (page-1)*10, 10);
		leaveWords.sort(new Comparator<LeaveWord>() {
			@Override
			public int compare(LeaveWord o1, LeaveWord o2) {
				return o2.getTime().compareTo(o1.getTime());
			}
		});
		
		for (LeaveWord leaveWord : leaveWords) {
			LeaveWordInfo leaveWordInfo = new LeaveWordInfo();
			leaveWordInfo.setContent(leaveWord.getContent());
			leaveWordInfo.setTime(MyDateFormat.changeDateToLongString(leaveWord.getTime()));
			if (leaveWord.getStudentID() != -1)
				leaveWordInfo.setWriter(studentMapper.getStudentByStudentID(leaveWord.getStudentID()).getName());
			else
				leaveWordInfo.setWriter(leaveWordMapper.getTeacherNameByID(leaveWord.getTeacherID())+"老师");
			leaveWordInfos.add(leaveWordInfo);
		}
		
		return leaveWordInfos;
	}
	
}
