package com.wiiy.sale.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.core.entity.DataDict;
import com.wiiy.core.entity.User;
import com.wiiy.sale.entity.SaleCustomer;
import com.wiiy.sale.preferences.enums.InfoEnum;

/**
 * <br/>class-description 来电/来访
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class ContectInfo extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 客户
	 */
	@FieldDescription("客户")
	private SaleCustomer customer;
	/**
	 * 接待人员
	 */
	@FieldDescription("接待人员")
	private User receiver;
	/**
	 * 交往类型
	 */
	@FieldDescription("交往类型")
	private DataDict type;
	/**
	 * 客户外键
	 */
	@FieldDescription("客户外键")
	private Long customerId;
	/**
	 * 接待人员外键
	 */
	@FieldDescription("接待人员外键")
	private Long receiverId;
	/**
	 * 回访提醒
	 */
	@FieldDescription("回访提醒")
	private Date returnVisit;
	/**
	 * 上次回访时间
	 */
	@FieldDescription("上次回访时间")
	private Date returnTime;
	/**
	 * 时间
	 */
	@FieldDescription("时间")
	private Date time;
	/**
	 * 状态
	 */
	@FieldDescription("状态")
	private InfoEnum infoStatus;
	/**
	 * 交往类型外键
	 */
	@FieldDescription("交往类型外键")
	private String typeId;
	/**
	 * 情况
	 */
	@FieldDescription("情况")
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
	 * 客户
	 */
	public SaleCustomer getCustomer(){
		return customer;
	}
	public void setCustomer(SaleCustomer customer){
		this.customer = customer;
	}
	/**
	 * 接待人员
	 */
	public User getReceiver(){
		return receiver;
	}
	public void setReceiver(User receiver){
		this.receiver = receiver;
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
	 * 客户外键
	 */
	public Long getCustomerId(){
		return customerId;
	}
	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}
	/**
	 * 接待人员外键
	 */
	public Long getReceiverId(){
		return receiverId;
	}
	public void setReceiverId(Long receiverId){
		this.receiverId = receiverId;
	}
	/**
	 * 回访提醒
	 */
	public Date getReturnVisit(){
		return returnVisit;
	}
	public void setReturnVisit(Date returnVisit){
		this.returnVisit = returnVisit;
	}
	/**
	 * 上次回访时间
	 */
	public Date getReturnTime(){
		return returnTime;
	}
	public void setReturnTime(Date returnTime){
		this.returnTime = returnTime;
	}
	/**
	 * 时间
	 */
	public Date getTime(){
		return time;
	}
	public void setTime(Date time){
		this.time = time;
	}
	/**
	 * 状态
	 */
	public InfoEnum getInfoStatus(){
		return infoStatus;
	}
	public void setInfoStatus(InfoEnum infoStatus){
		this.infoStatus = infoStatus;
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
	 * 情况
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