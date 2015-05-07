package com.wiiy.synthesis.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.TreeEntity;
import com.wiiy.core.entity.DataDict;
import com.wiiy.synthesis.preferences.enums.CarStatusEnum;

/**
 * <br/>class-description 车辆
 * <br/>extends com.wiiy.commons.entity.TreeEntity
 */
public class Car extends TreeEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 车辆类型
	 */
	@FieldDescription("车辆类型")
	private DataDict carType;
	/**
	 * 车牌号码
	 */
	@FieldDescription("车牌号码")
	private String licenseNo;
	/**
	 * 状态
	 */
	@FieldDescription("状态")
	private CarStatusEnum status;
	/**
	 * 车辆类型外键
	 */
	@FieldDescription("车辆类型外键")
	private String carTypeId;
	/**
	 * 厂家型号
	 */
	@FieldDescription("厂家型号")
	private String factoryModel;
	/**
	 * 发动机号
	 */
	@FieldDescription("发动机号")
	private String engineNumber;
	/**
	 * 保险日期
	 */
	@FieldDescription("保险日期")
	private Date insuranceDate;
	/**
	 * 年审日期
	 */
	@FieldDescription("年审日期")
	private Date annualDate;
	/**
	 * 购置日期
	 */
	@FieldDescription("购置日期")
	private Date buyDate;
	/**
	 * 驾驶员
	 */
	@FieldDescription("驾驶员")
	private String pilot;
	/**
	 * 照片
	 */
	@FieldDescription("照片")
	private String photo;
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
	 * 车辆类型
	 */
	public DataDict getCarType(){
		return carType;
	}
	public void setCarType(DataDict carType){
		this.carType = carType;
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
	 * 状态
	 */
	public CarStatusEnum getStatus(){
		return status;
	}
	public void setStatus(CarStatusEnum status){
		this.status = status;
	}
	/**
	 * 车辆类型外键
	 */
	public String getCarTypeId(){
		return carTypeId;
	}
	public void setCarTypeId(String carTypeId){
		this.carTypeId = carTypeId;
	}
	/**
	 * 厂家型号
	 */
	public String getFactoryModel(){
		return factoryModel;
	}
	public void setFactoryModel(String factoryModel){
		this.factoryModel = factoryModel;
	}
	/**
	 * 发动机号
	 */
	public String getEngineNumber(){
		return engineNumber;
	}
	public void setEngineNumber(String engineNumber){
		this.engineNumber = engineNumber;
	}
	/**
	 * 保险日期
	 */
	public Date getInsuranceDate(){
		return insuranceDate;
	}
	public void setInsuranceDate(Date insuranceDate){
		this.insuranceDate = insuranceDate;
	}
	/**
	 * 年审日期
	 */
	public Date getAnnualDate(){
		return annualDate;
	}
	public void setAnnualDate(Date annualDate){
		this.annualDate = annualDate;
	}
	/**
	 * 购置日期
	 */
	public Date getBuyDate(){
		return buyDate;
	}
	public void setBuyDate(Date buyDate){
		this.buyDate = buyDate;
	}
	/**
	 * 驾驶员
	 */
	public String getPilot(){
		return pilot;
	}
	public void setPilot(String pilot){
		this.pilot = pilot;
	}
	/**
	 * 照片
	 */
	public String getPhoto(){
		return photo;
	}
	public void setPhoto(String photo){
		this.photo = photo;
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