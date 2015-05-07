package com.wiiy.commons.preferences.enums;

public enum BooleanEnum {
	YES("是"),NO("否");
	
	private String title;

	BooleanEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
