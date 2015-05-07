package com.wiiy.common.preferences.enums;

public enum ContractFormEnum {
	VERBAL("口头合同"),WRITTEN("书面合同");
	
	private String title;

	ContractFormEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
