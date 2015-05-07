package com.wiiy.estate.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.common.preferences.enums.ProjectPriorityEnum;
import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.estate.preferences.enums.ComplaintAcceptStatusEnum;

/**
 * <br/>class-description 企业投诉
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class Complaint extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 投诉人
	 */
	@FieldDescription("投诉人")
	private String name;
	/**
	 * 联系电话
	 */
	@FieldDescription("联系电话")
	private String tel;
	/**
	 * 企业外键
	 */
	@FieldDescription("企业外键")
	private Long customerId;
	/**
	 * 企业名称
	 */
	@FieldDescription("企业名称")
	private String customerName;
	/**
	 * 投诉时间
	 */
	@FieldDescription("投诉时间")
	private Date complaintTime;
	/**
	 * 投诉对象
	 */
	@FieldDescription("投诉对象")
	private String complaintObject;
	/**
	 * 投诉内容
	 */
	@FieldDescription("投诉内容")
	private String content;
	/**
	 * 状态
	 */
	@FieldDescription("状态")
	private ComplaintAcceptStatusEnum status;
	/**
	 * 重要程度
	 */
	@FieldDescription("重要程度")
	private ProjectPriorityEnum importance;
	/**
	 * 受理人
	 */
	@FieldDescription("受理人")
	private String accepter;
	/**
	 * 处理结果
	 */
	@FieldDescription("处理结果")
	private String result;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 投诉人
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 联系电话
	 */
	public String getTel(){
		return tel;
	}
	public void setTel(String tel){
		this.tel = tel;
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
	 * 企业名称
	 */
	public String getCustomerName(){
		return customerName;
	}
	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}
	/**
	 * 投诉时间
	 */
	public Date getComplaintTime(){
		return complaintTime;
	}
	public void setComplaintTime(Date complaintTime){
		this.complaintTime = complaintTime;
	}
	/**
	 * 投诉对象
	 */
	public String getComplaintObject(){
		return complaintObject;
	}
	public void setComplaintObject(String complaintObject){
		this.complaintObject = complaintObject;
	}
	/**
	 * 投诉内容
	 */
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}
	/**
	 * 状态
	 */
	public ComplaintAcceptStatusEnum getStatus(){
		return status;
	}
	public void setStatus(ComplaintAcceptStatusEnum status){
		this.status = status;
	}
	/**
	 * 重要程度
	 */
	public ProjectPriorityEnum getImportance(){
		return importance;
	}
	public void setImportance(ProjectPriorityEnum importance){
		this.importance = importance;
	}
	/**
	 * 受理人
	 */
	public String getAccepter(){
		return accepter;
	}
	public void setAccepter(String accepter){
		this.accepter = accepter;
	}
	/**
	 * 处理结果
	 */
	public String getResult(){
		return result;
	}
	public void setResult(String result){
		this.result = result;
	}
}