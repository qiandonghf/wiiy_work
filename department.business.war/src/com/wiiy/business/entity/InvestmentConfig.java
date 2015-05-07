package com.wiiy.business.entity;

import java.io.Serializable;

import com.wiiy.business.entity.Investment;
import com.wiiy.business.entity.InvestmentContectInfo;
import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;

/**
 * <br/>class-description 项目线索关联表
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class InvestmentConfig extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 线索外键
	 */
	@FieldDescription("线索外键")
	private InvestmentContectInfo investmentContectInfo;
	/**
	 * 项目外键
	 */
	@FieldDescription("项目外键")
	private Investment investment;
	/**
	 * 线索外键ID
	 */
	@FieldDescription("线索外键ID")
	private Long investmentContectInfoId;
	/**
	 * 项目外键ID
	 */
	@FieldDescription("项目外键ID")
	private Long investmentId;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 线索外键
	 */
	public InvestmentContectInfo getInvestmentContectInfo(){
		return investmentContectInfo;
	}
	public void setInvestmentContectInfo(InvestmentContectInfo investmentContectInfo){
		this.investmentContectInfo = investmentContectInfo;
	}
	/**
	 * 项目外键
	 */
	public Investment getInvestment(){
		return investment;
	}
	public void setInvestment(Investment investment){
		this.investment = investment;
	}
	/**
	 * 线索外键ID
	 */
	public Long getInvestmentContectInfoId(){
		return investmentContectInfoId;
	}
	public void setInvestmentContectInfoId(Long investmentContectInfoId){
		this.investmentContectInfoId = investmentContectInfoId;
	}
	/**
	 * 项目外键ID
	 */
	public Long getInvestmentId(){
		return investmentId;
	}
	public void setInvestmentId(Long investmentId){
		this.investmentId = investmentId;
	}
}