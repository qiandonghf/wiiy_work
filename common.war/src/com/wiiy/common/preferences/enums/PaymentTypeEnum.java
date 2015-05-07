package com.wiiy.common.preferences.enums;

public enum PaymentTypeEnum {
	GATHERING("收款"),PAYMENT("付款");
	
	private String title;

	PaymentTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
