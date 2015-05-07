package com.wiiy.pf.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;

/**
 * <br/>class-description 内部联系单
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class ContactForm extends BaseEntity implements Serializable {
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
	 * 发出时间
	 */
	@FieldDescription("发出时间")
	private Date sendTime;
	/**
	 * 部门
	 */
	@FieldDescription("部门")
	private String department;
	/**
	 * 工作事宜
	 */
	@FieldDescription("工作事宜")
	private String affairs;

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
	 * 发送时间
	 */
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	/**
	 * 部门
	 */
	public String getDepartment(){
		return department;
	}
	public void setDepartment(String department){
		this.department = department;
	}
	/**
	 * 工作事宜
	 */
	public String getAffairs(){
		return affairs;
	}
	public void setAffairs(String affairs){
		this.affairs = affairs;
	}
}