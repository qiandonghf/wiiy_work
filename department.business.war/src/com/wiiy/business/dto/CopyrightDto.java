package com.wiiy.business.dto;

import java.util.Date;


import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.core.entity.DataDict;

public class CopyrightDto {
	private String customer;//企业
	private DataDict type;
	private String name;
	private String serialNo;
	private String appler;
	private Date applyTime;
	private Date effectivetime;
	private Date expireTime;
	private String summery;
	private BooleanEnum pub;
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public DataDict getType() {
		return type;
	}
	public void setType(DataDict type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getAppler() {
		return appler;
	}
	public void setAppler(String appler) {
		this.appler = appler;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Date getEffectivetime() {
		return effectivetime;
	}
	public void setEffectivetime(Date effectivetime) {
		this.effectivetime = effectivetime;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public String getSummery() {
		return summery;
	}
	public void setSummery(String summery) {
		this.summery = summery;
	}
	public BooleanEnum getPub() {
		return pub;
	}
	public void setPub(BooleanEnum pub) {
		this.pub = pub;
	}
}
