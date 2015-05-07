package com.wiiy.pf.preferences.enums;

public enum ProcessTypeEnum {
	LEAVE("请假申请单"),PARKIN("入孵申请单"),SEALAPPLY("用印申请"),FICTITIOUS("入驻审批单"),BILL("合同实际结算"),CONTACTFORM("内部联系单"),CONTACTINFORM("内部工作联系单"),PURCHASE("采购申请单");
	private String title;

	ProcessTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
