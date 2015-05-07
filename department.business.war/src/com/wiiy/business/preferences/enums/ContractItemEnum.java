package com.wiiy.business.preferences.enums;

/**
 * 合同类型
 */
public enum ContractItemEnum {

	DSZLHT("大厦租赁合同"),CFZLHT("厂房租赁合同"),FHXY("虚拟合同");
	
    private String title;

    ContractItemEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}	
}
