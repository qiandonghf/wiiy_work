package com.wiiy.commons.preferences.enums;

public enum GenderEnum {
	Male("男"),Female("女");
	
	private String title;

	GenderEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
