package com.wiiy.sale.preferences.enums;
/**
 * 
 * @author qd
 * 客户等级
 *
 */
public enum CustomerLevelEnum {
	A("A"),B("B"),C("C"),D("D");
	private String title;

	CustomerLevelEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
