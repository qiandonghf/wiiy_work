package com.wiiy.pf.preferences.enums;
/**
 * 
 * @author Aswan
 */
public enum SuspensionStateEnum {
	
	PENDING("挂起"),ACTIVE("激活");
	private String title;

	SuspensionStateEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
