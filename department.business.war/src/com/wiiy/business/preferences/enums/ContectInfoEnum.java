package com.wiiy.business.preferences.enums;

public enum ContectInfoEnum {
	NORMAL("开启"), LOCKED("关闭");
	    
	private String title;

	ContectInfoEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
