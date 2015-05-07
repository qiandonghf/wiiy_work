package com.wiiy.business.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.business.entity.BusinessContract;
import com.wiiy.business.preferences.enums.ContractRentStatusEnum;
import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;

/**
 * <br/>class-description 租约
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class Bail extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 合同
	 */
	@FieldDescription("合同")
	private BusinessContract businessContract;
	/**
	 * 合同ID
	 */
	@FieldDescription("合同ID")
	private Long businessContractId;
	/**
	 * 单 位 名 称
	 */
	@FieldDescription("单 位 名 称")
	private String name;
	/**
	 * 租赁状态
	 */
	@FieldDescription("租赁状态")
	private ContractRentStatusEnum rentState;
	/**
	 * 退租/续租日期
	 */
	@FieldDescription("退租/续租日期")
	private Date time;
	/**
	 * 金额
	 */
	@FieldDescription("金额")
	private Double money;
	/**
	 * 面积
	 */
	@FieldDescription("面积")
	private Double area;
	/**
	 * 原因
	 */
	@FieldDescription("原因")
	private String memo;
	/**
	 * 是否入驻
	 */
	@FieldDescription("是否入驻")
	private BooleanEnum enterStatus;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 合同
	 */
	public BusinessContract getBusinessContract(){
		return businessContract;
	}
	public void setBusinessContract(BusinessContract businessContract){
		this.businessContract = businessContract;
	}
	/**
	 * 合同ID
	 */
	public Long getBusinessContractId(){
		return businessContractId;
	}
	public void setBusinessContractId(Long businessContractId){
		this.businessContractId = businessContractId;
	}
	/**
	 * 单 位 名 称
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 租赁状态
	 */
	public ContractRentStatusEnum getRentState(){
		return rentState;
	}
	public void setRentState(ContractRentStatusEnum rentState){
		this.rentState = rentState;
	}
	/**
	 * 退租/续租日期
	 */
	public Date getTime(){
		return time;
	}
	public void setTime(Date time){
		this.time = time;
	}
	/**
	 * 金额
	 */
	public Double getMoney(){
		return money;
	}
	public void setMoney(Double money){
		this.money = money;
	}
	/**
	 * 面积
	 */
	public Double getArea(){
		return area;
	}
	public void setArea(Double area){
		this.area = area;
	}
	/**
	 * 原因
	 */
	public String getMemo(){
		return memo;
	}
	public void setMemo(String memo){
		this.memo = memo;
	}
	/**
	 * 是否入驻
	 */
	public BooleanEnum getEnterStatus(){
		return enterStatus;
	}
	public void setEnterStatus(BooleanEnum enterStatus){
		this.enterStatus = enterStatus;
	}
}