package com.wiiy.synthesis.preferences.enums;

public enum PositionConditionEnum {
	YES("是"),NO("否");
	private String title;

	PositionConditionEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
