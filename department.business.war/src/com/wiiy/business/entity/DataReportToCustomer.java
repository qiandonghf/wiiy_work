package com.wiiy.business.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.business.preferences.enums.CustomerReportStatusEnum;
import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;

/**
 * <br/>class-description 报表上报企业配置
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class DataReportToCustomer extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 报表
	 */
	@FieldDescription("报表")
	private DataReport report;
	/**
	 * 企业
	 */
	@FieldDescription("企业")
	private BusinessCustomer customer;
	/**
	 * 报表外键
	 */
	@FieldDescription("报表外键")
	private Long reportId;
	/**
	 * 企业外键
	 */
	@FieldDescription("企业外键")
	private Long customerId;
	/**
	 * 填写时间
	 */
	@FieldDescription("填写时间")
	private Date fillTime;
	/**
	 * 是否填写
	 */
	@FieldDescription("是否填写")
	private BooleanEnum finished;
	/**
	 * 上报状态
	 */
	@FieldDescription("上报状态")
	private CustomerReportStatusEnum status;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 报表
	 */
	public DataReport getReport(){
		return report;
	}
	public void setReport(DataReport report){
		this.report = report;
	}
	/**
	 * 企业
	 */
	public BusinessCustomer getCustomer(){
		return customer;
	}
	public void setCustomer(BusinessCustomer customer){
		this.customer = customer;
	}
	/**
	 * 报表外键
	 */
	public Long getReportId(){
		return reportId;
	}
	public void setReportId(Long reportId){
		this.reportId = reportId;
	}
	/**
	 * 企业外键
	 */
	public Long getCustomerId(){
		return customerId;
	}
	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}
	/**
	 * 填写时间
	 */
	public Date getFillTime(){
		return fillTime;
	}
	public void setFillTime(Date fillTime){
		this.fillTime = fillTime;
	}
	/**
	 * 是否填写
	 */
	public BooleanEnum getFinished(){
		return finished;
	}
	public void setFinished(BooleanEnum finished){
		this.finished = finished;
	}
	/**
	 * 上报状态
	 */
	public CustomerReportStatusEnum getStatus(){
		return status;
	}
	public void setStatus(CustomerReportStatusEnum status){
		this.status = status;
	}
}