package com.wiiy.common.preferences.enums;

public enum CustomerSupplierTypeEnum {
	CUSTOMER("客户"),SUPPLIER("供应商");
	private String title;

	CustomerSupplierTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
