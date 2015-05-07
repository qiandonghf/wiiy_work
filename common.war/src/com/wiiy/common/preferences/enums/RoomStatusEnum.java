package com.wiiy.common.preferences.enums;

public enum RoomStatusEnum {
	//IDLE("空闲"),BOOK("预定"),FITMENT("装修"),USING("占用"),SALED("已售"),NOSALE("未售"),KEPPING("预留"),SUBSCRIBE("认购");
	IDLE("空闲"),BOOK("预定/预留"),FITMENT("装修"),USING("已出租"),SALED("已出售");
	private String title;

	RoomStatusEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}	
	
}
