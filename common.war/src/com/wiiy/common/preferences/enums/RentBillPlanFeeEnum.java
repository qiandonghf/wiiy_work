package com.wiiy.common.preferences.enums;
/**
 * 绉熻祦鍚堝悓璧勯噾璁″垝璐圭敤绫诲瀷
 * @author Aswan
 *
 */
public enum RentBillPlanFeeEnum {
    RENT("g"), 
    MANAGE("gg"),
    ENERGY("ggg");
	
	private String title;

	RentBillPlanFeeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
