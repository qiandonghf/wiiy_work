package com.wiiy.business.dto;

import java.util.Map;

import com.wiiy.business.entity.BusinessCustomer;
import com.wiiy.business.entity.CustomerVentureType;

public class CustomerVentureTypeSearchResultDto {
	
	private Long customerId;
	private BusinessCustomer customer;
	private Map<String,CustomerVentureType> map;
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public BusinessCustomer getCustomer() {
		return customer;
	}
	public void setCustomer(BusinessCustomer customer) {
		this.customer = customer;
	}
	public Map<String, CustomerVentureType> getMap() {
		return map;
	}
	public void setMap(Map<String, CustomerVentureType> map) {
		this.map = map;
	}

}
