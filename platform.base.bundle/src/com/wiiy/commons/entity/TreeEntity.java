package com.wiiy.commons.entity;

import java.util.List;

public class TreeEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2134063585199697260L;
	
	public static final String STATE_OPEN = "open";
	
	public static final String STATE_CLOSED = "closed";
	
	/**
	 * 父ID
	 */
	private Long parentId;
	/**
	 * 节点名称
	 */
	private String text;
	/**
	 * 级别从零开始
	 */
	private Integer level;
	/**
	 * 级别从零开始
	 */
	private Integer order;
	/**
	 * 状态打开关闭
	 */
	private String state;
	/**
	 * 选中状态
	 */
	private Boolean checked;
	/**
	 * 父 Ids 数据格式  ,1,2,
	 */
	private String parentIds;
	/**
	 * 图片
	 */
	private String iconCls;
	/**
	 * 子集合
	 */
	private List children;
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public List getChildren() {
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
}
