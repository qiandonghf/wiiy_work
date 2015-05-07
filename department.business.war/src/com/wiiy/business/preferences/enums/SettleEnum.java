package com.wiiy.business.preferences.enums;
/**
 * 报表类型
 * @author zl
 * 
 */
public enum SettleEnum {
	THREEMONTH("季度"),SIXMONTH("半年");
	private String title;

	SettleEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
