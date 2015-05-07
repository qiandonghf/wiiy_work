package com.wiiy.pf.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.core.entity.DataDict;

public class InvestmentDto {
	private Long id;
	private String name;//企业名称
	/**
	 * 行业分类外键
	 */
	@FieldDescription("行业分类外键")
	private String technicId;
	/**
	 * 跟踪ID
	 */
	@FieldDescription("跟踪ID")
	private Long hostId;
	/**
	 * 跟踪
	 */
	@FieldDescription("跟踪")
	private String hostName;
	/**
	 * 引进ID
	 */
	@FieldDescription("引进ID")
	private Long importId;
	/**
	 * 引进
	 */
	@FieldDescription("引进")
	private String importName;
	/**
	 * 企业帐号
	 */
	@FieldDescription("企业帐号")
	private String account;
	
	//详细信息
	/**
	 * 实际办公地址
	 */
	@FieldDescription("实际办公地址")
	private String address;
	/**
	 * 注册类型实体
	 */
	@FieldDescription("注册类型实体")
	private DataDict regType;
	/**
	 * 币种实体
	 */
	@FieldDescription("币种实体")
	private DataDict currencyType;
	/**
	 * 证件类型实体
	 */
	@FieldDescription("证件类型实体")
	private DataDict documentType;
	/**
	 * 注册时间
	 */
	@FieldDescription("注册时间")
	private Date regTime;
	/**
	 * 注册类型外键
	 */
	@FieldDescription("注册类型外键")
	private String regTypeId;
	/**
	 * 注册资本
	 */
	@FieldDescription("注册资本")
	private BigDecimal regCapital;
	/**
	 * 币种外键
	 */
	@FieldDescription("币种外键")
	private String currencyTypeId;
	/**
	 * 组织机构代码
	 */
	@FieldDescription("组织机构代码")
	private String organizationNumber;
	/**
	 * 工商注册号
	 */
	@FieldDescription("工商注册号")
	private String businessNumber;
	/**
	 * 税务登记证
	 */
	@FieldDescription("税务登记证")
	private String taxNumber;
	/**
	 * 法定代表人
	 */
	@FieldDescription("法定代表人")
	private String legalPerson;
	/**
	 * 证件类型外键
	 */
	@FieldDescription("证件类型外键")
	private String documentTypeId;
	/**
	 * 证件号
	 */
	@FieldDescription("证件号")
	private String documentNumber;
	/**
	 * 注册EMAIL
	 */
	@FieldDescription("注册EMAIL")
	private String regMail;
	/**
	 * 移动电话
	 */
	@FieldDescription("移动电话")
	private String cellphone;
	/**
	 * 注册地址
	 */
	@FieldDescription("注册地址")
	private String regAddress;
	/**
	 * 经营范围
	 */
	@FieldDescription("经营范围")
	private String businessScope;
	/**
	 * 营业截至日期
	 */
	@FieldDescription("营业截至日期")
	private Date businessExpireDate;
	
	private String memo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTechnicId() {
		return technicId;
	}

	public void setTechnicId(String technicId) {
		this.technicId = technicId;
	}

	public Long getHostId() {
		return hostId;
	}

	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Long getImportId() {
		return importId;
	}

	public void setImportId(Long importId) {
		this.importId = importId;
	}

	public String getImportName() {
		return importName;
	}

	public void setImportName(String importName) {
		this.importName = importName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public DataDict getRegType() {
		return regType;
	}

	public void setRegType(DataDict regType) {
		this.regType = regType;
	}

	public DataDict getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(DataDict currencyType) {
		this.currencyType = currencyType;
	}

	public DataDict getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DataDict documentType) {
		this.documentType = documentType;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public String getRegTypeId() {
		return regTypeId;
	}

	public void setRegTypeId(String regTypeId) {
		this.regTypeId = regTypeId;
	}

	public BigDecimal getRegCapital() {
		return regCapital;
	}

	public void setRegCapital(BigDecimal regCapital) {
		this.regCapital = regCapital;
	}

	public String getCurrencyTypeId() {
		return currencyTypeId;
	}

	public void setCurrencyTypeId(String currencyTypeId) {
		this.currencyTypeId = currencyTypeId;
	}

	public String getOrganizationNumber() {
		return organizationNumber;
	}

	public void setOrganizationNumber(String organizationNumber) {
		this.organizationNumber = organizationNumber;
	}

	public String getBusinessNumber() {
		return businessNumber;
	}

	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(String documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getRegMail() {
		return regMail;
	}

	public void setRegMail(String regMail) {
		this.regMail = regMail;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getRegAddress() {
		return regAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public Date getBusinessExpireDate() {
		return businessExpireDate;
	}

	public void setBusinessExpireDate(Date businessExpireDate) {
		this.businessExpireDate = businessExpireDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
