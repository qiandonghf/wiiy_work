package com.wiiy.pf.util;

import java.util.Date;

import com.wiiy.core.entity.User;
import com.wiiy.pf.entity.ContactBaseLog;
import com.wiiy.pf.preferences.enums.ProcessTypeEnum;

public final class ContactLogUtil {
	private ContactLogUtil(){};
	
	/**
	 * 保存联系单轨迹
	 * @param id
	 * @param typeEnum
	 * @param user
	 * @return
	 */
	public static ContactBaseLog saveLog(
			Long id,
			ProcessTypeEnum typeEnum,
			User user,
			String taskId) {
		ContactBaseLog log = new ContactBaseLog();
		log.setContactId(id);
		log.setContactType(typeEnum);
		log = creator(log, user);
		log = modify(log, user);
		log.setTaskId(taskId);
		log.setContent("新建"+typeEnum.getTitle());
		return log;
	}
	
	/**
	 * 保存联系单轨迹
	 * @param id
	 * @param typeEnum
	 * @param user
	 * @return
	 */
	public static ContactBaseLog saveLog(
			Long id,
			User user,
			String taskId,
			String content) {
		ContactBaseLog log = new ContactBaseLog();
		log.setContactId(id);
		log.setTaskId(taskId);
		log = creator(log, user);
		log = modify(log, user);
		log.setContent(content);
		return log;
	}
	
	public static void updateLog(
			Long id,
			ContactBaseLog log,
			ProcessTypeEnum typeEnum,
			String content,
			User user) {
		
	}
	
	private static ContactBaseLog creator(ContactBaseLog log,User user) {
		log.setCreator(user.getRealName());
		log.setCreatorId(user.getId());
		log.setCreateTime(new Date());
		return log;
	}
	
	private static ContactBaseLog modify(ContactBaseLog log,User user){
		log.setModifier(user.getRealName());
		log.setModifierId(user.getId());
		log.setModifyTime(new Date());
		return log;
	}
}
