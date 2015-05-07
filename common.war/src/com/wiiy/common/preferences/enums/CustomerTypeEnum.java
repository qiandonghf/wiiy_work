package com.wiiy.common.preferences.enums;

public enum CustomerTypeEnum {
	BUSINESS("商业"),ENTERPRISE("企业"),PERSONAL("个人");
	private String title;

	CustomerTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
