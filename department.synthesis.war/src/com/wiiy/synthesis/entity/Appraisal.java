package com.wiiy.synthesis.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.synthesis.preferences.enums.AppraisalTypeEnums;

/**
 * <br/>class-description 考核管理
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class Appraisal extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 名称
	 */
	@FieldDescription("名称")
	private String name;
	/**
	 * 考核类型
	 */
	@FieldDescription("考核类型")
	private AppraisalTypeEnums appraisalType;
	/**
	 * 时间
	 */
	@FieldDescription("时间")
	private Date time;
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
	 * 名称
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 考核类型
	 */
	public AppraisalTypeEnums getAppraisalType(){
		return appraisalType;
	}
	public void setAppraisalType(AppraisalTypeEnums appraisalType){
		this.appraisalType = appraisalType;
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
	 * 备注
	 */
	public String getMemo(){
		return memo;
	}
	public void setMemo(String memo){
		this.memo = memo;
	}
}