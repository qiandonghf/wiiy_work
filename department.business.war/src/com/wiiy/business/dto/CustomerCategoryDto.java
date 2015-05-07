package com.wiiy.business.dto;

import java.util.List;

import com.wiiy.business.entity.CustomerCategory;
import com.wiiy.business.entity.CustomerLabel;

public class CustomerCategoryDto {
	
	private CustomerCategory customerCategory;
	private List<CustomerLabel> customerLabelList;
	
	public CustomerCategory getCustomerCategory() {
		return customerCategory;
	}
	public void setCustomerCategory(CustomerCategory customerCategory) {
		this.customerCategory = customerCategory;
	}
	public List<CustomerLabel> getCustomerLabelList() {
		return customerLabelList;
	}
	public void setCustomerLabelList(List<CustomerLabel> customerLabelList) {
		this.customerLabelList = customerLabelList;
	}

}
