package com.wiiy.sale.preferences.enums;

public enum InfoEnum {
	NORMAL("开启"), LOCKED("关闭");
    
	private String title;

	InfoEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
