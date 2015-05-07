package com.wiiy.estate.entity;

import java.io.Serializable;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;

/**
 * <br/>class-description 合同备忘
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class EstateContractMemo extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 合同
	 */
	@FieldDescription("合同")
	private EstateContract contract;
	/**
	 * 合同外键
	 */
	@FieldDescription("合同外键")
	private Long contractId;
	/**
	 * 备注内容
	 */
	@FieldDescription("备注内容")
	private String memo;

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
	 * 合同外键
	 */
	public Long getContractId(){
		return contractId;
	}
	public void setContractId(Long contractId){
		this.contractId = contractId;
	}
	/**
	 * 备注内容
	 */
	public String getMemo(){
		return memo;
	}
	public void setMemo(String memo){
		this.memo = memo;
	}
}