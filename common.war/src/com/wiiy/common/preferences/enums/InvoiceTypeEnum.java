package com.wiiy.common.preferences.enums;
/**
 * 开票状态
 * @author Swan
 *
 */
public enum InvoiceTypeEnum {
	NOBILLING("未开票"),
	HASBILLING("已开票");
	
	private String title;

	InvoiceTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
