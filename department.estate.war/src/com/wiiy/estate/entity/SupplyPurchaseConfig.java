package com.wiiy.estate.entity;

import java.io.Serializable;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.estate.entity.SupplyPurchase;
import com.wiiy.estate.entity.SupplyPurchaseForm;

/**
 * <br/>class-description 办公用品采购关联
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class SupplyPurchaseConfig extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 采购申请表外键
	 */
	@FieldDescription("采购申请表外键")
	private SupplyPurchaseForm supplyPurchaseForm;
	/**
	 * 采购物品外键
	 */
	@FieldDescription("采购物品外键")
	private SupplyPurchase supplyPurchase;
	/**
	 * 采购申请表外键ID
	 */
	@FieldDescription("采购申请表外键ID")
	private Long supplyPurchaseFormId;
	/**
	 * 采购物品外键ID
	 */
	@FieldDescription("采购物品外键ID")
	private Long supplyPurchaseId;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 采购申请表外键
	 */
	public SupplyPurchaseForm getSupplyPurchaseForm(){
		return supplyPurchaseForm;
	}
	public void setSupplyPurchaseForm(SupplyPurchaseForm supplyPurchaseForm){
		this.supplyPurchaseForm = supplyPurchaseForm;
	}
	/**
	 * 采购物品外键
	 */
	public SupplyPurchase getSupplyPurchase(){
		return supplyPurchase;
	}
	public void setSupplyPurchase(SupplyPurchase supplyPurchase){
		this.supplyPurchase = supplyPurchase;
	}
	/**
	 * 采购申请表外键ID
	 */
	public Long getSupplyPurchaseFormId(){
		return supplyPurchaseFormId;
	}
	public void setSupplyPurchaseFormId(Long supplyPurchaseFormId){
		this.supplyPurchaseFormId = supplyPurchaseFormId;
	}
	/**
	 * 采购物品外键ID
	 */
	public Long getSupplyPurchaseId(){
		return supplyPurchaseId;
	}
	public void setSupplyPurchaseId(Long supplyPurchaseId){
		this.supplyPurchaseId = supplyPurchaseId;
	}
}