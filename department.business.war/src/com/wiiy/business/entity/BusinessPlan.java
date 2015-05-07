package com.wiiy.business.entity;

import java.io.Serializable;
import java.util.Set;

import com.wiiy.business.entity.BusinessPlanAtt;
import com.wiiy.business.entity.Investment;
import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;

/**
 * <br/>class-description 商业计划书
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class BusinessPlan extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 招商项目
	 */
	@FieldDescription("招商项目")
	private Investment investment;
	/**
	 * 拟办企业概况
	 */
	@FieldDescription("拟办企业概况")
	private String companySummery;
	/**
	 * 创业团队概况
	 */
	@FieldDescription("创业团队概况")
	private String teamSummery;
	/**
	 * 项目技术可行性分析
	 */
	@FieldDescription("项目技术可行性分析")
	private String projectFeasibility;
	/**
	 * 产品市场可行性分析
	 */
	@FieldDescription("产品市场可行性分析")
	private String marketFeasibility;
	/**
	 * 经济效益及社会效益分析
	 */
	@FieldDescription("经济效益及社会效益分析")
	private String economicBenefits;
	/**
	 * 对创业中心的要求
	 */
	@FieldDescription("对创业中心的要求")
	private String requirements;
	private Set<BusinessPlanAtt> atts;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 招商项目
	 */
	public Investment getInvestment(){
		return investment;
	}
	public void setInvestment(Investment investment){
		this.investment = investment;
	}
	/**
	 * 拟办企业概况
	 */
	public String getCompanySummery(){
		return companySummery;
	}
	public void setCompanySummery(String companySummery){
		this.companySummery = companySummery;
	}
	/**
	 * 创业团队概况
	 */
	public String getTeamSummery(){
		return teamSummery;
	}
	public void setTeamSummery(String teamSummery){
		this.teamSummery = teamSummery;
	}
	/**
	 * 项目技术可行性分析
	 */
	public String getProjectFeasibility(){
		return projectFeasibility;
	}
	public void setProjectFeasibility(String projectFeasibility){
		this.projectFeasibility = projectFeasibility;
	}
	/**
	 * 产品市场可行性分析
	 */
	public String getMarketFeasibility(){
		return marketFeasibility;
	}
	public void setMarketFeasibility(String marketFeasibility){
		this.marketFeasibility = marketFeasibility;
	}
	/**
	 * 经济效益及社会效益分析
	 */
	public String getEconomicBenefits(){
		return economicBenefits;
	}
	public void setEconomicBenefits(String economicBenefits){
		this.economicBenefits = economicBenefits;
	}
	/**
	 * 对创业中心的要求
	 */
	public String getRequirements(){
		return requirements;
	}
	public void setRequirements(String requirements){
		this.requirements = requirements;
	}
	public Set<BusinessPlanAtt> getAtts(){
		return atts;
	}
	public void setAtts(Set<BusinessPlanAtt> atts){
		this.atts = atts;
	}
}