package com.wiiy.synthesis.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.core.entity.DataDict;
import com.wiiy.core.entity.User;
import com.wiiy.common.preferences.enums.PaymentTypeEnum;
import com.wiiy.common.preferences.enums.ProjectPriorityEnum;
import com.wiiy.common.preferences.enums.ProjectStatusEnum;
import com.wiiy.common.preferences.enums.SettlementTypeEnum;

/**
 * <br/>class-description 工程部项目
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class SynthesisProject extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 负责人
	 */
	@FieldDescription("负责人")
	private User manager;
	/**
	 * 币种实体
	 */
	@FieldDescription("币种实体")
	private DataDict currencyType;
	/**
	 * 经手人
	 */
	@FieldDescription("经手人")
	private User handling;
	/**
	 * 编码
	 */
	@FieldDescription("编码")
	private String code;
	/**
	 * 项目名称
	 */
	@FieldDescription("项目名称")
	private String name;
	/**
	 * 甲方外键
	 */
	@FieldDescription("甲方外键")
	private Long supplierId;
	/**
	 * 甲方
	 */
	@FieldDescription("甲方")
	private String supplierName;
	/**
	 * 乙方外键
	 */
	@FieldDescription("乙方外键")
	private Long customerId;
	/**
	 * 乙方
	 */
	@FieldDescription("乙方")
	private String customerName;
	/**
	 * 项目简称
	 */
	@FieldDescription("项目简称")
	private String abbreviation;
	/**
	 * 负责人外键
	 */
	@FieldDescription("负责人外键")
	private Long managerId;
	/**
	 * 项目进度数
	 */
	@FieldDescription("项目进度数")
	private String schedules;
	/**
	 * 项目状态
	 */
	@FieldDescription("项目状态")
	private ProjectStatusEnum status;
	/**
	 * 重要性
	 */
	@FieldDescription("重要性")
	private ProjectPriorityEnum priority;
	/**
	 * 币种外键
	 */
	@FieldDescription("币种外键")
	private String currencyTypeId;
	/**
	 * 收付方式
	 */
	@FieldDescription("收付方式")
	private PaymentTypeEnum payment;
	/**
	 * 结算方式
	 */
	@FieldDescription("结算方式")
	private SettlementTypeEnum settlement;
	/**
	 * 金额
	 */
	@FieldDescription("金额")
	private Double amount;
	/**
	 * 预计花费
	 */
	@FieldDescription("预计花费")
	private Double expectedCost;
	/**
	 * 实际花费
	 */
	@FieldDescription("实际花费")
	private Double actualCost;
	/**
	 * 项目开始日期
	 */
	@FieldDescription("项目开始日期")
	private Date startDate;
	/**
	 * 项目结束日期
	 */
	@FieldDescription("项目结束日期")
	private Date endDate;
	/**
	 * 项目签订日期
	 */
	@FieldDescription("项目签订日期")
	private Date signDate;
	/**
	 * 是否已审核
	 */
	@FieldDescription("是否已审核")
	private BooleanEnum audit;
	/**
	 * 是否已完成
	 */
	@FieldDescription("是否已完成")
	private BooleanEnum finished;
	/**
	 * 是否公开
	 */
	@FieldDescription("是否公开")
	private BooleanEnum published;
	/**
	 * 经手人外键
	 */
	@FieldDescription("经手人外键")
	private Long handlingId;
	/**
	 * 项目简介
	 */
	@FieldDescription("项目简介")
	private String introduction;
	/**
	 * 备注信息
	 */
	@FieldDescription("备注信息")
	private String memo;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 负责人
	 */
	public User getManager(){
		return manager;
	}
	public void setManager(User manager){
		this.manager = manager;
	}
	/**
	 * 币种实体
	 */
	public DataDict getCurrencyType(){
		return currencyType;
	}
	public void setCurrencyType(DataDict currencyType){
		this.currencyType = currencyType;
	}
	/**
	 * 经手人
	 */
	public User getHandling(){
		return handling;
	}
	public void setHandling(User handling){
		this.handling = handling;
	}
	/**
	 * 编码
	 */
	public String getCode(){
		return code;
	}
	public void setCode(String code){
		this.code = code;
	}
	/**
	 * 项目名称
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 甲方外键
	 */
	public Long getSupplierId(){
		return supplierId;
	}
	public void setSupplierId(Long supplierId){
		this.supplierId = supplierId;
	}
	/**
	 * 甲方
	 */
	public String getSupplierName(){
		return supplierName;
	}
	public void setSupplierName(String supplierName){
		this.supplierName = supplierName;
	}
	/**
	 * 乙方外键
	 */
	public Long getCustomerId(){
		return customerId;
	}
	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}
	/**
	 * 乙方
	 */
	public String getCustomerName(){
		return customerName;
	}
	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}
	/**
	 * 项目简称
	 */
	public String getAbbreviation(){
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation){
		this.abbreviation = abbreviation;
	}
	/**
	 * 负责人外键
	 */
	public Long getManagerId(){
		return managerId;
	}
	public void setManagerId(Long managerId){
		this.managerId = managerId;
	}
	/**
	 * 项目进度数
	 */
	public String getSchedules(){
		return schedules;
	}
	public void setSchedules(String schedules){
		this.schedules = schedules;
	}
	/**
	 * 项目状态
	 */
	public ProjectStatusEnum getStatus(){
		return status;
	}
	public void setStatus(ProjectStatusEnum status){
		this.status = status;
	}
	/**
	 * 重要性
	 */
	public ProjectPriorityEnum getPriority(){
		return priority;
	}
	public void setPriority(ProjectPriorityEnum priority){
		this.priority = priority;
	}
	/**
	 * 币种外键
	 */
	public String getCurrencyTypeId(){
		return currencyTypeId;
	}
	public void setCurrencyTypeId(String currencyTypeId){
		this.currencyTypeId = currencyTypeId;
	}
	/**
	 * 收付方式
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
	 * 金额
	 */
	public Double getAmount(){
		return amount;
	}
	public void setAmount(Double amount){
		this.amount = amount;
	}
	/**
	 * 预计花费
	 */
	public Double getExpectedCost(){
		return expectedCost;
	}
	public void setExpectedCost(Double expectedCost){
		this.expectedCost = expectedCost;
	}
	/**
	 * 实际花费
	 */
	public Double getActualCost(){
		return actualCost;
	}
	public void setActualCost(Double actualCost){
		this.actualCost = actualCost;
	}
	/**
	 * 项目开始日期
	 */
	public Date getStartDate(){
		return startDate;
	}
	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}
	/**
	 * 项目结束日期
	 */
	public Date getEndDate(){
		return endDate;
	}
	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}
	/**
	 * 项目签订日期
	 */
	public Date getSignDate(){
		return signDate;
	}
	public void setSignDate(Date signDate){
		this.signDate = signDate;
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
	 * 是否已完成
	 */
	public BooleanEnum getFinished(){
		return finished;
	}
	public void setFinished(BooleanEnum finished){
		this.finished = finished;
	}
	/**
	 * 是否公开
	 */
	public BooleanEnum getPublished(){
		return published;
	}
	public void setPublished(BooleanEnum published){
		this.published = published;
	}
	/**
	 * 经手人外键
	 */
	public Long getHandlingId(){
		return handlingId;
	}
	public void setHandlingId(Long handlingId){
		this.handlingId = handlingId;
	}
	/**
	 * 项目简介
	 */
	public String getIntroduction(){
		return introduction;
	}
	public void setIntroduction(String introduction){
		this.introduction = introduction;
	}
	/**
	 * 备注信息
	 */
	public String getMemo(){
		return memo;
	}
	public void setMemo(String memo){
		this.memo = memo;
	}
}