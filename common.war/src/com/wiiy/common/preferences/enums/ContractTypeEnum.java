package com.wiiy.common.preferences.enums;

public enum ContractTypeEnum {
	SWHT("商务合同"),ZLHT("租赁合同"),PYHT("聘用合同"),CGHT("采购合同"),XSHT("销售合同");
	
	private String title;

	ContractTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
