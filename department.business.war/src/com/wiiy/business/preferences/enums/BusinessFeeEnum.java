package com.wiiy.business.preferences.enums;
/**
 * 招商资金计划费用类型BusinessFeeEnum
 * @author Aswan
 *
 */
public enum BusinessFeeEnum {
    BUSINESS_ZJ("招商租金"), 
    BUSINESS_YJ("招商押金"),
    BUSINESS_GKF("招商挂靠费");
	
	private String title;

	BusinessFeeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
