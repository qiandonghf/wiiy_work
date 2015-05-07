package com.wiiy.synthesis.external.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.external.service.SMSPubService;
import com.wiiy.external.service.dto.SMSDto;
import com.wiiy.external.service.dto.SMSReceiverDto;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.activator.SynthesisActivator;
import com.wiiy.synthesis.dao.SmsDao;
import com.wiiy.synthesis.entity.Sms;
import com.wiiy.synthesis.entity.SmsReceiver;

public class SMSPubServiceImpl implements SMSPubService {
	
	private SmsDao smsDao;
	public void setSmsDao(SmsDao smsDao) {
		this.smsDao = smsDao;
	}
	
	/**
	 * 发送短信
	 * @param sms
	 * @return
	 */
	public Result<SMSDto> send(SMSDto sms,Session session) {
		Sms s = new Sms();
		s.setContent(sms.getContent());
		s.setSended(BooleanEnum.NO);
		Date time = new Date();
		if(sms.getSendTime()!=null){
			time = sms.getSendTime();
		}
		s.setSendTime(time);
		s.setTimeType(sms.getTimeType());
		s.setCreateTime(new Date());
		if(sms.getCreator()==null){//没有发送人,默认为admin
			Long userId = Long.valueOf(SynthesisActivator.getAppConfig().getConfig("center").getParameter("name"));
			if(SynthesisActivator.getUserById(userId)!=null){
				sms.setCreator(SynthesisActivator.getUserById(userId).getRealName());
				sms.setCreatorId(userId);
			}else{
				sms.setCreator("超级管理员");
			}
		}
		s.setCreator(sms.getCreator());
		s.setCreatorId(sms.getCreatorId());
		s.setEntityStatus(EntityStatus.NORMAL);
		session.save(s);
		for (SMSReceiverDto dto : sms.getReceiverList()) {
			SmsReceiver receiver = new SmsReceiver();
			receiver.setCreateTime(new Date());
			if(sms.getCreator()==null){
				Long userId = Long.valueOf(SynthesisActivator.getAppConfig().getConfig("center").getParameter("name"));
				if(SynthesisActivator.getUserById(userId)!=null){
					sms.setCreator(SynthesisActivator.getUserById(userId).getRealName());
					sms.setCreatorId(userId);
				}else{
					sms.setCreator("超级管理员");
				}
			}
			receiver.setCreator(sms.getCreator());
			receiver.setCreatorId(sms.getCreatorId());
			receiver.setEntityStatus(EntityStatus.NORMAL);
			receiver.setPhone(dto.getPhone());
			receiver.setReceiverName(dto.getReceiverName());
			receiver.setSended(s.getSended());
			receiver.setSmsId(s.getId());
			session.save(receiver);
		}
		sms.setSmsId(s.getId());
		return Result.value(sms);
	}

	/**
	 * 发送短信
	 * @param sms
	 * @return
	 */
	public Result<SMSDto> send(SMSDto sms) {
		Session session = null;
		try {
			session = smsDao.openSession();
			return send(sms,session);
		} catch (Exception e) {
			return null;
		} finally {
			session.close();
		}
	}
	@Override
	public Result deleteById(Long id,Session session) {
		session.createQuery("delete from SmsReceiver where smsId = "+id);
		session.createQuery("delete from Sms where id = "+id);
		return Result.success();
	}

	@Override
	public Result deleteReceiverById(Long id,Session session) {
		session.createQuery("delete from SmsReceiver where id = "+id);
		return Result.success();
	}

	@Override
	public Result<SMSDto> send(String receiverMobile, String content, String receiverName) {
		Session session = null;
		try {
			session = smsDao.openSession();
			return send(receiverMobile,content, receiverName ,session);
		} catch (Exception e) {
			return null;
		} finally {
			session.close();
		}
	}
	@Override
	public Result<SMSDto> send(String receiverMobile, String content, String receiverName,Session session) {
		SMSDto sms = new SMSDto();
		sms.setContent(content);
		List<SMSReceiverDto> receiverList = new ArrayList<SMSReceiverDto>();
		SMSReceiverDto receiver = new SMSReceiverDto();
		receiver.setPhone(receiverMobile);
		receiver.setReceiverName(receiverName);
		receiverList.add(receiver);
		sms.setReceiverList(receiverList);
		sms.setCreator(SynthesisActivator.getSessionUser().getRealName());
		sms.setCreatorId(SynthesisActivator.getSessionUser().getId());
		return send(sms,session);
	}

