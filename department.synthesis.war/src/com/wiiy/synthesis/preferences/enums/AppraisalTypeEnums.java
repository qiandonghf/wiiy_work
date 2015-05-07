package com.wiiy.synthesis.preferences.enums;
/**
 * 考核类型
 * @author Aswan
 *
 */
public enum AppraisalTypeEnums {
//	UNPAID ("未结算"), 
//    FULLPAID("结算"),
	ANNUAL("年度考核"),
	MONTHLY("月度考核");

	
	private String title;

	AppraisalTypeEnums(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
