package com.wiiy.external.service.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.SendTimeTypeEnum;

public class SMSDto {
	protected Long smsId;
	 /**
     * 创建人
     */
    protected String creator;
    /**
     * 创建人ID
     */
    protected Long creatorId;
	
	/**
	 * 短信内容
	 */
	private String content;
	/**
	 * 定时发送
	 */
	private SendTimeTypeEnum timeType;
	/**
	 * 指定发送时间
	 */
	private Date sendTime;
	/**
	 * 收件人列表
	 */
	private List<SMSReceiverDto> receiverList;
	
	public SMSDto() {
	}

	public SMSDto(String creator, Long creatorId, String content,
			SendTimeTypeEnum timeType, Date sendTime) {
		super();
		this.creator = creator;
		this.creatorId = creatorId;
		this.content = content;
		this.timeType = timeType;
		this.sendTime = sendTime;
		receiverList = new ArrayList<SMSReceiverDto>();
	}


	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public SendTimeTypeEnum getTimeType() {
		return timeType;
	}
	public void setTimeType(SendTimeTypeEnum timeType) {
		this.timeType = timeType;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public List<SMSReceiverDto> getReceiverList() {
		return receiverList;
	}
	public void setReceiverList(List<SMSReceiverDto> receiverList) {
		this.receiverList = receiverList;
	}

	public Long getSmsId() {
		return smsId;
	}

	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}

}
