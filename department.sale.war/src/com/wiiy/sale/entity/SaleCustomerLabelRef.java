package com.wiiy.sale.entity;

import java.io.Serializable;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.sale.entity.SaleCustomer;
import com.wiiy.sale.entity.SaleCustomerLabel;

/**
 * <br/>class-description 企业标签关系
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class SaleCustomerLabelRef extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 企业
	 */
	@FieldDescription("企业")
	private SaleCustomer customer;
	/**
	 * 企业标签
	 */
	@FieldDescription("企业标签")
	private SaleCustomerLabel customerLabel;
	/**
	 * 企业外键
	 */
	@FieldDescription("企业外键")
	private Long customerId;
	/**
	 * 标签外键
	 */
	@FieldDescription("标签外键")
	private Long labelId;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 企业
	 */
	public SaleCustomer getCustomer(){
		return customer;
	}
	public void setCustomer(SaleCustomer customer){
		this.customer = customer;
	}
	/**
	 * 企业标签
	 */
	public SaleCustomerLabel getCustomerLabel(){
		return customerLabel;
	}
	public void setCustomerLabel(SaleCustomerLabel customerLabel){
		this.customerLabel = customerLabel;
	}
	/**
	 * 企业外键
	 */
	public Long getCustomerId(){
		return customerId;
	}
	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}
	/**
	 * 标签外键
	 */
	public Long getLabelId(){
		return labelId;
	}
	public void setLabelId(Long labelId){
		this.labelId = labelId;
	}
}