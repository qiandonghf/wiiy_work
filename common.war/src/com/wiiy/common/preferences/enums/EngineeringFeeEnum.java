package com.wiiy.common.preferences.enums;
/**
 * 工程资金计划费用类型BusinessFeeEnum
 * @author Aswan
 *
 */
public enum EngineeringFeeEnum {
	ENGINEERING_HTK("工程合同款");
	
	private String title;

	EngineeringFeeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
