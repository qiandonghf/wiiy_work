package com.wiiy.commons.taglib;

import java.util.Map;

public class EnumRadioTaglib extends AbstractRadioTaglib {
	
	private String type;
	
	@Override
	protected Map<String, String> getValueMap() {
		return EnumTaglibHelper.getValueMapByType(type);
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
}
