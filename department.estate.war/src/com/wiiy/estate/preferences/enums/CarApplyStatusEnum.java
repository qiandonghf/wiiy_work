package com.wiiy.estate.preferences.enums;
/**
 * 
 * @author Aswan
 * 车辆申请状态
 *
 */
public enum CarApplyStatusEnum{
	PENDING("未审批"),SAGREE("审批同意"),DISAGREE("审批拒绝");
	private String title;

	CarApplyStatusEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
