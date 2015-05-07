package com.wiiy.common.preferences.enums;
/**
 * 物业资金计划费用类型BusinessFeeEnum
 * @author Aswan
 *
 */
public enum EstateFeeEnum {
    ESTATE_WY("物业费"), 
	ESTATE_GGNH("公共能耗费"),
	ESTATE_LJQY("垃圾消运费"),
	ESTATE_SF("水费"),
	ESTATE_DF("电费"),
	ESTATE_CWF("物业车位费"),
	ESTATE_YJF("物业预缴费");
	
	private String title;

	EstateFeeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
