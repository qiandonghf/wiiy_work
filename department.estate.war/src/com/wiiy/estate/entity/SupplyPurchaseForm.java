package com.wiiy.estate.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.core.entity.User;

/**
 * <br/>class-description 办公用品采购申请表
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class SupplyPurchaseForm extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 申购人
	 */
	@FieldDescription("申购人")
	private User requisitioner;
	/**
	 * 申请表名称
	 */
	@FieldDescription("申请表名称")
	private String name;
	/**
	 * 申请人名称
	 */
	@FieldDescription("申请人名称")
	private String realName;
	/**
	 * 编号
	 */
	@FieldDescription("编号")
	private String code;
	/**
	 * 申请时间
	 */
	@FieldDescription("申请时间")
	private Date applyTime;
	/**
	 * 申购人外键
	 */
	@FieldDescription("申购人外键")
	private Long requisitionerId;
	/**
	 * 备注
	 */
	@FieldDescription("备注")
	private String comment;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 申购人
	 */
	public User getRequisitioner(){
		return requisitioner;
	}
	public void setRequisitioner(User requisitioner){
		this.requisitioner = requisitioner;
	}
	/**
	 * 申请表名称
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 申请人名称
	 */
	public String getRealName(){
		return realName;
	}
	public void setRealName(String realName){
		this.realName = realName;
	}
	/**
	 * 编号
	 */
	public String getCode(){
		return code;
	}
	public void setCode(String code){
		this.code = code;
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
	 * 申购人外键
	 */
	public Long getRequisitionerId(){
		return requisitionerId;
	}
	public void setRequisitionerId(Long requisitionerId){
		this.requisitionerId = requisitionerId;
	}
	/**
	 * 备注
	 */
	public String getComment(){
		return comment;
	}
	public void setComment(String comment){
		this.comment = comment;
	}
}