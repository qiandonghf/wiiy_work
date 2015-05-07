package com.wiiy.sale.dto;

import java.util.List;

import com.wiiy.sale.entity.SaleCustomerCategory;
import com.wiiy.sale.entity.SaleCustomerLabel;

public class SaleCustomerCategoryDto {
	
	private SaleCustomerCategory customerCategory;
	private List<SaleCustomerLabel> customerLabelList;
	
	public SaleCustomerCategory getSaleCustomerCategory() {
		return customerCategory;
	}
	public void setSaleCustomerCategory(SaleCustomerCategory customerCategory) {
		this.customerCategory = customerCategory;
	}
	public List<SaleCustomerLabel> getSaleCustomerLabelList() {
		return customerLabelList;
	}
	public void setSaleCustomerLabelList(List<SaleCustomerLabel> customerLabelList) {
		this.customerLabelList = customerLabelList;
	}

}
