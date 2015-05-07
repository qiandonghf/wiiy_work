package com.wiiy.commons.preferences.enums;


public enum EntityStatus {

    NORMAL("正常"), LOCKED("禁用");
    
	private String title;

    EntityStatus(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
