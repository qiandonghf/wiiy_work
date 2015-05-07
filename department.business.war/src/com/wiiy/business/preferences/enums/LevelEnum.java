package com.wiiy.business.preferences.enums;

public enum LevelEnum {
	A("A"),B("B"),C("C"),D("D");
	private String title;

	LevelEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
