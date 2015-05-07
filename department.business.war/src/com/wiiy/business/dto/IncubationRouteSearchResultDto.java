package com.wiiy.business.dto;

import java.util.Map;

import com.wiiy.business.entity.BusinessCustomer;
import com.wiiy.business.entity.IncubationRoute;

public class IncubationRouteSearchResultDto {
	
	private Long customerId;
	private BusinessCustomer customer;
	private Map<String,IncubationRoute> map;
	
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
	public Map<String, IncubationRoute> getMap() {
		return map;
	}
	public void setMap(Map<String, IncubationRoute> map) {
		this.map = map;
	}
	
}
