package com.wiiy.business.preferences.enums;
/**
 * 
 * @author Aswan
 * 项目状态
 *种子期、成长期、扩张期、成熟期、IPO前期
 */
public enum ProjectStatusEnum{
	SEED("种子期"),GROWTH("成长期"),EXPANSION("扩张期"),MATURE("成熟期"),IPO("IPO前期");
	private String title;

	ProjectStatusEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
