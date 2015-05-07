package com.wiiy.sale.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.core.entity.User;
import com.wiiy.sale.entity.ContectInfo;

/**
 * <br/>class-description 销售回访信息
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class RepeatedViste extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 线索实体
	 */
	@FieldDescription("线索实体")
	private ContectInfo contectInfo;
	/**
	 * 回访人员
	 */
	@FieldDescription("回访人员")
	private User receiver;
	/**
	 * 线索外键
	 */
	@FieldDescription("线索外键")
	private Long contectInfoId;
	/**
	 * 回访人员外键
	 */
	@FieldDescription("回访人员外键")
	private Long receiverId;
	/**
	 * 回访时间
	 */
	@FieldDescription("回访时间")
	private Date startTime;
	/**
	 * 回访情况
	 */
	@FieldDescription("回访情况")
	private String memo;
	/**
	 * 回访提醒时间
	 */
	@FieldDescription("回访提醒时间")
	private Date remindTime;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 线索实体
	 */
	public ContectInfo getContectInfo(){
		return contectInfo;
	}
	public void setContectInfo(ContectInfo contectInfo){
		this.contectInfo = contectInfo;
	}
	/**
	 * 回访人员
	 */
	public User getReceiver(){
		return receiver;
	}
	public void setReceiver(User receiver){
		this.receiver = receiver;
	}
	/**
	 * 线索外键
	 */
	public Long getContectInfoId(){
		return contectInfoId;
	}
	public void setContectInfoId(Long contectInfoId){
		this.contectInfoId = contectInfoId;
	}
	/**
	 * 回访人员外键
	 */
	public Long getReceiverId(){
		return receiverId;
	}
	public void setReceiverId(Long receiverId){
		this.receiverId = receiverId;
	}
	/**
	 * 回访时间
	 */
	public Date getStartTime(){
		return startTime;
	}
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	/**
	 * 回访情况
	 */
	public String getMemo(){
		return memo;
	}
	public void setMemo(String memo){
		this.memo = memo;
	}
	/**
	 * 回访提醒时间
	 */
	public Date getRemindTime(){
		return remindTime;
	}
	public void setRemindTime(Date remindTime){
		this.remindTime = remindTime;
	}
}