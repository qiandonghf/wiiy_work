package com.wiiy.park.entity;

import java.io.Serializable;

import com.wiiy.common.preferences.enums.DepartmentEnum;
import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;

/**
 * <br/>class-description 项目
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class Project extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 子项目外键
	 */
	@FieldDescription("子项目外键")
	private Long projectId;
	/**
	 * 所属部门
	 */
	@FieldDescription("所属部门")
	private DepartmentEnum department;
	/**
	 * 编号
	 */
	@FieldDescription("编号")
	private String code;
	/**
	 * 乙方外键
	 */
	@FieldDescription("乙方外键")
	private Long customerId;
	/**
	 * 乙方名称
	 */
	@FieldDescription("乙方名称")
	private String customerName;
	/**
	 * 项目名称
	 */
	@FieldDescription("项目名称")
	private String name;
	/**
	 * 甲方外键
	 */
	@FieldDescription("甲方外键")
	private Long supplierId;
	/**
	 * 甲方名称
	 */
	@FieldDescription("甲方名称")
	private String supplierName;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 子项目外键
	 */
	public Long getProjectId(){
		return projectId;
	}
	public void setProjectId(Long projectId){
		this.projectId = projectId;
	}
	/**
	 * 所属部门
	 */
	public DepartmentEnum getDepartment(){
		return department;
	}
	public void setDepartment(DepartmentEnum department){
		this.department = department;
	}
	/**
	 * 编号
	 */
	public String getCode(){
		return code;
	}
	public void setCode(String code){
		this.code = code;
	}
	/**
	 * 乙方外键
	 */
	public Long getCustomerId(){
		return customerId;
	}
	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}
	/**
	 * 乙方名称
	 */
	public String getCustomerName(){
		return customerName;
	}
	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}
	/**
	 * 项目名称
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 甲方外键
	 */
	public Long getSupplierId(){
		return supplierId;
	}
	public void setSupplierId(Long supplierId){
		this.supplierId = supplierId;
	}
	/**
	 * 甲方名称
	 */
	public String getSupplierName(){
		return supplierName;
	}
	public void setSupplierName(String supplierName){
		this.supplierName = supplierName;
	}
}