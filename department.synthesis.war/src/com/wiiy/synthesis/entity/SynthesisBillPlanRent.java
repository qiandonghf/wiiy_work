package com.wiiy.synthesis.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.common.preferences.enums.ApprovalStatusEnum;
import com.wiiy.common.preferences.enums.BillPlanStatusEnum;
import com.wiiy.synthesis.entity.SynthesisContract;

/**
 * <br/>class-description 综合部合同资金计划
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class SynthesisBillPlanRent extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 合同
	 */
	@FieldDescription("合同")
	private SynthesisContract contract;
	/**
	 * 合同外键
	 */
	@FieldDescription("合同外键")
	private Long contractId;
	/**
	 * 编号
	 */
	@FieldDescription("编号")
	private String code;
	/**
	 * 单价
	 */
	@FieldDescription("单价")
	private String price;
	/**
	 * 数量
	 */
	@FieldDescription("数量")
	private Double amount;
	/**
	 * 计划金额
	 */
	@FieldDescription("计划金额")
	private Double planFee;
	/**
	 * 实际金额
	 */
	@FieldDescription("实际金额")
	private Double realFee;
	/**
	 * 计费开始时间
	 */
	@FieldDescription("计费开始时间")
	private Date startDate;
	/**
	 * 计费结束时间
	 */
	@FieldDescription("计费结束时间")
	private Date endDate;
	/**
	 * 计划付费时间
	 */
	@FieldDescription("计划付费时间")
	private Date planPayDate;
	/**
	 * 状态
	 */
	@FieldDescription("状态")
	private BillPlanStatusEnum status;
	/**
	 * 审批状态
	 */
	@FieldDescription("审批状态")
	private ApprovalStatusEnum approvalStatus;
	/**
	 * 是否已审核
	 */
	@FieldDescription("是否已审核")
	private BooleanEnum audit;
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
	 * 合同
	 */
	public SynthesisContract getContract(){
		return contract;
	}
	public void setContract(SynthesisContract contract){
		this.contract = contract;
	}
	/**
	 * 合同外键
	 */
	public Long getContractId(){
		return contractId;
	}
	public void setContractId(Long contractId){
		this.contractId = contractId;
	}
	/**
	 * 编号
	 */
	public String getCode(){
		return code;
	}
	public void setCode(String code){
		this.code = code;
	}
	/**
	 * 单价
	 */
	public String getPrice(){
		return price;
	}
	public void setPrice(String price){
		this.price = price;
	}
	/**
	 * 数量
	 */
	public Double getAmount(){
		return amount;
	}
	public void setAmount(Double amount){
		this.amount = amount;
	}
	/**
	 * 计划金额
	 */
	public Double getPlanFee(){
		return planFee;
	}
	public void setPlanFee(Double planFee){
		this.planFee = planFee;
	}
	/**
	 * 实际金额
	 */
	public Double getRealFee(){
		return realFee;
	}
	public void setRealFee(Double realFee){
		this.realFee = realFee;
	}
	/**
	 * 计费开始时间
	 */
	public Date getStartDate(){
		return startDate;
	}
	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}
	/**
	 * 计费结束时间
	 */
	public Date getEndDate(){
		return endDate;
	}
	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}
	/**
	 * 计划付费时间
	 */
	public Date getPlanPayDate(){
		return planPayDate;
	}
	public void setPlanPayDate(Date planPayDate){
		this.planPayDate = planPayDate;
	}
	/**
	 * 状态
	 */
	public BillPlanStatusEnum getStatus(){
		return status;
	}
	public void setStatus(BillPlanStatusEnum status){
		this.status = status;
	}
	/**
	 * 审批状态
	 */
	public ApprovalStatusEnum getApprovalStatus(){
		return approvalStatus;
	}
	public void setApprovalStatus(ApprovalStatusEnum approvalStatus){
		this.approvalStatus = approvalStatus;
	}
	/**
	 * 是否已审核
	 */
	public BooleanEnum getAudit(){
		return audit;
	}
	public void setAudit(BooleanEnum audit){
		this.audit = audit;
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