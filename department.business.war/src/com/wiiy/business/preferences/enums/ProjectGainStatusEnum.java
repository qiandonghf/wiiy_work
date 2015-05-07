package com.wiiy.business.preferences.enums;
/**
 * 
 * @author Aswan
 * 项目营收情况
 */
public enum ProjectGainStatusEnum{
	GAIN("盈利"),LOSS("亏损");
	private String title;

	ProjectGainStatusEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
