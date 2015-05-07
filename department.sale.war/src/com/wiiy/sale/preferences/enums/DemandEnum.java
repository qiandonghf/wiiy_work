package com.wiiy.sale.preferences.enums;
/**
 * 
 * @author qd
 * 需求级别
 *
 */
public enum DemandEnum {
	UNCERTAINTY("不确定"),VERYGOOD("很好"),GOOD("可以"),GENERAL("一般"),LOOKAROUND("随便看看"),NOINTENTION("无购买意向");
	private String title;

	DemandEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
