package com.wiiy.business.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.TreeEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.business.preferences.enums.DataTypeEnum;

/**
 * <br/>class-description 数据项(数据上报)
 * <br/>extends com.wiiy.commons.entity.TreeEntity
 */
public class DataProperty extends TreeEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	
	public String getParent() {
		return getParentId() != null ? getParentId()+"" : "";
	}
	public String getIsLeaf() {
		return getLeaf() != null ? getLeaf()+"" : "false";
	}
	
	private Long id;
	/**
	 * 上级
	 */
	@FieldDescription("上级")
	private DataProperty parentProperty;
	/**
	 * 叶子节点
	 */
	@FieldDescription("叶子节点")
	private Boolean leaf;
	/**
	 * 名称
	 */
	@FieldDescription("名称")
	private String name;
	/**
	 * 数据类型
	 */
	@FieldDescription("数据类型")
	private DataTypeEnum dataType;
	/**
	 * 数据类型扩展(JSON)
	 */
	@FieldDescription("数据类型扩展(JSON)")
	private String dataTypeExt;
	/**
	 * 单位
	 */
	@FieldDescription("单位")
	private String unit;
	/**
	 * 填报说明
	 */
	@FieldDescription("填报说明")
	private String note;
	/**
	 * 是否系统预设,不能删除系统预设的数据项
	 */
	@FieldDescription("是否系统预设,不能删除系统预设的数据项")
	private BooleanEnum fixed;
	/**
	 * 实体状态
	 */
	@FieldDescription("实体状态")
	private EntityStatus entityStatus;
	/**
	 * 创建时间
	 */
	@FieldDescription("创建时间")
	private Date createTime;
	/**
	 * 创建人姓名
	 */
	@FieldDescription("创建人姓名")
	private String creator;
	/**
	 * 创建人ID
	 */
	@FieldDescription("创建人ID")
	private Long creatorId;
	/**
	 * 修改时间
	 */
	@FieldDescription("修改时间")
	private Date modifyTime;
	/**
	 * 修改人姓名
	 */
	@FieldDescription("修改人姓名")
	private String modifier;
	/**
	 * 修改人ID
	 */
	@FieldDescription("修改人ID")
	private Long modifierId;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 上级
	 */
	public DataProperty getParentProperty(){
		return parentProperty;
	}
	public void setParentProperty(DataProperty parentProperty){
		this.parentProperty = parentProperty;
	}
	/**
	 * 叶子节点
	 */
	public Boolean getLeaf(){
		return leaf;
	}
	public void setLeaf(Boolean leaf){
		this.leaf = leaf;
	}
	/**
	 * 名称
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 数据类型
	 */
	public DataTypeEnum getDataType(){
		return dataType;
	}
	public void setDataType(DataTypeEnum dataType){
		this.dataType = dataType;
	}
	/**
	 * 数据类型扩展(JSON)
	 */
	public String getDataTypeExt(){
		return dataTypeExt;
	}
	public void setDataTypeExt(String dataTypeExt){
		this.dataTypeExt = dataTypeExt;
	}
	/**
	 * 单位
	 */
	public String getUnit(){
		return unit;
	}
	public void setUnit(String unit){
		this.unit = unit;
	}
	/**
	 * 填报说明
	 */
	public String getNote(){
		return note;
	}
	public void setNote(String note){
		this.note = note;
	}
	/**
	 * 是否系统预设,不能删除系统预设的数据项
	 */
	public BooleanEnum getFixed(){
		return fixed;
	}
	public void setFixed(BooleanEnum fixed){
		this.fixed = fixed;
	}
	/**
	 * 实体状态
	 */
	public EntityStatus getEntityStatus(){
		return entityStatus;
	}
	public void setEntityStatus(EntityStatus entityStatus){
		this.entityStatus = entityStatus;
	}
	/**
	 * 创建时间
	 */
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	/**
	 * 创建人姓名
	 */
	public String getCreator(){
		return creator;
	}
	public void setCreator(String creator){
		this.creator = creator;
	}
	/**
	 * 创建人ID
	 */
	public Long getCreatorId(){
		return creatorId;
	}
	public void setCreatorId(Long creatorId){
		this.creatorId = creatorId;
	}
	/**
	 * 修改时间
	 */
	public Date getModifyTime(){
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime){
		this.modifyTime = modifyTime;
	}
	/**
	 * 修改人姓名
	 */
	public String getModifier(){
		return modifier;
	}
	public void setModifier(String modifier){
		this.modifier = modifier;
	}
	/**
	 * 修改人ID
	 */
	public Long getModifierId(){
		return modifierId;
	}
	public void setModifierId(Long modifierId){
		this.modifierId = modifierId;
	}
}