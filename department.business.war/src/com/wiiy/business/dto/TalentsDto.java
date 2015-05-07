package com.wiiy.business.dto;

import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.GenderEnum;
import com.wiiy.core.entity.DataDict;

public class TalentsDto {
	private String customerName;//企业名称
	private String name;//姓名
	private GenderEnum gender;//性别
	private DataDict political;//政治面貌
	private DataDict position;//职位
	private DataDict degree;//学位
	private String education;//学历
	private String studySchool;//毕业院校
	private String abroadCountry;//留学国家
	private String cellPhone;//电话
	private String email;//邮件
	private BooleanEnum stockHolder;//是否股东
	private BooleanEnum manager;//是否总经理
	private BooleanEnum studyAbroad;//是否留学人员
	private BooleanEnum legal;//是否法人
	private BooleanEnum pub;//是否发布网站
	private String memo;//备注
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public GenderEnum getGender() {
		return gender;
	}
	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}
	public DataDict getPolitical() {
		return political;
	}
	public void setPolitical(DataDict political) {
		this.political = political;
	}
	public DataDict getPosition() {
		return position;
	}
	public void setPosition(DataDict position) {
		this.position = position;
	}
	public DataDict getDegree() {
		return degree;
	}
	public void setDegree(DataDict degree) {
		this.degree = degree;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getStudySchool() {
		return studySchool;
	}
	public void setStudySchool(String studySchool) {
		this.studySchool = studySchool;
	}
	public String getAbroadCountry() {
		return abroadCountry;
	}
	public void setAbroadCountry(String abroadCountry) {
		this.abroadCountry = abroadCountry;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public BooleanEnum getStockHolder() {
		return stockHolder;
	}
	public void setStockHolder(BooleanEnum stockHolder) {
		this.stockHolder = stockHolder;
	}
	public BooleanEnum getManager() {
		return manager;
	}
	public void setManager(BooleanEnum manager) {
		this.manager = manager;
	}
	public BooleanEnum getStudyAbroad() {
		return studyAbroad;
	}
	public void setStudyAbroad(BooleanEnum studyAbroad) {
		this.studyAbroad = studyAbroad;
	}
	public BooleanEnum getLegal() {
		return legal;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public void setLegal(BooleanEnum legal) {
		this.legal = legal;
	}
	public BooleanEnum getPub() {
		return pub;
	}
	public void setPub(BooleanEnum pub) {
		this.pub = pub;
	}
	

}
