package com.wiiy.sale.preferences.enums;
/**
 * 
 * @author qd
 * 满意度
 *
 */
public enum SatisfactionEnum {
	UNCERTAINTY("不确定"),SATISFY("满意"),IDENTITY("认同"),GENERAL("一般"),UNSATISFY("不满意");
	private String title;

	SatisfactionEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
