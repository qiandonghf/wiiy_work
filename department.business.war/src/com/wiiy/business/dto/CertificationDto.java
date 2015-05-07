package com.wiiy.business.dto;

import java.util.Date;

import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.core.entity.DataDict;

public class CertificationDto {
	private String customer;
	private String serialNo;
	private DataDict type;
	private BooleanEnum pub;
	private Date certTime;
	private String name;
	private String agency;
	private String summery;
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
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
	public BooleanEnum getPub() {
		return pub;
	}
	public void setPub(BooleanEnum pub) {
		this.pub = pub;
	}
	public Date getCertTime() {
		return certTime;
	}
	public void setCertTime(Date certTime) {
		this.certTime = certTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public String getSummery() {
		return summery;
	}
	public void setSummery(String summery) {
		this.summery = summery;
	}
	
}
