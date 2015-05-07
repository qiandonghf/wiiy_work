package com.wiiy.business.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.wiiy.business.entity.IncubationRoute;
import com.wiiy.commons.preferences.enums.BooleanEnum;

public class IncubateFormDto {
	private String name;//名称
	private BigDecimal incubatorAreas;//孵化面积
	private Date incubationStartDate;//孵化日期起
	private Date incubationEndDate;//孵化日期止
	private Date graduationDate;//毕业日期
	private BooleanEnum highTechEnterprise;//是否高新技术企业
	private BooleanEnum tutorSupport;//是否建立创业导师
	private BooleanEnum undergraduateEnterprise;//是否大学生创业
	private BooleanEnum overseaEnterprise;//是否留学生创业
	private String statusName;//孵化状态名称
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getIncubatorAreas() {
		return incubatorAreas;
	}
	public void setIncubatorAreas(BigDecimal incubatorAreas) {
		this.incubatorAreas = incubatorAreas;
	}
	public Date getIncubationStartDate() {
		return incubationStartDate;
	}
	public void setIncubationStartDate(Date incubationStartDate) {
		this.incubationStartDate = incubationStartDate;
	}
	public Date getIncubationEndDate() {
		return incubationEndDate;
	}
	public void setIncubationEndDate(Date incubationEndDate) {
		this.incubationEndDate = incubationEndDate;
	}
	public Date getGraduationDate() {
		return graduationDate;
	}
	public void setGraduationDate(Date graduationDate) {
		this.graduationDate = graduationDate;
	}
	public BooleanEnum getHighTechEnterprise() {
		return highTechEnterprise;
	}
	public void setHighTechEnterprise(BooleanEnum highTechEnterprise) {
		this.highTechEnterprise = highTechEnterprise;
	}
	public BooleanEnum getTutorSupport() {
		return tutorSupport;
	}
	public void setTutorSupport(BooleanEnum tutorSupport) {
		this.tutorSupport = tutorSupport;
	}
	public BooleanEnum getUndergraduateEnterprise() {
		return undergraduateEnterprise;
	}
	public void setUndergraduateEnterprise(BooleanEnum undergraduateEnterprise) {
		this.undergraduateEnterprise = undergraduateEnterprise;
	}
	public BooleanEnum getOverseaEnterprise() {
		return overseaEnterprise;
	}
	public void setOverseaEnterprise(BooleanEnum overseaEnterprise) {
		this.overseaEnterprise = overseaEnterprise;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}


}
