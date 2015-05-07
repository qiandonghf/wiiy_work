package com.wiiy.common.dto;

import com.wiiy.common.preferences.enums.ParkStatusEnum;
import com.wiiy.commons.preferences.enums.BooleanEnum;


public class CustomerDto {
	private Long id;
	private String name;
	private String code;
	private ParkStatusEnum parkStatus;
    private BooleanEnum incubated;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public ParkStatusEnum getParkStatus() {
		return parkStatus;
	}
	public void setParkStatus(ParkStatusEnum parkStatus) {
		this.parkStatus = parkStatus;
	}
	public BooleanEnum getIncubated() {
		return incubated;
	}
	public void setIncubated(BooleanEnum incubated) {
		this.incubated = incubated;
	}
    
	
}
