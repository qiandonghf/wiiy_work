package com.wiiy.sale.dto;

import com.wiiy.commons.entity.BaseEntity;

public class TreeDto {

	public static final String OPEN = "open";
	public static final String CLOSED = "closed";
	
	private String sid;
	private Long id;
	private String text;
	private Boolean checked;//����Ƿ���Ҷ�ӽڵ�
	private String state;//�ڵ�չ��״̬
	private BaseEntity value;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public BaseEntity getValue() {
		return value;
	}
	public void setValue(BaseEntity value) {
		this.value = value;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
}
