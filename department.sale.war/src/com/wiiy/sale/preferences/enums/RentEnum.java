package com.wiiy.sale.preferences.enums;

public enum RentEnum {
	RENT("出租"),SALE("出售");
	private String title;

	RentEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
	
}
