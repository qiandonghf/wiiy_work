package com.wiiy.external.service.dto;


public class SMSReceiverDto {
	private Long smsReceiverId;
	/**
	 * 收件人名称
	 */
	private String receiverName;
	
	/**
	 * 收件人号码
	 */
	private String phone;
	
	public SMSReceiverDto() {
	}
	
	public SMSReceiverDto(String receiverName, String phone) {
		super();
		this.receiverName = receiverName;
		//this.receiverId = receiverId;
		this.phone = phone;
	}

	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getSmsReceiverId() {
		return smsReceiverId;
	}

	public void setSmsReceiverId(Long smsReceiverId) {
		this.smsReceiverId = smsReceiverId;
	}
}
