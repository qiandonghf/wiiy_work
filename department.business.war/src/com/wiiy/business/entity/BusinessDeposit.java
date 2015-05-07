package com.wiiy.business.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.business.preferences.enums.DepositTypeEnum;
import com.wiiy.common.preferences.enums.BillPlanStatusEnum;
import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;

/**
 * <br/>class-description 押金
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class BusinessDeposit extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 合同
	 */
	@FieldDescription("合同")
	private BusinessContract contract;
	/**
	 * 企业
	 */
	@FieldDescription("企业")
	private BusinessCustomer customer;
	/**
	 * 合同外键
	 */
	@FieldDescription("合同外键")
	private Long contractId;
	/**
	 * 企业外键
	 */
	@FieldDescription("企业外键")
	private Long customerId;
	/**
	 * 保证金类型
	 */
	@FieldDescription("保证金类型")
	private DepositTypeEnum type;
	/**
	 * 计划付费时间
	 */
	@FieldDescription("计划付费时间")
	private Date planPayDate;
	/**
	 * 金额
	 */
	@FieldDescription("金额")
	private Double amount;
	/**
	 * 备注
	 */
	@FieldDescription("备注")
	private String memo;
	/**
	 * 状态
	 */
	@FieldDescription("状态")
	private BillPlanStatusEnum status;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 合同
	 */
	public BusinessContract getContract(){
		return contract;
	}
	public void setContract(BusinessContract contract){
		this.contract = contract;
	}
	/**
	 * 企业
	 */
	public BusinessCustomer getCustomer(){
		return customer;
	}
	public void setCustomer(BusinessCustomer customer){
		this.customer = customer;
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
	 * 企业外键
	 */
	public Long getCustomerId(){
		return customerId;
	}
	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}
	/**
	 * 保证金类型
	 */
	public DepositTypeEnum getType(){
		return type;
	}
	public void setType(DepositTypeEnum type){
		this.type = type;
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
	 * 金额
	 */
	public Double getAmount(){
		return amount;
	}
	public void setAmount(Double amount){
		this.amount = amount;
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
	 * 状态
	 */
	public BillPlanStatusEnum getStatus(){
		return status;
	}
	public void setStatus(BillPlanStatusEnum status){
		this.status = status;
	}
}