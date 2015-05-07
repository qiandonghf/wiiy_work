package com.wiiy.estate.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.common.preferences.enums.BillPlanStatusEnum;
import com.wiiy.common.preferences.enums.EstateFeeEnum;
import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.park.entity.Room;

/**
 * <br/>class-description 物业合同资金计划
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class EstateBillPlanRent extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	public EstateBillPlanRent() {
		super();
	}
	public EstateBillPlanRent(Double realFee, Date planPayDate,
			BillPlanStatusEnum status) {
		super();
		this.realFee = realFee;
		this.planPayDate = planPayDate;
		this.status = status;
	}
	
	private Long id;
	/**
	 * 合同
	 */
	@FieldDescription("合同")
	private EstateContract contract;
	/**
	 * 房间
	 */
	@FieldDescription("房间")
	private Room room;
	/**
	 * 标的
	 */
	@FieldDescription("标的")
	private EstateSubjectRent subject;
	/**
	 * 合同外键
	 */
	@FieldDescription("合同外键")
	private Long contractId;
	/**
	 * 房间外键
	 */
	@FieldDescription("房间外键")
	private Long roomId;
	/**
	 * 地址描述 如XX楼XX房间(程序自动填充)
	 */
	@FieldDescription("地址描述 如XX楼XX房间(程序自动填充)")
	private String roomName;
	/**
	 * 单价
	 */
	@FieldDescription("单价")
	private String price;
	/**
	 * 数量
	 */
	@FieldDescription("数量")
	private Double amount;
	/**
	 * 费用类型
	 */
	@FieldDescription("费用类型")
	private EstateFeeEnum feeType;
	/**
	 * 计划金额
	 */
	@FieldDescription("计划金额")
	private Double planFee;
	/**
	 * 实际金额
	 */
	@FieldDescription("实际金额")
	private Double realFee;
	/**
	 * 计费开始时间
	 */
	@FieldDescription("计费开始时间")
	private Date startDate;
	/**
	 * 计费结束时间
	 */
	@FieldDescription("计费结束时间")
	private Date endDate;
	/**
	 * 计划付费时间
	 */
	@FieldDescription("计划付费时间")
	private Date planPayDate;
	/**
	 * 状态
	 */
	@FieldDescription("状态")
	private BillPlanStatusEnum status;
	/**
	 * 是否自动出账
	 */
	@FieldDescription("是否自动出账")
	private BooleanEnum autoCheck;
	/**
	 * 备注
	 */
	@FieldDescription("备注")
	private String memo;
	/**
	 * 标的外键
	 */
	@FieldDescription("标的外键")
	private Long subjectId;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 合同
	 */
	public EstateContract getContract(){
		return contract;
	}
	public void setContract(EstateContract contract){
		this.contract = contract;
	}
	/**
	 * 房间
	 */
	public Room getRoom(){
		return room;
	}
	public void setRoom(Room room){
		this.room = room;
	}
	/**
	 * 标的
	 */
	public EstateSubjectRent getSubject(){
		return subject;
	}
	public void setSubject(EstateSubjectRent subject){
		this.subject = subject;
	}
	/**
	 * 合同外键
	 */
	public Long getContractId(){
		return contractId;
	}
	public void setContractId(Long contractId){
		this.contractId = contractId;
	}
	/**
	 * 房间外键
	 */
	public Long getRoomId(){
		return roomId;
	}
	public void setRoomId(Long roomId){
		this.roomId = roomId;
	}
	/**
	 * 地址描述 如XX楼XX房间(程序自动填充)
	 */
	public String getRoomName(){
		return roomName;
	}
	public void setRoomName(String roomName){
		this.roomName = roomName;
	}
	/**
	 * 单价
	 */
	public String getPrice(){
		return price;
	}
	public void setPrice(String price){
		this.price = price;
	}
	/**
	 * 数量
	 */
	public Double getAmount(){
		return amount;
	}
	public void setAmount(Double amount){
		this.amount = amount;
	}
	/**
	 * 费用类型
	 */
	public EstateFeeEnum getFeeType(){
		return feeType;
	}
	public void setFeeType(EstateFeeEnum feeType){
		this.feeType = feeType;
	}
	/**
	 * 计划金额
	 */
	public Double getPlanFee(){
		return planFee;
	}
	public void setPlanFee(Double planFee){
		this.planFee = planFee;
	}
	/**
	 * 实际金额
	 */
	public Double getRealFee(){
		return realFee;
	}
	public void setRealFee(Double realFee){
		this.realFee = realFee;
	}
	/**
	 * 计费开始时间
	 */
	public Date getStartDate(){
		return startDate;
	}
	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}
	/**
	 * 计费结束时间
	 */
	public Date getEndDate(){
		return endDate;
	}
	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}
	/**
	 * 计划付费时间
	 */
	public Date getPlanPayDate(){
		return planPayDate;
	}
	public void setPlanPayDate(Date planPayDate){
		this.planPayDate = planPayDate;
	}
	/**
	 * 状态
	 */
	public BillPlanStatusEnum getStatus(){
		return status;
	}
	public void setStatus(BillPlanStatusEnum status){
		this.status = status;
	}
	/**
	 * 是否自动出账
	 */
	public BooleanEnum getAutoCheck(){
		return autoCheck;
	}
	public void setAutoCheck(BooleanEnum autoCheck){
		this.autoCheck = autoCheck;
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
	/**
	 * 标的外键
	 */
	public Long getSubjectId(){
		return subjectId;
	}
	public void setSubjectId(Long subjectId){
		this.subjectId = subjectId;
	}
}