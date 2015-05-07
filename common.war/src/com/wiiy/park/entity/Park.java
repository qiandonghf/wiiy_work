package com.wiiy.park.entity;

import java.io.Serializable;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.TreeEntity;

/**
 * <br/>class-description 园区
 * <br/>extends com.wiiy.commons.entity.TreeEntity
 */
public class Park extends TreeEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 园区名称
	 */
	@FieldDescription("园区名称")
	private String name;
	/**
	 * 水费
	 */
	@FieldDescription("水费")
	private Double water;
	/**
	 * 电费
	 */
	@FieldDescription("电费")
	private Double electricity;
	/**
	 * 气费
	 */
	@FieldDescription("气费")
	private Double gas;
	/**
	 * 能源费
	 */
	@FieldDescription("能源费")
	private Double energyFee;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 园区名称
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 水费
	 */
	public Double getWater(){
		return water;
	}
	public void setWater(Double water){
		this.water = water;
	}
	/**
	 * 电费
	 */
	public Double getElectricity(){
		return electricity;
	}
	public void setElectricity(Double electricity){
		this.electricity = electricity;
	}
	/**
	 * 气费
	 */
	public Double getGas(){
		return gas;
	}
	public void setGas(Double gas){
		this.gas = gas;
	}
	/**
	 * 能源费
	 */
	public Double getEnergyFee(){
		return energyFee;
	}
	public void setEnergyFee(Double energyFee){
		this.energyFee = energyFee;
	}
}