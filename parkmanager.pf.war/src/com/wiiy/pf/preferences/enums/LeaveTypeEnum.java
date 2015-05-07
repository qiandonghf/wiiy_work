package com.wiiy.pf.preferences.enums;

public enum LeaveTypeEnum {
	GENERAL("公休"),SICK("病假"),OFF("调休"),PERSONAL("事假"),WEDDING("婚假");
	private String title;

	LeaveTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
