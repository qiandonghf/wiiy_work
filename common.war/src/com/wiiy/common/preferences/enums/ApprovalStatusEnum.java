package com.wiiy.common.preferences.enums;

public enum ApprovalStatusEnum {
	NOAPPLICATION("未发起"),APPROVAL("审批中"),AGREEMENT("已同意"),REFUSE("已拒绝"),WITHDRAW("已撤回");
	
	private String title;

	ApprovalStatusEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
