package com.wiiy.pf.entity;

import java.io.Serializable;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.pf.preferences.enums.ProcessTypeEnum;

/**
 * <br/>class-description 联系单轨迹
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class ContactBaseLog extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 联系单外键
	 */
	@FieldDescription("联系单外键")
	private Long contactId;
	/**
	 * 任务外键
	 */
	@FieldDescription("任务外键")
	private String taskId;
	/**
	 * 联系单类型
	 */
	@FieldDescription("联系单类型")
	private ProcessTypeEnum contactType;
	/**
	 * 内容
	 */
	@FieldDescription("内容")
	private String content;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 联系单外键
	 */
	public Long getContactId(){
		return contactId;
	}
	public void setContactId(Long contactId){
		this.contactId = contactId;
	}
	/**
	 * 任务外键
	 */
	public String getTaskId(){
		return taskId;
	}
	public void setTaskId(String taskId){
		this.taskId = taskId;
	}
	/**
	 * 联系单类型
	 */
	public ProcessTypeEnum getContactType(){
		return contactType;
	}
	public void setContactType(ProcessTypeEnum contactType){
		this.contactType = contactType;
	}
	/**
	 * 内容
	 */
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}
}