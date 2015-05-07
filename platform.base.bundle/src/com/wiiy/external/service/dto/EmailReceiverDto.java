package com.wiiy.external.service.dto;


public class EmailReceiverDto {
	private Long receiverId;
	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 收件人名称
	 */
	private String receiverName;
	
	/**
	 * 收件人Email 地址
	 */
	private String address;
	
	public EmailReceiverDto() {
	}

	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
}
