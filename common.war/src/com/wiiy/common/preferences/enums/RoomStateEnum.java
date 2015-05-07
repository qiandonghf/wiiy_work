package com.wiiy.common.preferences.enums;

public enum RoomStateEnum {
	QF("期房"),XF("现房");
	
	private String title;

	RoomStateEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
