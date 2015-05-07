package com.wiiy.common.preferences.enums;

public enum SettlementNatureEnum {
	ZCZF("正常支付"),WYPF("违约赔付"),ZJJF("追加经费");
	
	private String title;

	SettlementNatureEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
