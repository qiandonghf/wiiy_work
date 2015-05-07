package com.wiiy.synthesis.preferences.enums;

public enum LaborContractTypeEnum {
	PROBATION("试用期合同"),PARTTIME("兼职合同"),FORMAL("正式合同");
	private String title;

	LaborContractTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
