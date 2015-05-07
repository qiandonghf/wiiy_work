package com.wiiy.common.preferences.enums;

public enum SettlementTypeEnum {
	BANK("银行"),CASH("现金"),THIRD("第三方");
	
	private String title;

	SettlementTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
