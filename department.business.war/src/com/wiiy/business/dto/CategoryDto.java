package com.wiiy.business.dto;

import java.util.Date;

import com.wiiy.business.preferences.enums.CustomerTypeEnum;
import com.wiiy.business.preferences.enums.ParkStatusEnum;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.core.entity.DataDict;

public class CategoryDto {
	private CustomerTypeEnum type;//分类
	
	private String name;//名称
	private String shortName;//简称
	private String number;//编号
	private ParkStatusEnum status;//入驻状态
	private String receiver;//引进人
	private Date createTime;//创建时间
	private DataDict productCategory;//产业类别
	private DataDict source ;//企业来源
	private BooleanEnum incubate;//是否孵化企业
	public CustomerTypeEnum getType() {
		return type;
	}
	public void setType(CustomerTypeEnum type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public ParkStatusEnum getStatus() {
		return status;
	}
	public void setStatus(ParkStatusEnum status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public BooleanEnum getIncubate() {
		return incubate;
	}
	public void setIncubate(BooleanEnum incubate) {
		this.incubate = incubate;
	}
	public DataDict getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(DataDict productCategory) {
		this.productCategory = productCategory;
	}
	public DataDict getSource() {
		return source;
	}
	public void setSource(DataDict source) {
		this.source = source;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
}
