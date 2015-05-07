package com.wiiy.synthesis.entity;

import java.io.Serializable;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.synthesis.entity.Seal;
import com.wiiy.synthesis.entity.SealRegistration;

/**
 * <br/>class-description 用印配置
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class SealConfig extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 印章
	 */
	@FieldDescription("印章")
	private Seal seal;
	/**
	 * 申请人
	 */
	@FieldDescription("申请人")
	private SealRegistration sealRegistration;
	/**
	 * 印章Id
	 */
	@FieldDescription("印章Id")
	private Long sealId;
	/**
	 * 申请人Id
	 */
	@FieldDescription("申请人Id")
	private Long sealRegistrationId;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 印章
	 */
	public Seal getSeal(){
		return seal;
	}
	public void setSeal(Seal seal){
		this.seal = seal;
	}
	/**
	 * 申请人
	 */
	public SealRegistration getSealRegistration(){
		return sealRegistration;
	}
	public void setSealRegistration(SealRegistration sealRegistration){
		this.sealRegistration = sealRegistration;
	}
	/**
	 * 印章Id
	 */
	public Long getSealId(){
		return sealId;
	}
	public void setSealId(Long sealId){
		this.sealId = sealId;
	}
	/**
	 * 申请人Id
	 */
	public Long getSealRegistrationId(){
		return sealRegistrationId;
	}
	public void setSealRegistrationId(Long sealRegistrationId){
		this.sealRegistrationId = sealRegistrationId;
	}
}