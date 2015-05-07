package com.wiiy.common.preferences.enums;
/**
 * 综合资金计划费用类型BusinessFeeEnum
 * @author Aswan
 *
 */
public enum SynthesisFeeEnum {
	SYNTHESIS_HTK("综合部合同款");
	
	private String title;

	SynthesisFeeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
