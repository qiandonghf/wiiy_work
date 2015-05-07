package com.wiiy.common.preferences.enums;

public enum DepartmentEnum {
	SYNTHESIS("综合部"),ENGINEERING("工程部"),ESTATE("物业"),SALE("销售"),BUSINESS("招商");
	
	private String title;

	DepartmentEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
