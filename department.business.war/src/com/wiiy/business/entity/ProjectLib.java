package com.wiiy.business.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.business.preferences.enums.ProjectGainStatusEnum;
import com.wiiy.business.preferences.enums.ProjectStatusEnum;
import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.core.entity.DataDict;

/**
 * <br/>class-description 项目库
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class ProjectLib extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 所在城市
	 */
	@FieldDescription("所在城市")
	private DataDict city;
	/**
	 * 所属行业
	 */
	@FieldDescription("所属行业")
	private DataDict industry;
	/**
	 * 编号
	 */
	@FieldDescription("编号")
	private Long orderId;
	/**
	 * 项目来源
	 */
	@FieldDescription("项目来源")
	private String incubatorName;
	/**
	 * 公司ID
	 */
	@FieldDescription("公司ID")
	private Long customerId;
	/**
	 * 公司名称
	 */
	@FieldDescription("公司名称")
	private String customerName;
	/**
	 * 网址
	 */
	@FieldDescription("网址")
	private String homePage;
	/**
	 * 联系电话
	 */
	@FieldDescription("联系电话")
	private String phone;
	/**
	 * 邮编
	 */
	@FieldDescription("邮编")
	private String zipCode;
	/**
	 * 所在城市外键
	 */
	@FieldDescription("所在城市外键")
	private String cityId;
	/**
	 * 地址
	 */
	@FieldDescription("地址")
	private String address;
	/**
	 * LOGO
	 */
	@FieldDescription("LOGO")
	private String logo;
	/**
	 * 公司负责人{name:张三;position:总经理}
	 */
	@FieldDescription("公司负责人{name:张三;position:总经理}")
	private String leaders;
	/**
	 * 成立时间
	 */
	@FieldDescription("成立时间")
	private Date setupTime;
	/**
	 * 员工人数
	 */
	@FieldDescription("员工人数")
	private Integer employeeCnt;
	/**
	 * 公司简介
	 */
	@FieldDescription("公司简介")
	private String companyMemo;
	/**
	 * 项目名称
	 */
	@FieldDescription("项目名称")
	private String name;
	/**
	 * 融资额
	 */
	@FieldDescription("融资额")
	private Double amount;
	/**
	 * 融资期限
	 */
	@FieldDescription("融资期限")
	private Date endTime;
	/**
	 * 所属行业外键
	 */
	@FieldDescription("所属行业外键")
	private String industryId;
	/**
	 * 目前状态
	 */
	@FieldDescription("目前状态")
	private ProjectStatusEnum status;
	/**
	 * 营收情况
	 */
	@FieldDescription("营收情况")
	private ProjectGainStatusEnum gainStatus;
	/**
	 * 财务状况({year:2005;in:20000;out:10000;profit:10000})
	 */
	@FieldDescription("财务状况({year:2005;in:20000;out:10000;profit:10000})")
	private String gainMemo;
	/**
	 * 问题详情({business:公司业务;team:团队优势;demand:客户需求;market:目标市场及潜在市场;customer:潜在客户;strategy:市场战略;model:商业模式;power:公司竞争力;superiority:竞争优势})
	 */
	@FieldDescription("问题详情({business:公司业务;team:团队优势;demand:客户需求;market:目标市场及潜在市场;customer:潜在客户;strategy:市场战略;model:商业模式;power:公司竞争力;superiority:竞争优势})")
	private String questionMemo;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 所在城市
	 */
	public DataDict getCity(){
		return city;
	}
	public void setCity(DataDict city){
		this.city = city;
	}
	/**
	 * 所属行业
	 */
	public DataDict getIndustry(){
		return industry;
	}
	public void setIndustry(DataDict industry){
		this.industry = industry;
	}
	/**
	 * 编号
	 */
	public Long getOrderId(){
		return orderId;
	}
	public void setOrderId(Long orderId){
		this.orderId = orderId;
	}
	/**
	 * 项目来源
	 */
	public String getIncubatorName(){
		return incubatorName;
	}
	public void setIncubatorName(String incubatorName){
		this.incubatorName = incubatorName;
	}
	/**
	 * 公司ID
	 */
	public Long getCustomerId(){
		return customerId;
	}
	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}
	/**
	 * 公司名称
	 */
	public String getCustomerName(){
		return customerName;
	}
	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}
	/**
	 * 网址
	 */
	public String getHomePage(){
		return homePage;
	}
	public void setHomePage(String homePage){
		this.homePage = homePage;
	}
	/**
	 * 联系电话
	 */
	public String getPhone(){
		return phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	/**
	 * 邮编
	 */
	public String getZipCode(){
		return zipCode;
	}
	public void setZipCode(String zipCode){
		this.zipCode = zipCode;
	}
	/**
	 * 所在城市外键
	 */
	public String getCityId(){
		return cityId;
	}
	public void setCityId(String cityId){
		this.cityId = cityId;
	}
	/**
	 * 地址
	 */
	public String getAddress(){
		return address;
	}
	public void setAddress(String address){
		this.address = address;
	}
	/**
	 * LOGO
	 */
	public String getLogo(){
		return logo;
	}
	public void setLogo(String logo){
		this.logo = logo;
	}
	/**
	 * 公司负责人{name:张三;position:总经理}
	 */
	public String getLeaders(){
		return leaders;
	}
	public void setLeaders(String leaders){
		this.leaders = leaders;
	}
	/**
	 * 成立时间
	 */
	public Date getSetupTime(){
		return setupTime;
	}
	public void setSetupTime(Date setupTime){
		this.setupTime = setupTime;
	}
	/**
	 * 员工人数
	 */
	public Integer getEmployeeCnt(){
		return employeeCnt;
	}
	public void setEmployeeCnt(Integer employeeCnt){
		this.employeeCnt = employeeCnt;
	}
	/**
	 * 公司简介
	 */
	public String getCompanyMemo(){
		return companyMemo;
	}
	public void setCompanyMemo(String companyMemo){
		this.companyMemo = companyMemo;
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
	 * 融资额
	 */
	public Double getAmount(){
		return amount;
	}
	public void setAmount(Double amount){
		this.amount = amount;
	}
	/**
	 * 融资期限
	 */
	public Date getEndTime(){
		return endTime;
	}
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	/**
	 * 所属行业外键
	 */
	public String getIndustryId(){
		return industryId;
	}
	public void setIndustryId(String industryId){
		this.industryId = industryId;
	}
	/**
	 * 目前状态
	 */
	public ProjectStatusEnum getStatus(){
		return status;
	}
	public void setStatus(ProjectStatusEnum status){
		this.status = status;
	}
	/**
	 * 营收情况
	 */
	public ProjectGainStatusEnum getGainStatus(){
		return gainStatus;
	}
	public void setGainStatus(ProjectGainStatusEnum gainStatus){
		this.gainStatus = gainStatus;
	}
	/**
	 * 财务状况({year:2005;in:20000;out:10000;profit:10000})
	 */
	public String getGainMemo(){
		return gainMemo;
	}
	public void setGainMemo(String gainMemo){
		this.gainMemo = gainMemo;
	}
	/**
	 * 问题详情({business:公司业务;team:团队优势;demand:客户需求;market:目标市场及潜在市场;customer:潜在客户;strategy:市场战略;model:商业模式;power:公司竞争力;superiority:竞争优势})
	 */
	public String getQuestionMemo(){
		return questionMemo;
	}
	public void setQuestionMemo(String questionMemo){
		this.questionMemo = questionMemo;
	}
}