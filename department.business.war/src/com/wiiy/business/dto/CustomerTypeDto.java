package com.wiiy.business.dto;

public class CustomerTypeDto {
	private int amount;
	private int incubatedNums;
	private String value;
	private String name;
	private Enum types;
	
	public int getIncubatedNums() {
		return incubatedNums;
	}
	public void setIncubatedNums(int incubatedNums) {
		this.incubatedNums = incubatedNums;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Enum getTypes() {
		return types;
	}
	public void setTypes(Enum types) {
		this.types = types;
	}
}
