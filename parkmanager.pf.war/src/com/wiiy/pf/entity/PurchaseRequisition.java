package com.wiiy.pf.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;

/**
 * <br/>class-description 采购申请单
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class PurchaseRequisition extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 流程实例ID
	 */
	@FieldDescription("流程实例ID")
	private String processInstanceId;
	/**
	 * 用户ID
	 */
	@FieldDescription("用户ID")
	private String userId;
	/**
	 * 申请时间
	 */
	@FieldDescription("申请时间")
	private Date applyTime;
	/**
	 * 申购部门
	 */
	@FieldDescription("申购部门")
	private String purchaseDepartment;
	/**
	 * 申购人
	 */
	@FieldDescription("申购人")
	private String requisitioner;
	/**
	 * 经办人
	 */
	@FieldDescription("经办人")
	private String operator;
	/**
	 * 意见
	 */
	@FieldDescription("意见")
	private String opinion;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 流程实例ID
	 */
	public String getProcessInstanceId(){
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId){
		this.processInstanceId = processInstanceId;
	}
	/**
	 * 用户ID
	 */
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 * 申请时间
	 */
	public Date getApplyTime(){
		return applyTime;
	}
	public void setApplyTime(Date applyTime){
		this.applyTime = applyTime;
	}
	/**
	 * 申购部门
	 */
	public String getPurchaseDepartment(){
		return purchaseDepartment;
	}
	public void setPurchaseDepartment(String purchaseDepartment){
		this.purchaseDepartment = purchaseDepartment;
	}
	/**
	 * 申购人
	 */
	public String getRequisitioner(){
		return requisitioner;
	}
	public void setRequisitioner(String requisitioner){
		this.requisitioner = requisitioner;
	}
	/**
	 * 经办人
	 */
	public String getOperator(){
		return operator;
	}
	public void setOperator(String operator){
		this.operator = operator;
	}
	/**
	 * 意见
	 */
	public String getOpinion(){
		return opinion;
	}
	public void setOpinion(String opinion){
		this.opinion = opinion;
	}
}