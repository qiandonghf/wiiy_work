package com.wiiy.estate.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.common.preferences.enums.MeterTypeEnum;
import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.estate.entity.Meter;
import com.wiiy.estate.entity.MeterLabelRecord;
import com.wiiy.estate.preferences.enums.MeterRecordStatusEnum;

/**
 * <br/>class-description 分户表抄表费用报表
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class MeterLabelReport extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 水电表
	 */
	@FieldDescription("水电表")
	private Meter meter;
	/**
	 * 分户表抄表记录
	 */
	@FieldDescription("分户表抄表记录")
	private MeterLabelRecord meterLabelRecord;
	/**
	 * 水电表id
	 */
	@FieldDescription("水电表id")
	private Long meterId;
	/**
	 * 分户表抄表外键
	 */
	@FieldDescription("分户表抄表外键")
	private Long labelId;
	/**
	 * 分户表抄表记录外键
	 */
	@FieldDescription("分户表抄表记录外键")
	private Long labelRecordId;
	/**
	 * 报表名
	 */
	@FieldDescription("报表名")
	private String name;
	/**
	 * 制表日期
	 */
	@FieldDescription("制表日期")
	private Date date;
	/**
	 * 抄表开始时间
	 */
	@FieldDescription("抄表开始时间")
	private Date startTime;
	/**
	 * 抄表结束时间
	 */
	@FieldDescription("抄表结束时间")
	private Date endTime;
	/**
	 * 报表类型
	 */
	@FieldDescription("报表类型")
	private MeterTypeEnum type;
	/**
	 * 抄表人
	 */
	@FieldDescription("抄表人")
	private String reader;
	/**
	 * 是否出账
	 */
	@FieldDescription("是否出账")
	private MeterRecordStatusEnum checkOut;
	/**
	 * 表编号
	 */
	@FieldDescription("表编号")
	private String meterOrderNo;
	/**
	 * 客户名称
	 */
	@FieldDescription("客户名称")
	private String customerName;
	/**
	 * 上期读数
	 */
	@FieldDescription("上期读数")
	private Double preReading;
	/**
	 * 本期读数
	 */
	@FieldDescription("本期读数")
	private Double curReading;
	/**
	 * 倍数
	 */
	@FieldDescription("倍数")
	private Integer meterFactor;
	/**
	 * 实用读数
	 */
	@FieldDescription("实用读数")
	private Double syAmount;
	/**
	 * 合计
	 */
	@FieldDescription("合计")
	private Double totalAmount;
	/**
	 * 单价
	 */
	@FieldDescription("单价")
	private Double price;
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
	 * 水电表
	 */
	public Meter getMeter(){
		return meter;
	}
	public void setMeter(Meter meter){
		this.meter = meter;
	}
	/**
	 * 分户表抄表记录
	 */
	public MeterLabelRecord getMeterLabelRecord(){
		return meterLabelRecord;
	}
	public void setMeterLabelRecord(MeterLabelRecord meterLabelRecord){
		this.meterLabelRecord = meterLabelRecord;
	}
	/**
	 * 水电表id
	 */
	public Long getMeterId(){
		return meterId;
	}
	public void setMeterId(Long meterId){
		this.meterId = meterId;
	}
	/**
	 * 分户表抄表外键
	 */
	public Long getLabelId(){
		return labelId;
	}
	public void setLabelId(Long labelId){
		this.labelId = labelId;
	}
	/**
	 * 分户表抄表记录外键
	 */
	public Long getLabelRecordId(){
		return labelRecordId;
	}
	public void setLabelRecordId(Long labelRecordId){
		this.labelRecordId = labelRecordId;
	}
	/**
	 * 报表名
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 制表日期
	 */
	public Date getDate(){
		return date;
	}
	public void setDate(Date date){
		this.date = date;
	}
	/**
	 * 抄表开始时间
	 */
	public Date getStartTime(){
		return startTime;
	}
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	/**
	 * 抄表结束时间
	 */
	public Date getEndTime(){
		return endTime;
	}
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	/**
	 * 报表类型
	 */
	public MeterTypeEnum getType(){
		return type;
	}
	public void setType(MeterTypeEnum type){
		this.type = type;
	}
	/**
	 * 抄表人
	 */
	public String getReader(){
		return reader;
	}
	public void setReader(String reader){
		this.reader = reader;
	}
	/**
	 * 是否出账
	 */
	public MeterRecordStatusEnum getCheckOut(){
		return checkOut;
	}
	public void setCheckOut(MeterRecordStatusEnum checkOut){
		this.checkOut = checkOut;
	}
	/**
	 * 表编号
	 */
	public String getMeterOrderNo(){
		return meterOrderNo;
	}
	public void setMeterOrderNo(String meterOrderNo){
		this.meterOrderNo = meterOrderNo;
	}
	/**
	 * 客户名称
	 */
	public String getCustomerName(){
		return customerName;
	}
	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}
	/**
	 * 上期读数
	 */
	public Double getPreReading(){
		return preReading;
	}
	public void setPreReading(Double preReading){
		this.preReading = preReading;
	}
	/**
	 * 本期读数
	 */
	public Double getCurReading(){
		return curReading;
	}
	public void setCurReading(Double curReading){
		this.curReading = curReading;
	}
	/**
	 * 倍数
	 */
	public Integer getMeterFactor(){
		return meterFactor;
	}
	public void setMeterFactor(Integer meterFactor){
		this.meterFactor = meterFactor;
	}
	/**
	 * 实用读数
	 */
	public Double getSyAmount(){
		return syAmount;
	}
	public void setSyAmount(Double syAmount){
		this.syAmount = syAmount;
	}
	/**
	 * 合计
	 */
	public Double getTotalAmount(){
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount){
		this.totalAmount = totalAmount;
	}
	/**
	 * 单价
	 */
	public Double getPrice(){
		return price;
	}
	public void setPrice(Double price){
		this.price = price;
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