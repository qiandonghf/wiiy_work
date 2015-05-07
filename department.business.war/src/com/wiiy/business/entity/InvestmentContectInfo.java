package com.wiiy.business.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.business.preferences.enums.ContectInfoEnum;
import com.wiiy.business.preferences.enums.ContectTypeEnum;
import com.wiiy.business.preferences.enums.LevelEnum;
import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.core.entity.DataDict;
import com.wiiy.core.entity.User;

/**
 * <br/>class-description 交往信息
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class InvestmentContectInfo extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	
	
	/**
	 * 线索类型
	 */
	
	@FieldDescription("线索类型")
	private ContectTypeEnum contectType;
	

	/**
	 * 产业实体
	 */
	@FieldDescription("产业实体")
	private DataDict technic;
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
	 * 接待人员
	 */
	@FieldDescription("接待人员")
	private User receiver;
	/**
	 * 接待人员外键
	 */
	@FieldDescription("接待人员外键")
	private Long receiverId;
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
	 * 等级
	 */
	@FieldDescription("等级")
	private LevelEnum level;
	/**
	 * 状态
	 */
	@FieldDescription("状态")
	private ContectInfoEnum contectInfoStatus;
	/**
	 * 备注
	 */
	@FieldDescription("备注")
	private String memo;
	/**
	 * 产业外键
	 */
	@FieldDescription("产业外键")
	private String technicId;
	/**
	 * 是否关联项目
	 */
	@FieldDescription("是否关联项目")
	private BooleanEnum associate;
	

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 产业实体
	 */
	public DataDict getTechnic(){
		return technic;
	}
	public void setTechnic(DataDict technic){
		this.technic = technic;
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
	 * 接待人员
	 */
	public User getReceiver(){
		return receiver;
	}
	public void setReceiver(User receiver){
		this.receiver = receiver;
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
	 * 等级
	 */
	public LevelEnum getLevel(){
		return level;
	}
	public void setLevel(LevelEnum level){
		this.level = level;
	}
	/**
	 * 状态
	 */
	public ContectInfoEnum getContectInfoStatus(){
		return contectInfoStatus;
	}
	public void setContectInfoStatus(ContectInfoEnum contectInfoStatus){
		this.contectInfoStatus = contectInfoStatus;
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
	/**
	 * 产业外键
	 */
	public String getTechnicId(){
		return technicId;
	}
	public void setTechnicId(String technicId){
		this.technicId = technicId;
	}
	
	/*
	 * 线索类型
	 */
	public ContectTypeEnum getContectType() {
		return contectType;
	}
	public void setContectType(ContectTypeEnum contectType) {
		this.contectType = contectType;
	}
	/**
	 * 是否关联项目
	 */
	public BooleanEnum getAssociate() {
		return associate;
	}
	public void setAssociate(BooleanEnum associate) {
		this.associate = associate;
	}
}