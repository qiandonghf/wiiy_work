package com.wiiy.sale.preferences.enums;
/**
 * 
 * @author qd
 * 客户类型
 *
 */
public enum CustomerTypeEnum {
	INTENTION("意向客户"),OFFICIALLY("正式客户");
	private String title;

	CustomerTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
