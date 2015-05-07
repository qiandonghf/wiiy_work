package com.wiiy.common.preferences.enums;
/**
 * 销售资金计划费用类型BusinessFeeEnum
 * @author Aswan
 *
 */
public enum SaleFeeEnum {
	SALE_FWXS("房屋销售款"), 
	SALE_CWXS("车位销售款"), 
	SALE_ZJ("销售租金"), 
	SALE_YJ("销售押金");
	
	private String title;

	SaleFeeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
