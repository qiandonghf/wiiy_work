package com.wiiy.business.dto;

import java.util.Date;

import com.wiiy.business.entity.Product;
import com.wiiy.business.preferences.enums.ProjectApplyStatusEnum;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.core.entity.DataDict;

public class ProjectDto {
	
	private String customer;//企业
	private DataDict stage;//
	private DataDict technic;//技术领域
	private String name;//产品名称
	private String introduction;//备注
	private BooleanEnum pub;//是否发布到网站
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BooleanEnum getPub() {
		return pub;
	}
	public void setPub(BooleanEnum pub) {
		this.pub = pub;
	}
	public DataDict getStage() {
		return stage;
	}
	public void setStage(DataDict stage) {
		this.stage = stage;
	}
	public DataDict getTechnic() {
		return technic;
	}
	public void setTechnic(DataDict technic) {
		this.technic = technic;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
}
