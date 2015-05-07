package com.wiiy.business.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.business.entity.InvestmentContectInfo;
import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.core.entity.User;

/**
 * <br/>class-description 招商回访信息
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class InvestmentRepeatedViste extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 线索实体
	 */
	@FieldDescription("线索实体")
	private InvestmentContectInfo investmentContectInfo;
	/**
	 * 线索外键
	 */
	@FieldDescription("线索外键")
	private Long investmentContectInfoId;
	/**
	 * 姓名
	 */
	@FieldDescription("姓名")
	private String name;
	/**
	 * 联系电话
	 */
	@FieldDescription("联系电话")
	private String phone;
	/**
	 * 产业
	 */
	@FieldDescription("产业")
	private String product;
	/**
	 * 需求
	 */
	@FieldDescription("需求")
	private String demand;
	/**
	 * 回访人员
	 */
	@FieldDescription("回访人员")
	private User receiver;
	/**
	 * 回访人员外键
	 */
	@FieldDescription("回访人员外键")
	private Long receiverId;
	/**
	 * 回访开始时间
	 */
	@FieldDescription("回访开始时间")
	private Date startTime;
	/**
	 * 回访结束时间
	 */
	@FieldDescription("回访结束时间")
	private Date endTime;
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
	public InvestmentContectInfo getInvestmentContectInfo(){
		return investmentContectInfo;
	}
	public void setInvestmentContectInfo(InvestmentContectInfo investmentContectInfo){
		this.investmentContectInfo = investmentContectInfo;
	}
	/**
	 * 线索外键
	 */
	public Long getInvestmentContectInfoId(){
		return investmentContectInfoId;
	}
	public void setInvestmentContectInfoId(Long investmentContectInfoId){
		this.investmentContectInfoId = investmentContectInfoId;
	}
	/**
	 * 姓名
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
	public String getPhone(){
		return phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	/**
	 * 产业
	 */
	public String getProduct(){
		return product;
	}
	public void setProduct(String product){
		this.product = product;
	}
	/**
	 * 需求
	 */
	public String getDemand(){
		return demand;
	}
	public void setDemand(String demand){
		this.demand = demand;
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
	 * 回访人员外键
	 */
	public Long getReceiverId(){
		return receiverId;
	}
	public void setReceiverId(Long receiverId){
		this.receiverId = receiverId;
	}
	/**
	 * 回访开始时间
	 */
	public Date getStartTime(){
		return startTime;
	}
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	/**
	 * 回访结束时间
	 */
	public Date getEndTime(){
		return endTime;
	}
	public void setEndTime(Date endTime){
		this.endTime = endTime;
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