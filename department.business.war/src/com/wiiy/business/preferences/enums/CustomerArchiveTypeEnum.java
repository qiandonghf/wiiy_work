package com.wiiy.business.preferences.enums;

public enum CustomerArchiveTypeEnum {
	
	YYZZ("营业执照"),ZZJG("组织机构代码证"),GSDJ("税务登记证"),KHXKZ("开户许可证"),GSZC("公司章程复印件"),FRGD("法人股东的营业执照"),YZBG("验资报告"),FRSFZ("法人身份证"),FRDB("法定代表人任职文件"),OTHER("其它");
	
	private String title;

	CustomerArchiveTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
