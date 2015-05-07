package com.wiiy.business.entity;

import java.io.Serializable;

import com.wiiy.business.entity.BusinessCustomer;
import com.wiiy.business.preferences.enums.CustomerArchiveTypeEnum;
import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;

/**
 * <br/>class-description 客户档案材料
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class CustomerArchiveAtt extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 企业
	 */
	@FieldDescription("企业")
	private BusinessCustomer customer;
	/**
	 * 企业外键
	 */
	@FieldDescription("企业外键")
	private Long customerId;
	/**
	 * 附件类型
	 */
	@FieldDescription("附件类型")
	private CustomerArchiveTypeEnum attType;
	/**
	 * 附件名称
	 */
	@FieldDescription("附件名称")
	private String name;
	/**
	 * 重命名名称
	 */
	@FieldDescription("重命名名称")
	private String newName;
	/**
	 * 附件大小
	 */
	@FieldDescription("附件大小")
	private Long size;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 企业
	 */
	public BusinessCustomer getCustomer(){
		return customer;
	}
	public void setCustomer(BusinessCustomer customer){
		this.customer = customer;
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
	 * 附件类型
	 */
	public CustomerArchiveTypeEnum getAttType(){
		return attType;
	}
	public void setAttType(CustomerArchiveTypeEnum attType){
		this.attType = attType;
	}
	/**
	 * 附件名称
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 重命名名称
	 */
	public String getNewName(){
		return newName;
	}
	public void setNewName(String newName){
		this.newName = newName;
	}
	/**
	 * 附件大小
	 */
	public Long getSize(){
		return size;
	}
	public void setSize(Long size){
		this.size = size;
	}
}