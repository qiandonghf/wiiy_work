package com.wiiy.business.entity;

import java.io.Serializable;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;

/**
 * <br/>class-description 资金计划增长率
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class Rate extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 增长年
	 */
	@FieldDescription("增长年")
	private Integer addYear;
	/**
	 * 增长率
	 */
	@FieldDescription("增长率")
	private Double addRate;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 增长年
	 */
	public Integer getAddYear(){
		return addYear;
	}
	public void setAddYear(Integer addYear){
		this.addYear = addYear;
	}
	/**
	 * 增长率
	 */
	public Double getAddRate(){
		return addRate;
	}
	public void setAddRate(Double addRate){
		this.addRate = addRate;
	}
}