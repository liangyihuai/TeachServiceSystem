package com.huai.utils;

import java.util.Date;

/**
 * 简化留言信息，用于发送给前端处理
 * @author Terance
 *
 */
public class LeaveWordInfo {
		private String writer;
		private Date time;
		private String content;
		public String getWriter() {
			return writer;
		}
		public void setWriter(String writer) {
			this.writer = writer;
		}
		public Date getTime() {
			return time;
		}
		public void setTime(Date time) {
			this.time = time;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	}