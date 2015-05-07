package com.wiiy.synthesis.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.core.entity.User;
import com.wiiy.synthesis.preferences.enums.SealApplyEnum;

/**
 * <br/>class-description 用章登记
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class SealRegistration extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 使用人
	 */
	@FieldDescription("使用人")
	private User user;
	/**
	 * 印章名称
	 */
	@FieldDescription("印章名称")
	private String sealNames;
	/**
	 * 印章IDS
	 */
	@FieldDescription("印章IDS")
	private String sealIds;
	/**
	 * 使用人外键
	 */
	@FieldDescription("使用人外键")
	private Long userId;
	/**
	 * 时间
	 */
	@FieldDescription("时间")
	private Date time;
	/**
	 * 状态
	 */
	@FieldDescription("状态")
	private SealApplyEnum status;
	/**
	 * 备注
	 */
	@FieldDescription("备注")
	private String memo;

	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 使用人
	 */
	public User getUser(){
		return user;
	}
	public void setUser(User user){
		this.user = user;
	}
	/**
	 * 印章名称
	 */
	public String getSealNames(){
		return sealNames;
	}
	public void setSealNames(String sealNames){
		this.sealNames = sealNames;
	}
	/**
	 * 使用人外键
	 */
	public Long getUserId(){
		return userId;
	}
	public void setUserId(Long userId){
		this.userId = userId;
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
	public SealApplyEnum getStatus(){
		return status;
	}
	public void setStatus(SealApplyEnum status){
		this.status = status;
	}
	public String getSealIds() {
		return sealIds;
	}
	public void setSealIds(String sealIds) {
		this.sealIds = sealIds;
	}
}