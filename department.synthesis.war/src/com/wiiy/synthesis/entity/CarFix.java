package com.wiiy.synthesis.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.TreeEntity;
import com.wiiy.core.entity.DataDict;
import com.wiiy.synthesis.entity.Car;

/**
 * <br/>class-description 车辆维修
 * <br/>extends com.wiiy.commons.entity.TreeEntity
 */
public class CarFix extends TreeEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 车辆
	 */
	@FieldDescription("车辆")
	private Car car;
	/**
	 * 车辆维修类型
	 */
	@FieldDescription("车辆维修类型")
	private DataDict carFixType;
	/**
	 * 车牌号码
	 */
	@FieldDescription("车牌号码")
	private String licenseNo;
	/**
	 * 车辆外键
	 */
	@FieldDescription("车辆外键")
	private Long carId;
	/**
	 * 维修日期
	 */
	@FieldDescription("维修日期")
	private Date fixDate;
	/**
	 * 经办人
	 */
	@FieldDescription("经办人")
	private String operator;
	/**
	 * 车辆维修类型外键
	 */
	@FieldDescription("车辆维修类型外键")
	private String carFixTypeId;
	/**
	 * 费用
	 */
	@FieldDescription("费用")
	private Double fee;
	/**
	 * 维修原因
	 */
	@FieldDescription("维修原因")
	private String reason;
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
	 * 车辆
	 */
	public Car getCar(){
		return car;
	}
	public void setCar(Car car){
		this.car = car;
	}
	/**
	 * 车辆维修类型
	 */
	public DataDict getCarFixType(){
		return carFixType;
	}
	public void setCarFixType(DataDict carFixType){
		this.carFixType = carFixType;
	}
	/**
	 * 车牌号码
	 */
	public String getLicenseNo(){
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo){
		this.licenseNo = licenseNo;
	}
	/**
	 * 车辆外键
	 */
	public Long getCarId(){
		return carId;
	}
	public void setCarId(Long carId){
		this.carId = carId;
	}
	/**
	 * 维修日期
	 */
	public Date getFixDate(){
		return fixDate;
	}
	public void setFixDate(Date fixDate){
		this.fixDate = fixDate;
	}
	/**
	 * 经办人
	 */
	public String getOperator(){
		return operator;
	}
	public void setOperator(String operator){
		this.operator = operator;
	}
	/**
	 * 车辆维修类型外键
	 */
	public String getCarFixTypeId(){
		return carFixTypeId;
	}
	public void setCarFixTypeId(String carFixTypeId){
		this.carFixTypeId = carFixTypeId;
	}
	/**
	 * 费用
	 */
	public Double getFee(){
		return fee;
	}
	public void setFee(Double fee){
		this.fee = fee;
	}
	/**
	 * 维修原因
	 */
	public String getReason(){
		return reason;
	}
	public void setReason(String reason){
		this.reason = reason;
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