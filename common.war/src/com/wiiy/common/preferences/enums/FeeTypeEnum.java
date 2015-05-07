package com.wiiy.common.preferences.enums;
/**
 * 资金计划费用类型FeeTypeEnum
 * @author Aswan
 *
 */
public enum FeeTypeEnum {
    BUSINESS_ZJ("招商租金"), 
    BUSINESS_YJ("招商押金"),
    BUSINESS_GKF("招商挂靠费"),
    ESTATE_WY("物业费"), 
	ESTATE_GGNH("公共能耗费"),
	ESTATE_LJQY("垃圾消运费"),
	ESTATE_SF("水费"),
	ESTATE_DF("电费"),
	ESTATE_CWF("物业车位费"),
	ESTATE_YJF("物业预缴费"),
	SALE_FWXS("房屋销售款"), 
	SALE_CWXS("车位销售款"), 
	SALE_ZJ("销售租金"), 
	SALE_YJ("销售押金"),
	ENGINEERING_HTK("工程合同款"),
	SYNTHESIS_HTK("综合部合同款");
	
	private String title;

	FeeTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
