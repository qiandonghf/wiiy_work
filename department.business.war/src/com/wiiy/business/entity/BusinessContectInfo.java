package com.wiiy.business.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.business.entity.BusinessCustomer;
import com.wiiy.business.entity.Contect;
import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.core.entity.DataDict;

/**
 * <br/>class-description 交往信息
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class BusinessContectInfo extends BaseEntity implements Serializable {
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
	 * 联系人
	 */
	@FieldDescription("联系人")
	private Contect contect;
	/**
	 * 交往类型
	 */
	@FieldDescription("交往类型")
	private DataDict type;
	/**
	 * 企业外键
	 */
	@FieldDescription("企业外键")
	private Long customerId;
	/**
	 * 联系人外键
	 */
	@FieldDescription("联系人外键")
	private Long contectId;
	/**
	 * 交往类型外键
	 */
	@FieldDescription("交往类型外键")
	private String typeId;
	/**
	 * 交往开始时间
	 */
	@FieldDescription("交往开始时间")
	private Date startTime;
	/**
	 * 交往结束时间
	 */
	@FieldDescription("交往结束时间")
	private Date endTime;
	/**
	 * 交往内容
	 */
	@FieldDescription("交往内容")
	private String content;
	/**
	 * 备注
	 */
	@FieldDescription("备注")
	private String memo;

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
	 * 联系人
	 */
	public Contect getContect(){
		return contect;
	}
	public void setContect(Contect contect){
		this.contect = contect;
	}
	/**
	 * 交往类型
	 */
	public DataDict getType(){
		return type;
	}
	public void setType(DataDict type){
		this.type = type;
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
	 * 联系人外键
	 */
	public Long getContectId(){
		return contectId;
	}
	public void setContectId(Long contectId){
		this.contectId = contectId;
	}
	/**
	 * 交往类型外键
	 */
	public String getTypeId(){
		return typeId;
	}
	public void setTypeId(String typeId){
		this.typeId = typeId;
	}
	/**
	 * 交往开始时间
	 */
	public Date getStartTime(){
		return startTime;
	}
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	/**
	 * 交往结束时间
	 */
	public Date getEndTime(){
		return endTime;
	}
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	/**
	 * 交往内容
	 */
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}
	/**
	 * 备注
	 */
	public String getMemo(){
		return memo;
	}
	public void setMemo(String memo){
		this.memo = memo;
	}
}