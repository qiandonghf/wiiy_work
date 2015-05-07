package com.wiiy.synthesis.entity;

import java.io.Serializable;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;

/**
 * <br/>class-description 考核附件
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class AppraisalAtt extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 考核Id
	 */
	@FieldDescription("考核Id")
	private Long appraisalId;
	/**
	 * 附件名称
	 */
	@FieldDescription("附件名称")
	private String name;
	/**
	 * 重命名名称
	 */
	@FieldDescription("重命名名称")
	private String newName;
	/**
	 * 附件大小
	 */
	@FieldDescription("附件大小")
	private Long size;
	/**
	 * 备注说明
	 */
	@FieldDescription("备注说明")
	private String memo;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 考核Id
	 */
	public Long getAppraisalId(){
		return appraisalId;
	}
	public void setAppraisalId(Long appraisalId){
		this.appraisalId = appraisalId;
	}
	/**
	 * 附件名称
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 重命名名称
	 */
	public String getNewName(){
		return newName;
	}
	public void setNewName(String newName){
		this.newName = newName;
	}
	/**
	 * 附件大小
	 */
	public Long getSize(){
		return size;
	}
	public void setSize(Long size){
		this.size = size;
	}
	/**
	 * 备注说明
	 */
	public String getMemo(){
		return memo;
	}
	public void setMemo(String memo){
		this.memo = memo;
	}
}