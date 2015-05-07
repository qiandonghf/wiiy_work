package com.wiiy.common.preferences.enums;
/**
 * 资金计划状态
 * @author Aswan
 *
 */
public enum ContractAttTypeEnum {
    BGWJ("变更文件"), 
    HTWB("合同文本"),  
    HTFJ("合同附件"),
    HTFT("合同附图"),
    BCXY("补充协议");

	
	private String title;

	ContractAttTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
