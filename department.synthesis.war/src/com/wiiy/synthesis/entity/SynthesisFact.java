package com.wiiy.synthesis.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.core.entity.User;
import com.wiiy.synthesis.entity.SynthesisPlan;
import com.wiiy.synthesis.entity.SynthesisProject;

/**
 * <br/>class-description 工程部项目实际进度
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class SynthesisFact extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 项目
	 */
	@FieldDescription("项目")
	private SynthesisProject project;
	/**
	 * 项目计划进度
	 */
	@FieldDescription("项目计划进度")
	private SynthesisPlan plan;
	/**
	 * 经手人
	 */
	@FieldDescription("经手人")
	private User handling;
	/**
	 * 项目外键
	 */
	@FieldDescription("项目外键")
	private Long projectId;
	/**
	 * 项目计划进度外键
	 */
	@FieldDescription("项目计划进度外键")
	private Long planId;
	/**
	 * 时间
	 */
	@FieldDescription("时间")
	private Date time;
	/**
	 * 实际进度
	 */
	@FieldDescription("实际进度")
	private String schedule;
	/**
	 * 经手人外键
	 */
	@FieldDescription("经手人外键")
	private Long handlingId;
	/**
	 * 备注
	 */
	@FieldDescription("备注")
	private String memo;
	/**
	 * 是否已审核
	 */
	@FieldDescription("是否已审核")
	private BooleanEnum audit;
	/**
	 * 是否已完成
	 */
	@FieldDescription("是否已完成")
	private BooleanEnum finished;
	/**
	 * 是否公开
	 */
	@FieldDescription("是否公开")
	private BooleanEnum published;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 项目
	 */
	public SynthesisProject getProject(){
		return project;
	}
	public void setProject(SynthesisProject project){
		this.project = project;
	}
	/**
	 * 项目计划进度
	 */
	public SynthesisPlan getPlan(){
		return plan;
	}
	public void setPlan(SynthesisPlan plan){
		this.plan = plan;
	}
	/**
	 * 经手人
	 */
	public User getHandling(){
		return handling;
	}
	public void setHandling(User handling){
		this.handling = handling;
	}
	/**
	 * 项目外键
	 */
	public Long getProjectId(){
		return projectId;
	}
	public void setProjectId(Long projectId){
		this.projectId = projectId;
	}
	/**
	 * 项目计划进度外键
	 */
	public Long getPlanId(){
		return planId;
	}
	public void setPlanId(Long planId){
		this.planId = planId;
	}
	/**
	 * 时间
	 */
	public Date getTime(){
		return time;
	}
	public void setTime(Date time){
		this.time = time;
	}
	/**
	 * 实际进度
	 */
	public String getSchedule(){
		return schedule;
	}
	public void setSchedule(String schedule){
		this.schedule = schedule;
	}
	/**
	 * 经手人外键
	 */
	public Long getHandlingId(){
		return handlingId;
	}
	public void setHandlingId(Long handlingId){
		this.handlingId = handlingId;
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
	 * 是否已审核
	 */
	public BooleanEnum getAudit(){
		return audit;
	}
	public void setAudit(BooleanEnum audit){
		this.audit = audit;
	}
	/**
	 * 是否已完成
	 */
	public BooleanEnum getFinished(){
		return finished;
	}
	public void setFinished(BooleanEnum finished){
		this.finished = finished;
	}
	/**
	 * 是否公开
	 */
	public BooleanEnum getPublished(){
		return published;
	}
	public void setPublished(BooleanEnum published){
		this.published = published;
	}
}