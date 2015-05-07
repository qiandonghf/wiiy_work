package com.wiiy.estate.entity;

import java.io.Serializable;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.TreeEntity;
import com.wiiy.estate.entity.SupplyCategory;

/**
 * <br/>class-description 办公用品分类
 * <br/>extends com.wiiy.commons.entity.TreeEntity
 */
public class SupplyCategory extends TreeEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 办公用品实体
	 */
	@FieldDescription("办公用品实体")
	private SupplyCategory parent;
	/**
	 * 分类名称
	 */
	@FieldDescription("分类名称")
	private String name;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 办公用品实体
	 */
	public SupplyCategory getParent(){
		return parent;
	}
	public void setParent(SupplyCategory parent){
		this.parent = parent;
	}
	/**
	 * 分类名称
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
}