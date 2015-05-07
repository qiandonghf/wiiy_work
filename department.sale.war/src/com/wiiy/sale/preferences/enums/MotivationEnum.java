package com.wiiy.sale.preferences.enums;
/**
 * 
 * @author qd
 * 购房动机 
 *
 */
public enum MotivationEnum {
	UNCERTAINTY("不确定"),VERYGOOD("首次购房"),GOOD("改善住房条件"),JOBDEMAND("工作需要"),INVEST("投资"),OTHER("其他原因");
	private String title;

	MotivationEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
