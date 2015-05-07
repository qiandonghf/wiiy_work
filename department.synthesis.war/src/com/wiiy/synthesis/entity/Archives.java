package com.wiiy.synthesis.entity;

import java.io.Serializable;

import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.GenderEnum;
import com.wiiy.core.entity.DataDict;
import com.wiiy.core.entity.User;
import com.wiiy.synthesis.preferences.enums.PositionConditionEnum;

/**
 * <br/>class-description 档案
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class Archives extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 用户
	 */
	@FieldDescription("用户")
	private User user;
	/**
	 * 政治面貌
	 */
	@FieldDescription("政治面貌")
	private DataDict political;
	/**
	 * 国籍
	 */
	@FieldDescription("国籍")
	private DataDict nationality;
	/**
	 * 民族
	 */
	@FieldDescription("民族")
	private DataDict ethnic;
	/**
	 * 员工姓名
	 */
	@FieldDescription("员工姓名")
	private String name;
	/**
	 * 身份证号
	 */
	@FieldDescription("身份证号")
	private String idNo;
	/**
	 * 用户外键
	 */
	@FieldDescription("用户外键")
	private Long userId;
	/**
	 * 出生日期
	 */
	@FieldDescription("出生日期")
	private Date birthDay;
	/**
	 * 政治面貌外键
	 */
	@FieldDescription("政治面貌外键")
	private String politicalId;
	/**
	 * 性别
	 */
	@FieldDescription("性别")
	private GenderEnum gender;
	/**
	 * 国籍外键
	 */
	@FieldDescription("国籍外键")
	private String nationalityId;
	/**
	 * 城镇户籍
	 */
	@FieldDescription("城镇户籍")
	private BooleanEnum towncensusRegister;
	/**
	 * 是否已婚
	 */
	@FieldDescription("是否已婚")
	private BooleanEnum marriage;
	/**
	 * 民族外键
	 */
	@FieldDescription("民族外键")
	private String ethnicId;
	/**
	 * 籍贯
	 */
	@FieldDescription("籍贯")
	private String homeTown;
	/**
	 * 状态
	 */
	@FieldDescription("状态")
	private PositionConditionEnum status;
	/**
	 * 职务
	 */
	@FieldDescription("职务")
	private String position;
	/**
	 * 家庭地址
	 */
	@FieldDescription("家庭地址")
	private String homeAddr;
	/**
	 * 电话号码
	 */
	@FieldDescription("电话号码")
	private String phone;
	/**
	 * 家庭邮编
	 */
	@FieldDescription("家庭邮编")
	private String zipCode;
	/**
	 * 学历
	 */
	@FieldDescription("学历")
	private String degree;
	/**
	 * 毕业学校
	 */
	@FieldDescription("毕业学校")
	private String school;
	/**
	 * 专业
	 */
	@FieldDescription("专业")
	private String profession;
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
	 * 用户
	 */
	public User getUser(){
		return user;
	}
	public void setUser(User user){
		this.user = user;
	}
	/**
	 * 政治面貌
	 */
	public DataDict getPolitical(){
		return political;
	}
	public void setPolitical(DataDict political){
		this.political = political;
	}
	/**
	 * 国籍
	 */
	public DataDict getNationality(){
		return nationality;
	}
	public void setNationality(DataDict nationality){
		this.nationality = nationality;
	}
	/**
	 * 民族
	 */
	public DataDict getEthnic(){
		return ethnic;
	}
	public void setEthnic(DataDict ethnic){
		this.ethnic = ethnic;
	}
	/**
	 * 员工姓名
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 身份证号
	 */
	public String getIdNo(){
		return idNo;
	}
	public void setIdNo(String idNo){
		this.idNo = idNo;
	}
	/**
	 * 用户外键
	 */
	public Long getUserId(){
		return userId;
	}
	public void setUserId(Long userId){
		this.userId = userId;
	}
	/**
	 * 出生日期
	 */
	public Date getBirthDay(){
		return birthDay;
	}
	public void setBirthDay(Date birthDay){
		this.birthDay = birthDay;
	}
	/**
	 * 政治面貌外键
	 */
	public String getPoliticalId(){
		return politicalId;
	}
	public void setPoliticalId(String politicalId){
		this.politicalId = politicalId;
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
	 * 国籍外键
	 */
	public String getNationalityId(){
		return nationalityId;
	}
	public void setNationalityId(String nationalityId){
		this.nationalityId = nationalityId;
	}
	/**
	 * 城镇户籍
	 */
	public BooleanEnum getTowncensusRegister(){
		return towncensusRegister;
	}
	public void setTowncensusRegister(BooleanEnum towncensusRegister){
		this.towncensusRegister = towncensusRegister;
	}
	/**
	 * 是否已婚
	 */
	public BooleanEnum getMarriage(){
		return marriage;
	}
	public void setMarriage(BooleanEnum marriage){
		this.marriage = marriage;
	}
	/**
	 * 民族外键
	 */
	public String getEthnicId(){
		return ethnicId;
	}
	public void setEthnicId(String ethnicId){
		this.ethnicId = ethnicId;
	}
	/**
	 * 籍贯
	 */
	public String getHomeTown(){
		return homeTown;
	}
	public void setHomeTown(String homeTown){
		this.homeTown = homeTown;
	}
	/**
	 * 状态
	 */
	public PositionConditionEnum getStatus(){
		return status;
	}
	public void setStatus(PositionConditionEnum status){
		this.status = status;
	}
	/**
	 * 职务
	 */
	public String getPosition(){
		return position;
	}
	public void setPosition(String position){
		this.position = position;
	}
	/**
	 * 家庭地址
	 */
	public String getHomeAddr(){
		return homeAddr;
	}
	public void setHomeAddr(String homeAddr){
		this.homeAddr = homeAddr;
	}
	/**
	 * 电话号码
	 */
	public String getPhone(){
		return phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	/**
	 * 家庭邮编
	 */
	public String getZipCode(){
		return zipCode;
	}
	public void setZipCode(String zipCode){
		this.zipCode = zipCode;
	}
	/**
	 * 学历
	 */
	public String getDegree(){
		return degree;
	}
	public void setDegree(String degree){
		this.degree = degree;
	}
	/**
	 * 毕业学校
	 */
	public String getSchool(){
		return school;
	}
	public void setSchool(String school){
		this.school = school;
	}
	/**
	 * 专业
	 */
	public String getProfession(){
		return profession;
	}
	public void setProfession(String profession){
		this.profession = profession;
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