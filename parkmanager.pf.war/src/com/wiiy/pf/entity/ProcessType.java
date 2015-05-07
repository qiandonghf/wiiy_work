package com.wiiy.pf.entity;

import java.io.Serializable;

import org.activiti.engine.repository.ProcessDefinition;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.pf.preferences.enums.ProcessTypeEnum;

/**
 * <br/>class-description 流程类别
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class ProcessType extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 流程定义Id
	 */
	@FieldDescription("流程定义Id")
	private String processDefId;
	/**
	 * 流程版本
	 */
	@FieldDescription("流程版本")
	private String processVesion;
	/**
	 * 流程部署Id
	 */
	@FieldDescription("流程部署Id")
	private String deployId;
	/**
	 * 流程类型
	 */
	@FieldDescription("流程类型")
	private String type;
	/**
	 * 类型名称
	 */
	@FieldDescription("类型名称")
	private String typeName;
	/**
	 * 表单类型
	 */
	@FieldDescription("表单类型")
	private ProcessTypeEnum formName;
	
	
	private ProcessDefinition processDefinition;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 流程定义Id
	 */
	public String getProcessDefId(){
		return processDefId;
	}
	public void setProcessDefId(String processDefId){
		this.processDefId = processDefId;
	}
	/**
	 * 流程版本
	 */
	public String getProcessVesion() {
		return processVesion;
	}
	public void setProcessVesion(String processVesion) {
		this.processVesion = processVesion;
	}
	/**
	 * 流程部署Id
	 */
	public String getDeployId(){
		return deployId;
	}
	public void setDeployId(String deployId){
		this.deployId = deployId;
	}
	/**
	 * 流程类型
	 */
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type = type;
	}
	/**
	 * 类型名称
	 */
	public String getTypeName(){
		return typeName;
	}
	public void setTypeName(String typeName){
		this.typeName = typeName;
	}
	/**
	 * 表单类型
	 */
	public ProcessTypeEnum getFormName(){
		return formName;
	}
	public void setFormName(ProcessTypeEnum formName){
		this.formName = formName;
	}
	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}
	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}
}