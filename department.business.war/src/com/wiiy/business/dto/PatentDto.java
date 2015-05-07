package com.wiiy.business.dto;

import java.util.Date;

import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.core.entity.DataDict;

public class PatentDto {
	private String customer;//企业
	private String name;//专利名称
	private String serialNo;//专利号
	private DataDict type;//专利类型
	private Date buyTime;//授权购买日期
	private DataDict state;//专利状态
	private String appler;//专利申请人
	private Date applyTime;//专利申请日期
	private DataDict source;//专利来源
	private Date expireTime;//专利失效日期
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
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public DataDict getType() {
		return type;
	}
	public void setType(DataDict type) {
		this.type = type;
	}
	public Date getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}
	public DataDict getState() {
		return state;
	}
	public void setState(DataDict state) {
		this.state = state;
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
	public DataDict getSource() {
		return source;
	}
	public void setSource(DataDict source) {
		this.source = source;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public BooleanEnum getPub() {
		return pub;
	}
	public void setPub(BooleanEnum pub) {
		this.pub = pub;
	}
	
}
