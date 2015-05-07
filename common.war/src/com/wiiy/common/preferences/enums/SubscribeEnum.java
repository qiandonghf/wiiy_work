package com.wiiy.common.preferences.enums;

public enum SubscribeEnum {
	SHOP("商铺"),RESIDENTIAL("住宅");
	private String title;

	SubscribeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
