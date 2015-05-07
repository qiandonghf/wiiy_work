package com.wiiy.synthesis.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.TreeEntity;
import com.wiiy.synthesis.entity.Car;
import com.wiiy.synthesis.preferences.enums.CarApplyStatusEnum;

/**
 * <br/>class-description 车辆申请
 * <br/>extends com.wiiy.commons.entity.TreeEntity
 */
public class CarApply extends TreeEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 车牌号码
	 */
	@FieldDescription("车牌号码")
	private String licenseNo;
	/**
	 * 车辆外键
	 */
	@FieldDescription("车辆外键")
	private String carIds;
	/**
	 * 用车人
	 */
	@FieldDescription("用车人")
	private String usePersons;
	/**
	 * 申请时间
	 */
	@FieldDescription("申请时间")
	private Date applyDate;
	/**
	 * 原因
	 */
	@FieldDescription("原因")
	private String reason;
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
	 * 里程
	 */
	@FieldDescription("里程")
	private Double distance;
	/**
	 * 油耗
	 */
	@FieldDescription("油耗")
	private Double oil;
	/**
	 * 备注
	 */
	@FieldDescription("备注")
	private String memo;
	/**
	 * 审批状态
	 */
	@FieldDescription("审批状态")
	private CarApplyStatusEnum status;
	/**
	 * 审批人姓名
	 */
	@FieldDescription("审批人姓名")
	private String approver;
	/**
	 * 审批人ID
	 */
	@FieldDescription("审批人ID")
	private Long approverId;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
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
	public String getCarIds(){
		return carIds;
	}
	public void setCarIds(String carIds){
		this.carIds = carIds;
	}
	/**
	 * 用车人
	 */
	public String getUsePersons(){
		return usePersons;
	}
	public void setUsePersons(String usePersons){
		this.usePersons = usePersons;
	}
	/**
	 * 申请时间
	 */
	public Date getApplyDate(){
		return applyDate;
	}
	public void setApplyDate(Date applyDate){
		this.applyDate = applyDate;
	}
	/**
	 * 原因
	 */
	public String getReason(){
		return reason;
	}
	public void setReason(String reason){
		this.reason = reason;
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
	 * 里程
	 */
	public Double getDistance(){
		return distance;
	}
	public void setDistance(Double distance){
		this.distance = distance;
	}
	/**
	 * 油耗
	 */
	public Double getOil(){
		return oil;
	}
	public void setOil(Double oil){
		this.oil = oil;
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
	 * 审批状态
	 */
	public CarApplyStatusEnum getStatus(){
		return status;
	}
	public void setStatus(CarApplyStatusEnum status){
		this.status = status;
	}
	/**
	 * 审批人姓名
	 */
	public String getApprover(){
		return approver;
	}
	public void setApprover(String approver){
		this.approver = approver;
	}
	/**
	 * 审批人ID
	 */
	public Long getApproverId(){
		return approverId;
	}
	public void setApproverId(Long approverId){
		this.approverId = approverId;
	}
}