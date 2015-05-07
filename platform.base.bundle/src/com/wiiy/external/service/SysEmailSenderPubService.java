package com.wiiy.external.service;

import org.hibernate.Session;

import com.wiiy.external.service.dto.EmailDto;
import com.wiiy.hibernate.Result;
/**
 * 
 * @author Aswan
 *系统消息邮件接口
 */
public interface SysEmailSenderPubService {
	/**
	 * 发送系统消息邮件
	 * @param sms
	 * @return
	 */
	
	public Result<EmailDto> send(String receiverEmail,String content,String subject);
	
	public Result<EmailDto> send(String[] receiverEmail, String[] content,String subject);
	
	public Result<EmailDto> send(EmailDto email,Session session);
	
	public Result<EmailDto> send(EmailDto email);

	public Result deleteById(Long id,Session session);
	
}
