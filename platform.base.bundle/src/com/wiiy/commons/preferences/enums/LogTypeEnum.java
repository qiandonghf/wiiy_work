package com.wiiy.commons.preferences.enums;
/**
 * 
 * @author Aswan
 * 日志类型
 *
 */
public enum LogTypeEnum {
	OP("操作日志"),LOGIN("登录日志"),LOGOUT("登出日志");
	
	private String title;

	LogTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
