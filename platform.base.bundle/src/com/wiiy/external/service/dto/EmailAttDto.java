package com.wiiy.external.service.dto;

public class EmailAttDto {
	private String name;
	private String newName;
	private Integer partIndex;
	private int size;
	
	
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPartIndex() {
		return partIndex;
	}
	public void setPartIndex(Integer partIndex) {
		this.partIndex = partIndex;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

}
