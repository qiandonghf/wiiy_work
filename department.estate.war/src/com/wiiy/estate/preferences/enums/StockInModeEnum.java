package com.wiiy.estate.preferences.enums;

public enum StockInModeEnum {
	FORMS("按表单"),SUPPLYS("按商品");
    private String title;

    StockInModeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
