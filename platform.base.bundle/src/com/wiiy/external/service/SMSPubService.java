package com.wiiy.external.service;

import org.hibernate.Session;

import com.wiiy.external.service.dto.SMSDto;
import com.wiiy.hibernate.Result;
/**
 * 短信公共接口
 * @author Aswan
 *
 */
public interface SMSPubService {
	/**
	 * 发送短信
	 * @param sms
	 * @return
	 */
	public Result<SMSDto> send(SMSDto sms,Session session);
	
	public Result<SMSDto> send(String receiverMobile,String content,String receiverName,Session session);
	
	public Result<SMSDto> sendByAdmin(String receiverMobile,String content,String receiverName);
	
	public Result<SMSDto> send(String[] receiverMobile,String content,String[] receiverName,Session session);
	
	public Result<SMSDto> send(String[] receiverMobile,String[] content,String[] receiverName,Session session);
	
	public Result<SMSDto> sendByAdmin(String[] receiverMobile,String[] content,String[] receiverName);
	
	public Result<SMSDto> send(String[] receiverMobile,String[] content,String[] receiverName);
	
	public Result<SMSDto> send(String receiverMobile,String content,String receiverName);
	
	public Result<SMSDto> send(SMSDto sms);
	
	public Result deleteById(Long id,Session session);
	public Result deleteReceiverById(Long id,Session session);
}
