package com.wiiy.business.dto;

public class OrgTypeDto {

	private int amount;
	private String value;
	private String name;
	private Enum types;
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
