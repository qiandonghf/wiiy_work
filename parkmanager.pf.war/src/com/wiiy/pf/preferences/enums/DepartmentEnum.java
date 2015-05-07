package com.wiiy.pf.preferences.enums;

public enum DepartmentEnum {
	SYNTHESIS("综合部"),ENGINEERING("工程部"),ESTATE("物业部"),SALE("销售部"),BUSINESS("招商部");
	
	private String title;

	DepartmentEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
