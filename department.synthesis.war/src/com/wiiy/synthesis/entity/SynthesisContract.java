package com.wiiy.synthesis.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.core.entity.DataDict;
import com.wiiy.core.entity.User;
import com.wiiy.common.preferences.enums.ContractFormEnum;
import com.wiiy.common.preferences.enums.ContractStatusEnum;
import com.wiiy.common.preferences.enums.PaymentTypeEnum;
import com.wiiy.common.preferences.enums.SettlementTypeEnum;
import com.wiiy.synthesis.entity.SynthesisProject;

/**
 * <br/>class-description 综合部合同
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class SynthesisContract extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 项目
	 */
	@FieldDescription("项目")
	private SynthesisProject project;
	/**
	 * 联系人
	 */
	@FieldDescription("联系人")
	private User contact;
	/**
	 * 责任人
	 */
	@FieldDescription("责任人")
	private User duty;
	/**
	 * 经手人
	 */
	@FieldDescription("经手人")
	private User handling;
	/**
	 * 合同类别
	 */
	@FieldDescription("合同类别")
	private DataDict category;
	/**
	 * 货币类型
	 */
	@FieldDescription("货币类型")
	private DataDict currencyType;
	/**
	 * 编码
	 */
	@FieldDescription("编码")
	private String code;
	/**
	 * 合同名称
	 */
	@FieldDescription("合同名称")
	private String name;
	/**
	 * 项目外键
	 */
	@FieldDescription("项目外键")
	private Long projectId;
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
	 * 联系人外键
	 */
	@FieldDescription("联系人外键")
	private Long contactId;
	/**
	 * 责任人外键
	 */
	@FieldDescription("责任人外键")
	private Long dutyId;
	/**
	 * 经手人外键
	 */
	@FieldDescription("经手人外键")
	private Long handlingId;
	/**
	 * 合同类别外键
	 */
	@FieldDescription("合同类别外键")
	private String categoryId;
	/**
	 * 合同形式
	 */
	@FieldDescription("合同形式")
	private ContractFormEnum contractForm;
	/**
	 * 合同状态
	 */
	@FieldDescription("合同状态")
	private ContractStatusEnum contractStatus;
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
	 * 货币类型外键
	 */
	@FieldDescription("货币类型外键")
	private String currencyTypeId;
	/**
	 * 合同签约金额
	 */
	@FieldDescription("合同签约金额")
	private Double moneyTotal;
	/**
	 * 合同总额
	 */
	@FieldDescription("合同总额")
	private Double contractAmount;
	/**
	 * 预计花费
	 */
	@FieldDescription("预计花费")
	private Double predictCost;
	/**
	 * 实际花费
	 */
	@FieldDescription("实际花费")
	private Double actualCost;
	/**
	 * 签订日期
	 */
	@FieldDescription("签订日期")
	private Date signDate;
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
	 * 登记日期
	 */
	@FieldDescription("登记日期")
	private Date receiveDate;
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
	 * 合同简介
	 */
	@FieldDescription("合同简介")
	private String introduction;
	/**
	 * 备注
	 */
	@FieldDescription("备注")
	private String memo;
	/**
	 * 扩展1
	 */
	@FieldDescription("扩展1")
	private String ext1;
	/**
	 * 扩展2
	 */
	@FieldDescription("扩展2")
	private String ext2;
	/**
	 * 扩展3
	 */
	@FieldDescription("扩展3")
	private String ext3;
	/**
	 * 扩展4
	 */
	@FieldDescription("扩展4")
	private String ext4;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 项目
	 */
	public SynthesisProject getProject(){
		return project;
	}
	public void setProject(SynthesisProject project){
		this.project = project;
	}
	/**
	 * 联系人
	 */
	public User getContact(){
		return contact;
	}
	public void setContact(User contact){
		this.contact = contact;
	}
	/**
	 * 责任人
	 */
	public User getDuty(){
		return duty;
	}
	public void setDuty(User duty){
		this.duty = duty;
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
	 * 合同类别
	 */
	public DataDict getCategory(){
		return category;
	}
	public void setCategory(DataDict category){
		this.category = category;
	}
	/**
	 * 货币类型
	 */
	public DataDict getCurrencyType(){
		return currencyType;
	}
	public void setCurrencyType(DataDict currencyType){
		this.currencyType = currencyType;
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
	 * 合同名称
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 项目外键
	 */
	public Long getProjectId(){
		return projectId;
	}
	public void setProjectId(Long projectId){
		this.projectId = projectId;
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
	 * 联系人外键
	 */
	public Long getContactId(){
		return contactId;
	}
	public void setContactId(Long contactId){
		this.contactId = contactId;
	}
	/**
	 * 责任人外键
	 */
	public Long getDutyId(){
		return dutyId;
	}
	public void setDutyId(Long dutyId){
		this.dutyId = dutyId;
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
	 * 合同类别外键
	 */
	public String getCategoryId(){
		return categoryId;
	}
	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}
	/**
	 * 合同形式
	 */
	public ContractFormEnum getContractForm(){
		return contractForm;
	}
	public void setContractForm(ContractFormEnum contractForm){
		this.contractForm = contractForm;
	}
	/**
	 * 合同状态
	 */
	public ContractStatusEnum getContractStatus(){
		return contractStatus;
	}
	public void setContractStatus(ContractStatusEnum contractStatus){
		this.contractStatus = contractStatus;
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
	 * 货币类型外键
	 */
	public String getCurrencyTypeId(){
		return currencyTypeId;
	}
	public void setCurrencyTypeId(String currencyTypeId){
		this.currencyTypeId = currencyTypeId;
	}
	/**
	 * 合同签约金额
	 */
	public Double getMoneyTotal(){
		return moneyTotal;
	}
	public void setMoneyTotal(Double moneyTotal){
		this.moneyTotal = moneyTotal;
	}
	/**
	 * 合同总额
	 */
	public Double getContractAmount(){
		return contractAmount;
	}
	public void setContractAmount(Double contractAmount){
		this.contractAmount = contractAmount;
	}
	/**
	 * 预计花费
	 */
	public Double getPredictCost(){
		return predictCost;
	}
	public void setPredictCost(Double predictCost){
		this.predictCost = predictCost;
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
	 * 签订日期
	 */
	public Date getSignDate(){
		return signDate;
	}
	public void setSignDate(Date signDate){
		this.signDate = signDate;
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
	 * 登记日期
	 */
	public Date getReceiveDate(){
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate){
		this.receiveDate = receiveDate;
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
	 * 合同简介
	 */
	public String getIntroduction(){
		return introduction;
	}
	public void setIntroduction(String introduction){
		this.introduction = introduction;
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
	 * 扩展1
	 */
	public String getExt1(){
		return ext1;
	}
	public void setExt1(String ext1){
		this.ext1 = ext1;
	}
	/**
	 * 扩展2
	 */
	public String getExt2(){
		return ext2;
	}
	public void setExt2(String ext2){
		this.ext2 = ext2;
	}
	/**
	 * 扩展3
	 */
	public String getExt3(){
		return ext3;
	}
	public void setExt3(String ext3){
		this.ext3 = ext3;
	}
	/**
	 * 扩展4
	 */
	public String getExt4(){
		return ext4;
	}
	public void setExt4(String ext4){
		this.ext4 = ext4;
	}
}