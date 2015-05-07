package com.wiiy.commons.preferences.enums;
/**
 * 称谓枚举
 * @author Aswan
 *
 */
public enum TitleEnum {
	MADAM("女士"),MISTER("先生");
	
	private String title;

	TitleEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
