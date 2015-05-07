package com.wiiy.pf.dto;

import java.util.Date;
import java.util.List;

public class BillRentDto {
	private Long planId;
	private String code;
	private Date planPayDate;
	private Double planFee;
	private Long contractId;
	private String	contractName;
	private Double contractAmount;
	private Double completedFee;
	private String taskId;
	private String taskName;
	private List<BillTaskDto> taskDtos;
	public Long getPlanId() {
		return planId;
	}
	public void setPlanId(Long planId) {
		this.planId = planId;
	}
	public Date getPlanPayDate() {
		return planPayDate;
	}
	public void setPlanPayDate(Date planPayDate) {
		this.planPayDate = planPayDate;
	}
	public Double getPlanFee() {
		return planFee;
	}
	public void setPlanFee(Double planFee) {
		this.planFee = planFee;
	}
	public Long getContractId() {
		return contractId;
	}
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public Double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<BillTaskDto> getTaskDtos() {
		return taskDtos;
	}
	public void setTaskDtos(List<BillTaskDto> taskDtos) {
		this.taskDtos = taskDtos;
	}
	public Double getCompletedFee() {
		return completedFee;
	}
	public void setCompletedFee(Double completedFee) {
		this.completedFee = completedFee;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	
}
