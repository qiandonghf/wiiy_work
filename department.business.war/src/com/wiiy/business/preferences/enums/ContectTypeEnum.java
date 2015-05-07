package com.wiiy.business.preferences.enums;
/**
 * 线索类型
 * @author aswan
 *
 */
public enum ContectTypeEnum {
	LD("来电"),LF("来访");
	private String title;

	ContectTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
