package com.wiiy.external.service.dto;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.SendTimeTypeEnum;

public class EmailDto {
	
	private Long id;
	private String sender;//发送人
	private Date sendDate;//发送时间
	private String subject;//主题
	private String content;//内容
	private Boolean containAttach;//是否包含附件
	protected List<EmailAttDto> emailAttDtoList;//附件
	private int mailAttNo;//附件数量
	private List<EmailReceiverDto> receiverList;
	
	 /**
     * 创建人
     */
    protected String creator;
    /**
     * 创建人ID
     */
    protected Long creatorId;
	/**
	 * 定时发送
	 */
	private SendTimeTypeEnum timeType;
	/**
	 * 指定发送时间
	 */
	private Date sendTime;
	/**
	 * 附件列表
	 */
	private List<File> attList;
	
	public List<File> getAttList() {
		return attList;
	}
	public void setAttList(List<File> attList) {
		this.attList = attList;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Boolean getContainAttach() {
		return containAttach;
	}
	public void setContainAttach(Boolean containAttach) {
		this.containAttach = containAttach;
	}
	public List<EmailAttDto> getEmailAttDtoList() {
		return emailAttDtoList;
	}
	public void setEmailAttDtoList(List<EmailAttDto> emailAttDtoList) {
		this.emailAttDtoList = emailAttDtoList;
	}
	public int getMailAttNo() {
		return mailAttNo;
	}
	public void setMailAttNo(int mailAttNo) {
		this.mailAttNo = mailAttNo;
	}
	public List<EmailReceiverDto> getReceiverList() {
		return receiverList;
	}
	public void setReceiverList(List<EmailReceiverDto> receiverList) {
		this.receiverList = receiverList;
	}

}
