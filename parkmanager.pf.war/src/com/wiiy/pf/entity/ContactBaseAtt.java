package com.wiiy.pf.entity;

import java.io.Serializable;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;

/**
 * <br/>class-description 流程类别
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class ContactBaseAtt extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 申请单Id
	 */
	@FieldDescription("申请单Id")
	private Long contactId;
	/**
	 * 流程实体Id
	 */
	@FieldDescription("流程实体Id")
	private String processInstanceId;
	/**
	 * 原文件名
	 */
	@FieldDescription("原文件名")
	private String oldName;
	/**
	 * 文件类别
	 */
	@FieldDescription("文件类别")
	private String type;
	/**
	 * 新文件名
	 */
	@FieldDescription("新文件名")
	private String newName;
	/**
	 * 文件路径
	 */
	@FieldDescription("文件路径")
	private String path;
	
	/**
	 * 附件大小
	 */
	@FieldDescription("附件大小")
	private Long size;


	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 申请单Id
	 */
	public Long getContactId(){
		return contactId;
	}
	public void setContactId(Long contactId){
		this.contactId = contactId;
	}
	/**
	 * 流程实体Id
	 */
	public String getProcessInstanceId(){
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId){
		this.processInstanceId = processInstanceId;
	}
	/**
	 * 原文件名
	 */
	public String getOldName(){
		return oldName;
	}
	public void setOldName(String oldName){
		this.oldName = oldName;
	}
	/**
	 * 文件类别
	 */
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type = type;
	}
	/**
	 * 新文件名
	 */
	public String getNewName(){
		return newName;
	}
	public void setNewName(String newName){
		this.newName = newName;
	}
	/**
	 * 文件路径
	 */
	public String getPath(){
		return path;
	}
	public void setPath(String path){
		this.path = path;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	
}