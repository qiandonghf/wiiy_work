package com.wiiy.estate.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.TreeEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;

/**
 * <br/>class-description 停车费
 * <br/>extends com.wiiy.commons.entity.TreeEntity
 */
public class ParkingFee extends TreeEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 车牌ID
	 */
	@FieldDescription("车牌ID")
	private Long licenseNoId;
	/**
	 * 车牌
	 */
	@FieldDescription("车牌")
	private String licenseNo;
	/**
	 * 车位ID
	 */
	@FieldDescription("车位ID")
	private Long parkingManageId;
	/**
	 * 车位
	 */
	@FieldDescription("车位")
	private String parkingManage;
	/**
	 * 单位/个人
	 */
	@FieldDescription("单位/个人")
	private String name;
	/**
	 * 收费人
	 */
	@FieldDescription("收费人")
	private String toller;
	/**
	 * 金额
	 */
	@FieldDescription("金额")
	private Double money;
	/**
	 * 开始时间
	 */
	@FieldDescription("开始时间")
	private Date startDate;
	/**
	 * 结束时间
	 */
	@FieldDescription("结束时间")
	private Date endDate;
	/**
	 * 备注
	 */
	@FieldDescription("备注")
	private String memo;
	/**
	 * 是否关闭提醒
	 */
	@FieldDescription("是否关闭提醒")
	private BooleanEnum isRemind;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 车牌ID
	 */
	public Long getLicenseNoId(){
		return licenseNoId;
	}
	public void setLicenseNoId(Long licenseNoId){
		this.licenseNoId = licenseNoId;
	}
	/**
	 * 车牌
	 */
	public String getLicenseNo(){
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo){
		this.licenseNo = licenseNo;
	}
	/**
	 * 车位ID
	 */
	public Long getParkingManageId(){
		return parkingManageId;
	}
	public void setParkingManageId(Long parkingManageId){
		this.parkingManageId = parkingManageId;
	}
	/**
	 * 车位
	 */
	public String getParkingManage(){
		return parkingManage;
	}
	public void setParkingManage(String parkingManage){
		this.parkingManage = parkingManage;
	}
	/**
	 * 单位/个人
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 收费人
	 */
	public String getToller(){
		return toller;
	}
	public void setToller(String toller){
		this.toller = toller;
	}
	/**
	 * 金额
	 */
	public Double getMoney(){
		return money;
	}
	public void setMoney(Double money){
		this.money = money;
	}
	/**
	 * 开始时间
	 */
	public Date getStartDate(){
		return startDate;
	}
	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}
	/**
	 * 结束时间
	 */
	public Date getEndDate(){
		return endDate;
	}
	public void setEndDate(Date endDate){
		this.endDate = endDate;
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
	 * 是否关闭提醒
	 */
	public BooleanEnum getIsRemind(){
		return isRemind;
	}
	public void setIsRemind(BooleanEnum isRemind){
		this.isRemind = isRemind;
	}
}