package com.wiiy.common.preferences.enums;

public enum ProjectPriorityEnum {
	HIGH("最重要"),MIDDLE("重要"),NORMAL("普通"),LOW("不重要");
	
	private String title;

	ProjectPriorityEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