	@Override
	public Result<SMSDto> send(String[] receiverMobile, String content, String[] receiverName,Session session) {
		SMSDto sms = new SMSDto();
		sms.setContent(content);
		List<SMSReceiverDto> receiverList = new ArrayList<SMSReceiverDto>();
		for (int i=0;i<receiverMobile.length;i++) {
			SMSReceiverDto receiver = new SMSReceiverDto();
			receiver.setPhone(receiverMobile[i]);
			receiver.setReceiverName(receiverName[i]);
			receiverList.add(receiver);
		}
		sms.setReceiverList(receiverList);
		sms.setCreator(SynthesisActivator.getSessionUser().getRealName());
		sms.setCreatorId(SynthesisActivator.getSessionUser().getId());
		return send(sms,session);
	}
	
	@Override
	public Result<SMSDto> send(String[] receiverMobile, String[] content, String[] receiverName,Session session) {
		List<SMSReceiverDto> receiverList = new ArrayList<SMSReceiverDto>();
		SMSDto sms = null;
		for (int i=0;i<receiverMobile.length;i++) {
			sms = new SMSDto();
			sms.setContent(content[i]);
			SMSReceiverDto receiver = new SMSReceiverDto();
			receiver.setPhone(receiverMobile[i]);
			receiver.setReceiverName(receiverName[i]);
			receiverList.add(receiver);
			sms.setReceiverList(receiverList);
			send(sms,session);
		}
		return Result.value(sms);
	}
	
	@Override
	public Result<SMSDto> send(String[] receiverMobile, String[] content, String[] receiverName) {
		List<SMSReceiverDto> receiverList = new ArrayList<SMSReceiverDto>();
		SMSDto sms = null;
		for (int i=0;i<receiverMobile.length;i++) {
			sms = new SMSDto();
			sms.setContent(content[i]);
			SMSReceiverDto receiver = new SMSReceiverDto();
			receiver.setPhone(receiverMobile[i]);
			receiver.setReceiverName(receiverName[i]);
			receiverList.add(receiver);
			sms.setReceiverList(receiverList);
			send(sms);
		}
		return Result.value(sms);
	}

	@Override
	public Result<SMSDto> sendByAdmin(String[] receiverMobile, String[] content, String[] receiverName) {
		List<SMSReceiverDto> receiverList = new ArrayList<SMSReceiverDto>();
		SMSDto sms = null;
		for (int i=0;i<receiverMobile.length;i++) {
			sms = new SMSDto();
			sms.setContent(content[i]);
			SMSReceiverDto receiver = new SMSReceiverDto();
			receiver.setPhone(receiverMobile[i]);
			receiver.setReceiverName(receiverName[i]);
			receiverList.add(receiver);
			sms.setReceiverList(receiverList);
			Long userId = Long.valueOf(SynthesisActivator.getAppConfig().getConfig("center").getParameter("name"));
			if(SynthesisActivator.getUserById(userId)!=null){
				sms.setCreator(SynthesisActivator.getUserById(userId).getRealName());
				sms.setCreatorId(userId);
			}else{
				sms.setCreator("超级管理员");
			}
			send(sms);
		}
		return Result.value(sms);
	}

	@Override
	public Result<SMSDto> sendByAdmin(String receiverMobile, String content, String receiverName) {
		SMSDto sms = new SMSDto();
		sms.setContent(content);
		List<SMSReceiverDto> receiverList = new ArrayList<SMSReceiverDto>();
		SMSReceiverDto receiver = new SMSReceiverDto();
		receiver.setPhone(receiverMobile);
		receiver.setReceiverName(receiverName);
		receiverList.add(receiver);
		sms.setReceiverList(receiverList);
		Long userId = Long.valueOf(SynthesisActivator.getAppConfig().getConfig("center").getParameter("name"));
		if(SynthesisActivator.getUserById(userId)!=null){
			sms.setCreator(SynthesisActivator.getUserById(userId).getRealName());
			sms.setCreatorId(userId);
		}else{
			sms.setCreator("超级管理员");
		}
		return send(sms);
	}
	
}
