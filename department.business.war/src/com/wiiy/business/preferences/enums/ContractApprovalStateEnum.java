package com.wiiy.business.preferences.enums;

public enum ContractApprovalStateEnum {
	
	COMFIRMYES("审批通过"), COMFIRMNO("审批驳回");

	private String title;

	ContractApprovalStateEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
