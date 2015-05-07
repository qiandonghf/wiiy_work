package com.wiiy.pf.dto;

import java.util.List;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.core.entity.User;
import com.wiiy.hibernate.Result;
import com.wiiy.pf.entity.ContactBaseAtt;
import com.wiiy.pf.entity.ContactBaseLog;
import com.wiiy.pf.entity.ProcessType;

public class ContactBaseDto extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;//创建的用户
	private Task task;//任务
	private ProcessType processType;//流程与Form实体
	private HistoricProcessInstance hiProcessInstance;//历史流程实体
	
	private ProcessInstance processInstance;//流程实体
	
	private ProcessDefinition processDefinition;//流程定义
	private List<ContactBaseLog> logs;//轨迹
	@SuppressWarnings("rawtypes")
	private Result entity;//实体(请假单等)
	private List<TaskDefinition> taskDefinitions;//用户任务
	private List<UserTaskDto> userTaskInfos;//对应的用户信息
	/**
	 * 流程与对应的Form集合
	 */
	@FieldDescription("流程与对应的Form集合")
	private List<TypesDto> processTypes;
	/**
	 * ContactBaseDto集合(联系单实体类及对应的task等)
	 */
	@FieldDescription("ContactBaseDto集合")
	List<ContactBaseDto> dtos;
	
	/**
	 * ContactBaseAtt集合(附件)
	 */
	@FieldDescription("ContactBaseAtt集合")
	List<ContactBaseAtt> contactAttList;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public ProcessType getProcessType() {
		return processType;
	}
	public void setProcessType(ProcessType processType) {
		this.processType = processType;
	}
	public ProcessInstance getProcessInstance() {
		return processInstance;
	}
	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}
	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}
	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}
	@SuppressWarnings("rawtypes")
	public Result getEntity() {
		return entity;
	}
	@SuppressWarnings("rawtypes")
	public void setEntity(Result entity) {
		this.entity = entity;
	}
	public List<ContactBaseLog> getLogs() {
		return logs;
	}
	public void setLogs(List<ContactBaseLog> logs) {
		this.logs = logs;
	}
	public List<TaskDefinition> getTaskDefinitions() {
		return taskDefinitions;
	}
	public void setTaskDefinitions(List<TaskDefinition> taskDefinitions) {
		this.taskDefinitions = taskDefinitions;
	}
	public List<TypesDto> getProcessTypes() {
		return processTypes;
	}
	public void setProcessTypes(List<TypesDto> processTypes) {
		this.processTypes = processTypes;
	}
	public List<ContactBaseDto> getDtos() {
		return dtos;
	}
	public void setDtos(List<ContactBaseDto> dtos) {
		this.dtos = dtos;
	}

	public List<UserTaskDto> getUserTaskInfos() {
		return userTaskInfos;
	}
	public void setUserTaskInfos(List<UserTaskDto> userTaskInfos) {
		this.userTaskInfos = userTaskInfos;
	}
	public HistoricProcessInstance getHiProcessInstance() {
		return hiProcessInstance;
	}
	public void setHiProcessInstance(HistoricProcessInstance hiProcessInstance) {
		this.hiProcessInstance = hiProcessInstance;
	}
	public List<ContactBaseAtt> getContactAttList() {
		return contactAttList;
	}
	public void setContactAttList(List<ContactBaseAtt> contactAttList) {
		this.contactAttList = contactAttList;
	}
	
	
}
