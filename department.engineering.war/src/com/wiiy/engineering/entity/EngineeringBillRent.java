package com.wiiy.engineering.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.common.preferences.enums.PaymentTypeEnum;
import com.wiiy.common.preferences.enums.SettlementNatureEnum;
import com.wiiy.common.preferences.enums.SettlementTypeEnum;
import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;

/**
 * <br/>class-description 工程部合同实际结算
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class EngineeringBillRent extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 合同
	 */
	@FieldDescription("合同")
	private EngineeringContract contract;
	/**
	 * 结算计划
	 */
	@FieldDescription("结算计划")
	private EngineeringBillPlanRent billPlanRent;
	/**
	 * 合同外键
	 */
	@FieldDescription("合同外键")
	private Long contractId;
	/**
	 * 结算计划外键
	 */
	@FieldDescription("结算计划外键")
	private Long billPlanRentId;
	/**
	 * 编号
	 */
	@FieldDescription("编号")
	private String code;
	/**
	 * 已结算总和
	 */
	@FieldDescription("已结算总和")
	private Double completeFee;
	/**
	 * 结算日期
	 */
	@FieldDescription("结算日期")
	private Date settlementDate;
	/**
	 * 金额
	 */
	@FieldDescription("金额")
	private Double settlementFee;
	/**
	 * 收付方向
	 */
	@FieldDescription("收付方向")
	private PaymentTypeEnum payment;
	/**
	 * 结算方式
	 */
	@FieldDescription("结算方式")
	private SettlementTypeEnum settlement;
	/**
	 * 结算性质
	 */
	@FieldDescription("结算性质")
	private SettlementNatureEnum settlementType;
	/**
	 * 是否已审核
	 */
	@FieldDescription("是否已审核")
	private BooleanEnum audit;
	/**
	 * 是否已付款
	 */
	@FieldDescription("是否已付款")
	private BooleanEnum paid;
	/**
	 * 备注
	 */
	@FieldDescription("备注")
	private String memo;
	/**
	 * 进度
	 */
	@FieldDescription("进度")
	private Double progress;
	
	public Double getProgress() {
		return progress;
	}
	public void setProgress(Double progress) {
		this.progress = progress;
	}

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 合同
	 */
	public EngineeringContract getContract(){
		return contract;
	}
	public void setContract(EngineeringContract contract){
		this.contract = contract;
	}
	/**
	 * 结算计划
	 */
	public EngineeringBillPlanRent getBillPlanRent(){
		return billPlanRent;
	}
	public void setBillPlanRent(EngineeringBillPlanRent billPlanRent){
		this.billPlanRent = billPlanRent;
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
	 * 结算计划外键
	 */
	public Long getBillPlanRentId(){
		return billPlanRentId;
	}
	public void setBillPlanRentId(Long billPlanRentId){
		this.billPlanRentId = billPlanRentId;
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
	 * 已结算总和
	 */
	public Double getCompleteFee(){
		return completeFee;
	}
	public void setCompleteFee(Double completeFee){
		this.completeFee = completeFee;
	}
	/**
	 * 结算日期
	 */
	public Date getSettlementDate(){
		return settlementDate;
	}
	public void setSettlementDate(Date settlementDate){
		this.settlementDate = settlementDate;
	}
	/**
	 * 金额
	 */
	public Double getSettlementFee(){
		return settlementFee;
	}
	public void setSettlementFee(Double settlementFee){
		this.settlementFee = settlementFee;
	}
	/**
	 * 收付方向
	 */
	public PaymentTypeEnum getPayment(){
		return payment;
	}
	public void setPayment(PaymentTypeEnum payment){
		this.payment = payment;
	}
	/**
	 * 结算方式
	 */
	public SettlementTypeEnum getSettlement(){
		return settlement;
	}
	public void setSettlement(SettlementTypeEnum settlement){
		this.settlement = settlement;
	}
	/**
	 * 结算性质
	 */
	public SettlementNatureEnum getSettlementType(){
		return settlementType;
	}
	public void setSettlementType(SettlementNatureEnum settlementType){
		this.settlementType = settlementType;
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
	 * 是否已付款
	 */
	public BooleanEnum getPaid(){
		return paid;
	}
	public void setPaid(BooleanEnum paid){
		this.paid = paid;
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