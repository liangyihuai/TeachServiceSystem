package com.huai.utils;

/**
 * 简化留言信息，用于发送给前端处理
 * @author Terance
 *
 */
public class LeaveWordInfo {
		private String writer;
		private String time;
		private String content;
		public String getWriter() {
			return writer;
		}
		public void setWriter(String writer) {
			this.writer = writer;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	}