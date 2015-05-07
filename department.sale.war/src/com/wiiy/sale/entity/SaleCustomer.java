package com.wiiy.sale.entity;

import java.io.Serializable;
import java.util.Set;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.GenderEnum;
import com.wiiy.core.entity.DataDict;
import com.wiiy.core.entity.User;
import com.wiiy.sale.entity.SaleCustomerLabelRef;
import com.wiiy.sale.preferences.enums.CustomerLevelEnum;
import com.wiiy.sale.preferences.enums.CustomerTypeEnum;
import com.wiiy.sale.preferences.enums.DemandEnum;
import com.wiiy.sale.preferences.enums.SatisfactionEnum;

/**
 * <br/>class-description 销售部客户
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class SaleCustomer extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 学历
	 */
	@FieldDescription("学历")
	private DataDict education;
	/**
	 * 职业
	 */
	@FieldDescription("职业")
	private DataDict profession;
	/**
	 * 家庭年收入
	 */
	@FieldDescription("家庭年收入")
	private DataDict familyIncome;
	/**
	 * 客户区域
	 */
	@FieldDescription("客户区域")
	private DataDict clientArea;
	/**
	 * 讯息来源
	 */
	@FieldDescription("讯息来源")
	private DataDict source;
	/**
	 * 户型需求
	 */
	@FieldDescription("户型需求")
	private DataDict huxing;
	/**
	 * 抗性分析
	 */
	@FieldDescription("抗性分析")
	private DataDict resistance;
	/**
	 * 购房动机
	 */
	@FieldDescription("购房动机")
	private DataDict motivation;
	/**
	 * 置业顾问
	 */
	@FieldDescription("置业顾问")
	private User user;
	/**
	 * 姓名
	 */
	@FieldDescription("姓名")
	private String name;
	/**
	 * 性别
	 */
	@FieldDescription("性别")
	private GenderEnum gender;
	/**
	 * 年龄
	 */
	@FieldDescription("年龄")
	private String age;
	/**
	 * 证件号
	 */
	@FieldDescription("证件号")
	private String idNO;
	/**
	 * Email
	 */
	@FieldDescription("Email")
	private String email;
	/**
	 * 电话
	 */
	@FieldDescription("电话")
	private String phone;
	/**
	 * 手机号码
	 */
	@FieldDescription("手机号码")
	private String mobile;
	/**
	 * 婚否
	 */
	@FieldDescription("婚否")
	private BooleanEnum marriage;
	/**
	 * 学历外键
	 */
	@FieldDescription("学历外键")
	private String educationId;
	/**
	 * 职业外键
	 */
	@FieldDescription("职业外键")
	private String professionId;
	/**
	 * 家庭年收入外键
	 */
	@FieldDescription("家庭年收入外键")
	private String familyIncomeId;
	/**
	 * 客户区域外键
	 */
	@FieldDescription("客户区域外键")
	private String clientAreaId;
	/**
	 * 讯息来源外键
	 */
	@FieldDescription("讯息来源外键")
	private String sourceId;
	/**
	 * 家庭住址
	 */
	@FieldDescription("家庭住址")
	private String address;
	/**
	 * 户型需求外键
	 */
	@FieldDescription("户型需求外键")
	private String huxingId;
	/**
	 * 抗性分析外键
	 */
	@FieldDescription("抗性分析外键")
	private String resistanceId;
	/**
	 * 地段
	 */
	@FieldDescription("地段")
	private SatisfactionEnum section;
	/**
	 * 户型
	 */
	@FieldDescription("户型")
	private SatisfactionEnum houseType;
	/**
	 * 价格
	 */
	@FieldDescription("价格")
	private SatisfactionEnum cost;
	/**
	 * 环境
	 */
	@FieldDescription("环境")
	private SatisfactionEnum environment;
	/**
	 * 需求级别
	 */
	@FieldDescription("需求级别")
	private DemandEnum demandLevel;
	/**
	 * 客户类型
	 */
	@FieldDescription("客户类型")
	private CustomerTypeEnum customerType;
	/**
	 * 客户等级
	 */
	@FieldDescription("客户等级")
	private CustomerLevelEnum level;
	/**
	 * 购房动机外键
	 */
	@FieldDescription("购房动机外键")
	private String motivationId;
	/**
	 * 可接受价格区间
	 */
	@FieldDescription("可接受价格区间")
	private String acceptPrice;
	/**
	 * 置业顾问外键
	 */
	@FieldDescription("置业顾问外键")
	private Long userId;
	/**
	 * 标签信息
	 */
	@FieldDescription("标签信息")
	private Set<SaleCustomerLabelRef> labelRefs;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 学历
	 */
	public DataDict getEducation(){
		return education;
	}
	public void setEducation(DataDict education){
		this.education = education;
	}
	/**
	 * 职业
	 */
	public DataDict getProfession(){
		return profession;
	}
	public void setProfession(DataDict profession){
		this.profession = profession;
	}
	/**
	 * 家庭年收入
	 */
	public DataDict getFamilyIncome(){
		return familyIncome;
	}
	public void setFamilyIncome(DataDict familyIncome){
		this.familyIncome = familyIncome;
	}
	/**
	 * 客户区域
	 */
	public DataDict getClientArea(){
		return clientArea;
	}
	public void setClientArea(DataDict clientArea){
		this.clientArea = clientArea;
	}
	/**
	 * 讯息来源
	 */
	public DataDict getSource(){
		return source;
	}
	public void setSource(DataDict source){
		this.source = source;
	}
	/**
	 * 户型需求
	 */
	public DataDict getHuxing(){
		return huxing;
	}
	public void setHuxing(DataDict huxing){
		this.huxing = huxing;
	}
	/**
	 * 抗性分析
	 */
	public DataDict getResistance(){
		return resistance;
	}
	public void setResistance(DataDict resistance){
		this.resistance = resistance;
	}
	/**
	 * 购房动机
	 */
	public DataDict getMotivation(){
		return motivation;
	}
	public void setMotivation(DataDict motivation){
		this.motivation = motivation;
	}
	/**
	 * 置业顾问
	 */
	public User getUser(){
		return user;
	}
	public void setUser(User user){
		this.user = user;
	}
	/**
	 * 姓名
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 性别
	 */
	public GenderEnum getGender(){
		return gender;
	}
	public void setGender(GenderEnum gender){
		this.gender = gender;
	}
	/**
	 * 年龄
	 */
	public String getAge(){
		return age;
	}
	public void setAge(String age){
		this.age = age;
	}
	/**
	 * 证件号
	 */
	public String getIdNO(){
		return idNO;
	}
	public void setIdNO(String idNO){
		this.idNO = idNO;
	}
	/**
	 * Email
	 */
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	/**
	 * 电话
	 */
	public String getPhone(){
		return phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	/**
	 * 手机号码
	 */
	public String getMobile(){
		return mobile;
	}
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	/**
	 * 婚否
	 */
	public BooleanEnum getMarriage(){
		return marriage;
	}
	public void setMarriage(BooleanEnum marriage){
		this.marriage = marriage;
	}
	/**
	 * 学历外键
	 */
	public String getEducationId(){
		return educationId;
	}
	public void setEducationId(String educationId){
		this.educationId = educationId;
	}
	/**
	 * 职业外键
	 */
	public String getProfessionId(){
		return professionId;
	}
	public void setProfessionId(String professionId){
		this.professionId = professionId;
	}
	/**
	 * 家庭年收入外键
	 */
	public String getFamilyIncomeId(){
		return familyIncomeId;
	}
	public void setFamilyIncomeId(String familyIncomeId){
		this.familyIncomeId = familyIncomeId;
	}
	/**
	 * 客户区域外键
	 */
	public String getClientAreaId(){
		return clientAreaId;
	}
	public void setClientAreaId(String clientAreaId){
		this.clientAreaId = clientAreaId;
	}
	/**
	 * 讯息来源外键
	 */
	public String getSourceId(){
		return sourceId;
	}
	public void setSourceId(String sourceId){
		this.sourceId = sourceId;
	}
	/**
	 * 家庭住址
	 */
	public String getAddress(){
		return address;
	}
	public void setAddress(String address){
		this.address = address;
	}
	/**
	 * 户型需求外键
	 */
	public String getHuxingId(){
		return huxingId;
	}
	public void setHuxingId(String huxingId){
		this.huxingId = huxingId;
	}
	/**
	 * 抗性分析外键
	 */
	public String getResistanceId(){
		return resistanceId;
	}
	public void setResistanceId(String resistanceId){
		this.resistanceId = resistanceId;
	}
	/**
	 * 地段
	 */
	public SatisfactionEnum getSection(){
		return section;
	}
	public void setSection(SatisfactionEnum section){
		this.section = section;
	}
	/**
	 * 户型
	 */
	public SatisfactionEnum getHouseType(){
		return houseType;
	}
	public void setHouseType(SatisfactionEnum houseType){
		this.houseType = houseType;
	}
	/**
	 * 价格
	 */
	public SatisfactionEnum getCost(){
		return cost;
	}
	public void setCost(SatisfactionEnum cost){
		this.cost = cost;
	}
	/**
	 * 环境
	 */
	public SatisfactionEnum getEnvironment(){
		return environment;
	}
	public void setEnvironment(SatisfactionEnum environment){
		this.environment = environment;
	}
	/**
	 * 需求级别
	 */
	public DemandEnum getDemandLevel(){
		return demandLevel;
	}
	public void setDemandLevel(DemandEnum demandLevel){
		this.demandLevel = demandLevel;
	}
	/**
	 * 客户类型
	 */
	public CustomerTypeEnum getCustomerType(){
		return customerType;
	}
	public void setCustomerType(CustomerTypeEnum customerType){
		this.customerType = customerType;
	}
	/**
	 * 客户等级
	 */
	public CustomerLevelEnum getLevel(){
		return level;
	}
	public void setLevel(CustomerLevelEnum level){
		this.level = level;
	}
	/**
	 * 购房动机外键
	 */
	public String getMotivationId(){
		return motivationId;
	}
	public void setMotivationId(String motivationId){
		this.motivationId = motivationId;
	}
	/**
	 * 可接受价格区间
	 */
	public String getAcceptPrice(){
		return acceptPrice;
	}
	public void setAcceptPrice(String acceptPrice){
		this.acceptPrice = acceptPrice;
	}
	/**
	 * 置业顾问外键
	 */
	public Long getUserId(){
		return userId;
	}
	public void setUserId(Long userId){
		this.userId = userId;
	}
	/**
	 * 标签信息
	 */
	public Set<SaleCustomerLabelRef> getLabelRefs(){
		return labelRefs;
	}
	public void setLabelRefs(Set<SaleCustomerLabelRef> labelRefs){
		this.labelRefs = labelRefs;
	}
}