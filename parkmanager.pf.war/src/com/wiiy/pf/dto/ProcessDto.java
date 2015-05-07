package com.wiiy.pf.dto;

import java.util.Date;

import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.pf.entity.Leave;
import com.wiiy.pf.entity.ProcessType;
import com.wiiy.pf.preferences.enums.SuspensionStateEnum;

public class ProcessDto {
	private String id;
	private String executeId;//执行Id
	private String deploymentId;//部署ID
	private String instanceId;//实例Id
	private String definitionId;//定义Id
	private String currentNodeName;
	private ProcessType processType;
	private Leave leave;
	private String name;
	private String key;
	private Integer version;
	private String resourceName;
	private String diagramResourceName;
	private Date deploymentTime;
	private SuspensionStateEnum state;
	private BooleanEnum isEnd;//是否结束
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getDiagramResourceName() {
		return diagramResourceName;
	}
	public void setDiagramResourceName(String diagramResourceName) {
		this.diagramResourceName = diagramResourceName;
	}
	public Date getDeploymentTime() {
		return deploymentTime;
	}
	public void setDeploymentTime(Date deploymentTime) {
		this.deploymentTime = deploymentTime;
	}
	public SuspensionStateEnum getState() {
		return state;
	}
	public void setState(SuspensionStateEnum state) {
		this.state = state;
	}
	public String getExecuteId() {
		return executeId;
	}
	public void setExecuteId(String executeId) {
		this.executeId = executeId;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getDefinitionId() {
		return definitionId;
	}
	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}
	public String getCurrentNodeName() {
		return currentNodeName;
	}
	public void setCurrentNodeName(String currentNodeName) {
		this.currentNodeName = currentNodeName;
	}
	public BooleanEnum getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(BooleanEnum isEnd) {
		this.isEnd = isEnd;
	}
	public ProcessType getProcessType() {
		return processType;
	}
	public void setProcessType(ProcessType processType) {
		this.processType = processType;
	}
	public Leave getLeave() {
		return leave;
	}
	public void setLeave(Leave leave) {
		this.leave = leave;
	}
	
	
}
