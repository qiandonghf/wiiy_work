package com.wiiy.business.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.wiiy.business.preferences.enums.ContractItemEnum;
import com.wiiy.business.preferences.enums.ContractRentStatusEnum;
import com.wiiy.business.preferences.enums.ContractStatusEnum;
import com.wiiy.business.preferences.enums.SettleEnum;
import com.wiiy.core.entity.DataDict;


public class ContractDetailDto {
	private String code;//合同编号
	private String name;//合同名称
	private String customerName;//企业名称
	private String manager;//负责人
	private Date signDate;//合同签定日期
	private Date startDate;//合同开始时间
	private Date endDate;//合同结束时间
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
