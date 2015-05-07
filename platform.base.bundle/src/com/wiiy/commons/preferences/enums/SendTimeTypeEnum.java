package com.wiiy.commons.preferences.enums;
/**
 * 
 * @author Aswan
 *消息定时发送类型
 */
public enum SendTimeTypeEnum {
	NOW("立即发送"),CUSTOMER("定时发送");
	
	private String title;

	SendTimeTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
