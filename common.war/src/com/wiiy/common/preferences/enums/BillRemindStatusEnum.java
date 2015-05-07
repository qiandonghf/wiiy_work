package com.wiiy.common.preferences.enums;
/**
 * 账单催缴状态
 * @author Aswan
 *
 */
public enum BillRemindStatusEnum {
	UNCALLEDBILL ("未催缴"), 
	CALLEDBILL("已催缴");
	
	private String title;

	BillRemindStatusEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
