package com.wiiy.common.preferences.enums;

public enum PayTypeEnum {
	
	NETWORK("网络"); 

	
	private String title;

	PayTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

}
