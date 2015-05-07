package com.wiiy.estate.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.TreeEntity;
import com.wiiy.estate.preferences.enums.FixDifficultyEnum;
import com.wiiy.estate.preferences.enums.PropertyFixStatusEnum;
import com.wiiy.estate.preferences.enums.SatisficingEnum;

/**
 * <br/>class-description 维护费用
 * <br/>extends com.wiiy.commons.entity.TreeEntity
 */
public class FixFee extends TreeEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 物业报修外键
	 */
	@FieldDescription("物业报修外键")
	private Long propertyId;
	/**
	 * 状态
	 */
	@FieldDescription("状态")
	private PropertyFixStatusEnum status;
	/**
	 * 难度
	 */
	@FieldDescription("难度")
	private FixDifficultyEnum difficulty;
	/**
	 * 满意度
	 */
	@FieldDescription("满意度")
	private SatisficingEnum satisficing;
	/**
	 * 完工日期
	 */
	@FieldDescription("完工日期")
	private Date finishTime;
	/**
	 * 维修人员
	 */
	@FieldDescription("维修人员")
	private String maintainer;
	/**
	 * 维修人员所属单位
	 */
	@FieldDescription("维修人员所属单位")
	private String maintainerOrg;
	/**
	 * 人工费用
	 */
	@FieldDescription("人工费用")
	private Double laborCosts;
	/**
	 * 材料费用
	 */
	@FieldDescription("材料费用")
	private Double materialCosts;
	/**
	 * 维修结果
	 */
	@FieldDescription("维修结果")
	private String result;
	/**
	 * 整改意见
	 */
	@FieldDescription("整改意见")
	private String rectification;
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
	 * 物业报修外键
	 */
	public Long getPropertyId(){
		return propertyId;
	}
	public void setPropertyId(Long propertyId){
		this.propertyId = propertyId;
	}
	/**
	 * 状态
	 */
	public PropertyFixStatusEnum getStatus(){
		return status;
	}
	public void setStatus(PropertyFixStatusEnum status){
		this.status = status;
	}
	/**
	 * 难度
	 */
	public FixDifficultyEnum getDifficulty(){
		return difficulty;
	}
	public void setDifficulty(FixDifficultyEnum difficulty){
		this.difficulty = difficulty;
	}
	/**
	 * 满意度
	 */
	public SatisficingEnum getSatisficing(){
		return satisficing;
	}
	public void setSatisficing(SatisficingEnum satisficing){
		this.satisficing = satisficing;
	}
	/**
	 * 完工日期
	 */
	public Date getFinishTime(){
		return finishTime;
	}
	public void setFinishTime(Date finishTime){
		this.finishTime = finishTime;
	}
	/**
	 * 维修人员
	 */
	public String getMaintainer(){
		return maintainer;
	}
	public void setMaintainer(String maintainer){
		this.maintainer = maintainer;
	}
	/**
	 * 维修人员所属单位
	 */
	public String getMaintainerOrg(){
		return maintainerOrg;
	}
	public void setMaintainerOrg(String maintainerOrg){
		this.maintainerOrg = maintainerOrg;
	}
	/**
	 * 人工费用
	 */
	public Double getLaborCosts(){
		return laborCosts;
	}
	public void setLaborCosts(Double laborCosts){
		this.laborCosts = laborCosts;
	}
	/**
	 * 材料费用
	 */
	public Double getMaterialCosts(){
		return materialCosts;
	}
	public void setMaterialCosts(Double materialCosts){
		this.materialCosts = materialCosts;
	}
	/**
	 * 维修结果
	 */
	public String getResult(){
		return result;
	}
	public void setResult(String result){
		this.result = result;
	}
	/**
	 * 整改意见
	 */
	public String getRectification(){
		return rectification;
	}
	public void setRectification(String rectification){
		this.rectification = rectification;
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