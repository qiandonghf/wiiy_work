package com.wiiy.estate.dto;

import java.util.Date;

public class SupplyStockInDto {
	private Double amount;
	private Date inTime;
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getInTime() {
		return inTime;
	}
	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}
	public String getBuyMan() {
		return buyMan;
	}
	public void setBuyMan(String buyMan) {
		this.buyMan = buyMan;
	}
	private String buyMan;
	
	
}
