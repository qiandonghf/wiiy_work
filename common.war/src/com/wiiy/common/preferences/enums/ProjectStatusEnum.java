package com.wiiy.common.preferences.enums;

public enum ProjectStatusEnum {
	PREPARATION("筹备"),INITIATION("立项"),START("开始"),RUNNING("执行"),TERMINATION("终止"),ABANDON("废弃"),END("结束");
	
	private String title;

	ProjectStatusEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
