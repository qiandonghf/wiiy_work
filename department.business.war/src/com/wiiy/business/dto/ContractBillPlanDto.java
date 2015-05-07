package com.wiiy.business.dto;

import java.util.Date;

import com.wiiy.business.preferences.enums.DepositTypeEnum;
import com.wiiy.common.preferences.enums.BusinessFeeEnum;

public class ContractBillPlanDto {
	
	private BusinessFeeEnum feeType;
	private DepositTypeEnum depositType;
	private Double price;//单价
	private Double total;//每条计划的金额数
	
	private Date payDate;
	private Date endDate;
	private Date startDate;
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public BusinessFeeEnum getFeeType() {
		return feeType;
	}
	
	public void setFeeType(BusinessFeeEnum feeType) {
		this.feeType = feeType;
	}
	public DepositTypeEnum getDepositType() {
		return depositType;
	}
	public void setDepositType(DepositTypeEnum depositType) {
		this.depositType = depositType;
	}
}
