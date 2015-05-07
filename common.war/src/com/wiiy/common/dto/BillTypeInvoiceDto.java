package com.wiiy.common.dto;

import java.util.Date;


public class BillTypeInvoiceDto {
	private Double money;//金额
	private String typeName;//费用类型
	private Double factPayment;//实际支付金额
	private Date payTime;//实际支付日期
	private Date feeStartTime;//计费开始时间
	private Date feeEndTime;//计费结束时间
	private Date checkoutTime;//出账日期
	private Double penalty;//违约金
	public Date getCheckoutTime() {
		return checkoutTime;
	}
	public void setCheckoutTime(Date checkoutTime) {
		this.checkoutTime = checkoutTime;
	}
	public Double getPenalty() {
		return penalty;
	}
	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Double getFactPayment() {
		return factPayment;
	}
	public void setFactPayment(Double factPayment) {
		this.factPayment = factPayment;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public Date getFeeStartTime() {
		return feeStartTime;
	}
	public void setFeeStartTime(Date feeStartTime) {
		this.feeStartTime = feeStartTime;
	}
	public Date getFeeEndTime() {
		return feeEndTime;
	}
	public void setFeeEndTime(Date feeEndTime) {
		this.feeEndTime = feeEndTime;
	}
	
	
}
