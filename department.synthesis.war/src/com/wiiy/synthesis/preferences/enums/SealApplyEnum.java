package com.wiiy.synthesis.preferences.enums;
/**
 * 
 * @author qd
 * 用章状态
 *
 */
public enum SealApplyEnum{
	SAPPLAY("未审批"),SAGREE("审批同意");
	private String title;

	SealApplyEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
