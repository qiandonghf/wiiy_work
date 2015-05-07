package com.wiiy.pf.dto;

import java.util.List;

import com.wiiy.pf.entity.ProcessType;

public class TypesDto {
	private String typeName;//类型名称
	private List<ProcessType> processTypes;//类型集合
	
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public List<ProcessType> getProcessTypes() {
		return processTypes;
	}
	public void setProcessTypes(List<ProcessType> processTypes) {
		this.processTypes = processTypes;
	}
	
	
}
