package com.wiiy.commons.preferences.enums;

public enum UserTypeEnum {
	Center("中心帐号"),Customer("企业"),OTHER("其他");
	
	private String title;

	UserTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
