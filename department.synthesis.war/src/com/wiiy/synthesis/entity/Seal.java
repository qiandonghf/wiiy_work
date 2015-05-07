package com.wiiy.synthesis.entity;

import java.io.Serializable;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.core.entity.DataDict;
import com.wiiy.core.entity.Org;

/**
 * <br/>class-description 印章
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class Seal extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 印章类别
	 */
	@FieldDescription("印章类别")
	private DataDict sealType;
	/**
	 * 所属部门
	 */
	@FieldDescription("所属部门")
	private Org org;
	/**
	 * 印章名称
	 */
	@FieldDescription("印章名称")
	private String name;
	/**
	 * 印章类别外键
	 */
	@FieldDescription("印章类别外键")
	private String sealTypeId;
	/**
	 * 部门外键
	 */
	@FieldDescription("部门外键")
	private Long orgId;
	/**
	 * 备注
	 */
	@FieldDescription("备注")
	private String memo;

	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 印章类别
	 */
	public DataDict getSealType(){
		return sealType;
	}
	public void setSealType(DataDict sealType){
		this.sealType = sealType;
	}
	/**
	 * 所属部门
	 */
	public Org getOrg(){
		return org;
	}
	public void setOrg(Org org){
		this.org = org;
	}
	/**
	 * 印章名称
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 印章类别外键
	 */
	public String getSealTypeId(){
		return sealTypeId;
	}
	public void setSealTypeId(String sealTypeId){
		this.sealTypeId = sealTypeId;
	}
	/**
	 * 部门外键
	 */
	public Long getOrgId(){
		return orgId;
	}
	public void setOrgId(Long orgId){
		this.orgId = orgId;
	}
}