package com.wiiy.business.dto;

import com.wiiy.business.entity.BusinessCustomer;
import com.wiiy.business.entity.Staffer;

public class CustomerSearchResultDto {
	
	private BusinessCustomer customer;
	private Staffer manager;
	private Staffer legal;
	
	public BusinessCustomer getCustomer() {
		return customer;
	}
	public void setCustomer(BusinessCustomer customer) {
		this.customer = customer;
	}
	public Staffer getManager() {
		return manager;
	}
	public void setManager(Staffer manager) {
		this.manager = manager;
	}
	public Staffer getLegal() {
		return legal;
	}
	public void setLegal(Staffer legal) {
		this.legal = legal;
	}
}
