package com.wiiy.business.dto;

import java.util.Date;
import java.util.List;

import com.wiiy.business.preferences.enums.ContractItemEnum;


public class ContractRentPrintDto {
	
	private ContractItemEnum item;
	
	private String customerName;
	private String contractParty;//甲方
	private String propertyName;//本物业名称
	private String propertyNo;//房屋产权证号
	private String roomType;
	private String contractItemList;
	private Double areaTotal;
	private String purpose;//租赁用途
	private String propertyUnit;//物管理单价
	private Double price;
	
	private String address;//座落
	private String years;//租期多少
	private String overallFloorage;//厂房建筑总面积
	private String payType;//租金支付方式
	private Double amount;//押金
	private String rentAmount;//租金额度
	
	private List<ContractBillPlanDto> rentList;
	private List<ContractBillPlanDto> energyList;
	private List<ContractBillPlanDto> depositList;//押金集合
	private Double annuity;//年费用
	private Date startDate;
	private Date endDate;
	private Date signDate;
	private String policy;//优惠政策
	private Double deposit;//押金,ningbo的押金就一条，替换时用这个。
	private Double rentTotal;//租金总额
	private Double energyTotal;//能源费总额
	private Double netTotal;//网络总额
	
	
	private String code;//合同编号
	private String contractDate;//房屋租赁期
	
	private Date decorateStartDate;//装修期
	private Date decorateEndDate;
	
	private Double rentPrice;//租金（每日每平方米）
	private String rentPriceUnit;//租金单价单位
	
	private Double realFeeZJ;//第一期租金
	private String realFeeZJ2;//大写金额
	
	private Double realFeeDJ;//定金
	private String realFeeDJ2;//大写金额
	
	private String customerAddress;//乙方联系地址
	private String customerContact;//联系人
	private String phone;//联系电话
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getContractDate() {
		return contractDate;
	}
	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}
	public Date getDecorateStartDate() {
		return decorateStartDate;
	}
	public void setDecorateStartDate(Date decorateStartDate) {
		this.decorateStartDate = decorateStartDate;
	}
	public Date getDecorateEndDate() {
		return decorateEndDate;
	}
	public void setDecorateEndDate(Date decorateEndDate) {
		this.decorateEndDate = decorateEndDate;
	}
	public Double getRentPrice() {
		return rentPrice;
	}
	public void setRentPrice(Double rentPrice) {
		this.rentPrice = rentPrice;
	}
	public Double getRealFeeZJ() {
		return realFeeZJ;
	}
	public void setRealFeeZJ(Double realFeeZJ) {
		this.realFeeZJ = realFeeZJ;
	}
	public String getRealFeeZJ2() {
		return realFeeZJ2;
	}
	public void setRealFeeZJ2(String realFeeZJ2) {
		this.realFeeZJ2 = realFeeZJ2;
	}
	public Double getRealFeeDJ() {
		return realFeeDJ;
	}
	public void setRealFeeDJ(Double realFeeDJ) {
		this.realFeeDJ = realFeeDJ;
	}
	public String getRealFeeDJ2() {
		return realFeeDJ2;
	}
	public void setRealFeeDJ2(String realFeeDJ2) {
		this.realFeeDJ2 = realFeeDJ2;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getCustomerContact() {
		return customerContact;
	}
	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public ContractItemEnum getItem() {
		return item;
	}
	public void setItem(ContractItemEnum item) {
		this.item = item;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Double getAreaTotal() {
		return areaTotal;
	}
	public void setAreaTotal(Double areaTotal) {
		this.areaTotal = areaTotal;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public List<ContractBillPlanDto> getRentList() {
		return rentList;
	}
	public void setRentList(List<ContractBillPlanDto> rentList) {
		this.rentList = rentList;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public List<ContractBillPlanDto> getDepositList() {
		return depositList;
	}
	public void setDepositList(List<ContractBillPlanDto> depositList) {
		this.depositList = depositList;
	}
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	public List<ContractBillPlanDto> getEnergyList() {
		return energyList;
	}
	public void setEnergyList(List<ContractBillPlanDto> energyList) {
		this.energyList = energyList;
	}
	public Double getDeposit() {
		return deposit;
	}
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}
	public String getContractItemList() {
		return contractItemList;
	}
	public void setContractItemList(String contractItemList) {
		this.contractItemList = contractItemList;
	}
	public Double getRentTotal() {
		return rentTotal;
	}
	public void setRentTotal(Double rentTotal) {
		this.rentTotal = rentTotal;
	}
	public Double getEnergyTotal() {
		return energyTotal;
	}
	public void setEnergyTotal(Double energyTotal) {
		this.energyTotal = energyTotal;
	}
	public Double getNetTotal() {
		return netTotal;
	}
	public void setNetTotal(Double netTotal) {
		this.netTotal = netTotal;
	}
	public String getContractParty() {
		return contractParty;
	}
	public void setContractParty(String contractParty) {
		this.contractParty = contractParty;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyNo() {
		return propertyNo;
	}
	public void setPropertyNo(String propertyNo) {
		this.propertyNo = propertyNo;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getPropertyUnit() {
		return propertyUnit;
	}
	public void setPropertyUnit(String propertyUnit) {
		this.propertyUnit = propertyUnit;
	}
	public Double getAnnuity() {
		return annuity;
	}
	public void setAnnuity(Double annuity) {
		this.annuity = annuity;
	}
	public String getOverallFloorage() {
		return overallFloorage;
	}
	public void setOverallFloorage(String overallFloorage) {
		this.overallFloorage = overallFloorage;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getRentAmount() {
		return rentAmount;
	}
	public void setRentAmount(String rentAmount) {
		this.rentAmount = rentAmount;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
	}
	public String getRentPriceUnit() {
		return rentPriceUnit;
	}
	public void setRentPriceUnit(String rentPriceUnit) {
		this.rentPriceUnit = rentPriceUnit;
	}
}
