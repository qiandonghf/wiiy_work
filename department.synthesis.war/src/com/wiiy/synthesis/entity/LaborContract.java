package com.wiiy.synthesis.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.core.entity.User;
import com.wiiy.synthesis.entity.LaborContractAtt;
import com.wiiy.synthesis.preferences.enums.LaborContractTypeEnum;

/**
 * <br/>class-description 劳动合同
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class LaborContract extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 用户
	 */
	@FieldDescription("用户")
	private User user;
	/**
	 * 合同开始时间
	 */
	@FieldDescription("合同开始时间")
	private Date startTime;
	/**
	 * 合同结束时间
	 */
	@FieldDescription("合同结束时间")
	private Date endTime;
	/**
	 * 合同类型
	 */
	@FieldDescription("合同类型")
	private LaborContractTypeEnum contractCharacter;
	/**
	 * 签订日期
	 */
	@FieldDescription("签订日期")
	private Date signingDate;
	/**
	 * 岗位名称
	 */
	@FieldDescription("岗位名称")
	private String position;
	/**
	 * 员工姓名
	 */
	@FieldDescription("员工姓名")
	private String name;
	/**
	 * 用户外键
	 */
	@FieldDescription("用户外键")
	private Long userId;
	private Set<LaborContractAtt> atts;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 用户
	 */
	public User getUser(){
		return user;
	}
	public void setUser(User user){
		this.user = user;
	}
	/**
	 * 合同开始时间
	 */
	public Date getStartTime(){
		return startTime;
	}
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	/**
	 * 合同结束时间
	 */
	public Date getEndTime(){
		return endTime;
	}
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	/**
	 * 合同类型
	 */
	public LaborContractTypeEnum getContractCharacter(){
		return contractCharacter;
	}
	public void setContractCharacter(LaborContractTypeEnum contractCharacter){
		this.contractCharacter = contractCharacter;
	}
	/**
	 * 签订日期
	 */
	public Date getSigningDate(){
		return signingDate;
	}
	public void setSigningDate(Date signingDate){
		this.signingDate = signingDate;
	}
	/**
	 * 岗位名称
	 */
	public String getPosition(){
		return position;
	}
	public void setPosition(String position){
		this.position = position;
	}
	/**
	 * 员工姓名
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 用户外键
	 */
	public Long getUserId(){
		return userId;
	}
	public void setUserId(Long userId){
		this.userId = userId;
	}
	public Set<LaborContractAtt> getAtts(){
		return atts;
	}
	public void setAtts(Set<LaborContractAtt> atts){
		this.atts = atts;
	}
}