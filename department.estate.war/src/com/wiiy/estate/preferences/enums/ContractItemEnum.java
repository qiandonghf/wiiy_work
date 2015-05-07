package com.wiiy.estate.preferences.enums;

/**
 * 合同类型
 */
public enum ContractItemEnum {

	WYHT("物业合同");
	
    private String title;

    ContractItemEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}	
}
