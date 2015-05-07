package com.wiiy.synthesis.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.external.service.SMSPubService;
import com.wiiy.external.service.SysEmailSenderPubService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.synthesis.activator.SynthesisActivator;
import com.wiiy.synthesis.dao.ScheduleDao;
import com.wiiy.synthesis.entity.Schedule;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.preferences.enums.PromotEnum;
import com.wiiy.synthesis.preferences.enums.RepeatEnum;
import com.wiiy.synthesis.service.ScheduleService;

public class ScheduleServiceImpl implements ScheduleService{
	
	private ScheduleDao scheduleDao;
	
	public void setScheduleDao(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}

	@Override
	public Result<Schedule> delete(Schedule t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = scheduleDao.openSession();
			tr = session.beginTransaction();
			if(t.getEmailId()!=null){
				SynthesisActivator.getService(SysEmailSenderPubService.class).deleteById(t.getEmailId(), session);
			}
			if(t.getSmsId()!=null){
				SynthesisActivator.getService(SysEmailSenderPubService.class).deleteById(t.getSmsId(), session);
			}
			SynthesisActivator.getOperationLogService().logOP("删除日程【"+t.getTitle()+"】");
			session.delete(t);
			tr.commit();
			return Result.success(R.Schedule.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.Schedule.DELETE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<Schedule> deleteById(Serializable id) {
		try {
			Schedule schedule = getBeanById(id).getValue();
			delete(schedule);
			return Result.success(R.Schedule.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.Schedule.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Schedule> deleteByIds(String ids) {
		try {
			scheduleDao.deleteByIds(ids);
			return Result.success(R.Schedule.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.Schedule.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Schedule> getBeanByFilter(Filter filter) {
		try {
			return Result.value(scheduleDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Schedule.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Schedule> getBeanById(Serializable id) {
		try {
			return Result.value(scheduleDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.Schedule.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Schedule>> getList() {
		try {
			return Result.value(scheduleDao.getList());
		} catch (Exception e) {
			return Result.failure(R.Schedule.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Schedule>> getListByFilter(Filter filter) {
		try {
			return Result.value(scheduleDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Schedule.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Schedule> save(Schedule t) {
		Session session = null;
		Transaction tr = null;
		try{
			session = scheduleDao.openSession();
			tr = session.beginTransaction();
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			if(t.getPromot()==null){
				t.setPromot(PromotEnum.NOPROMOT);
			}
			if(t.getDefaultEmail()==null){
				t.setDefaultEmail(BooleanEnum.NO);
			}
			if(t.getEmail()==null){
				t.setEmail(BooleanEnum.NO);
			}
			if(t.getSms()==null){
				t.setSms(BooleanEnum.NO);
			}
			if(t.getRepeatType()==null){
				t.setRepeatType(RepeatEnum.NOREPEAT);
			}
			t.setOwnerId(SynthesisActivator.getSessionUser().getId());
			session.save(t);
			SynthesisActivator.getOperationLogService().logOP("创建日程【"+t.getTitle()+"】");
			tr.commit();
			try {
				SMSPubService smsPubService = SynthesisActivator.getService(SMSPubService.class);
				SysEmailSenderPubService sysEmailSenderPubService = SynthesisActivator.getService(SysEmailSenderPubService.class);
				if(!t.getPromot().equals(PromotEnum.NOPROMOT) ){
					if(smsPubService!=null && smsActive()){
						if(t.getSms().equals(BooleanEnum.YES)){
							String content = SynthesisActivator.getAppConfig().getConfig("scheduleRemind").getParameter("smsModule");
							content = content.replace("${title}", t.getTitle());
							String receiverMobile = SynthesisActivator.getSessionUser().getMobile();
							String receiverName = SynthesisActivator.getSessionUser().getRealName();
							smsPubService.send(receiverMobile, content, receiverName);
							session.merge(t);
						}
					}
					if(sysEmailSenderPubService!=null && emailActive()){
						if(t.getEmail().equals(BooleanEnum.YES)||t.getDefaultEmail().equals(BooleanEnum.YES)){
							String content = parseHTML("web/msgRemindModule/msgRemindModule.html").toString();
							String subject = "日程提醒";
							String receviceName =  SynthesisActivator.getSessionUser().getRealName();
							content = content.replace("${subject}", t.getTitle());
							content = content.replace("${msgType}", "日程提醒");
							content = content.replace("${url}", basePath()+"parkmanager.oa/schedule!view.action?id="+t.getId());
							content = content.replace("${receiver}",receviceName);
							content = content.replace("${customerName}",receviceName);
							Long userId = Long.valueOf(SynthesisActivator.getAppConfig().getConfig("center").getParameter("name"));
							String userName = SynthesisActivator.getUserById(userId).getRealName();
							content = content.replace("${sender}", userName);
							content = content.replace("${content}", t.getMemo());
							content = content.replace("${msgLink}", SynthesisActivator.getHttpSessionService().getRemindEmailLink());
							sysEmailSenderPubService.send(SynthesisActivator.getSessionUser().getEmail(), content, subject);
							session.merge(t);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return Result.success(R.Schedule.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Schedule.class)));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.Schedule.SAVE_FAILURE);
		} finally {
			session.close();
		}
	}
	
	private boolean emailActive(){
		String msgSet =  SynthesisActivator.getAppConfig().getConfig("scheduleRemind").getParameter("email");
		if(msgSet.equals("active")){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean smsActive(){
		String msgSet =  SynthesisActivator.getAppConfig().getConfig("scheduleRemind").getParameter("sms");
		if(msgSet.equals("active")){
			return true;
		}else{
			return false;
		}
	}
	
	private StringBuffer parseHTML(String str){
		URL url = SynthesisActivator.getURL(str);
		InputStreamReader Inputreader;
		StringBuffer data = new StringBuffer();
		try {
			Inputreader = new InputStreamReader(url.openStream(),"utf-8");
			BufferedReader br = new BufferedReader(Inputreader);
			String temp=br.readLine();
			while( temp!=null){
				data.append(temp+"\n");
				temp=br.readLine(); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	private String basePath(){
		return ServletActionContext.getRequest().getScheme()+"://"+ServletActionContext.getRequest().getServerName()+":"+ServletActionContext.getRequest().getServerPort()+"/";
	}

	@Override
	public Result<Schedule> update(Schedule t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = scheduleDao.openSession();
			tr = session.beginTransaction();
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			session.update(t);
			if(t.getEmailId()!=null){
				if(!t.getPromot().equals(PromotEnum.NOPROMOT)){
					if(t.getEmail().equals(BooleanEnum.NO)){
						SynthesisActivator.getService(SysEmailSenderPubService.class).deleteById(t.getEmailId(), session);
					}
				}else{
					SynthesisActivator.getService(SysEmailSenderPubService.class).deleteById(t.getEmailId(), session);
				}
			}
			if(t.getSmsId()!=null){
				if(!t.getPromot().equals(PromotEnum.NOPROMOT)){
					if(t.getSms().equals(BooleanEnum.NO)){
						SynthesisActivator.getService(SysEmailSenderPubService.class).deleteById(t.getSmsId(), session);
					}
				}else{
					SynthesisActivator.getService(SysEmailSenderPubService.class).deleteById(t.getSmsId(), session);
				}
			}
			SynthesisActivator.getOperationLogService().logOP("更新日程【"+t.getTitle()+"】");
			tr.commit();
			return Result.success(R.Schedule.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Schedule.class)));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.Schedule.UPDATE_FAILURE);
		} finally {
			session.close();
		}
	}
	
	
	@Override
	public Result save(List<Schedule> insertList) {
		Session session = null;
		Transaction tr = null;
		try {
			session = scheduleDao.openSession();
			tr = session.beginTransaction();
			for (Schedule t : insertList) {
				t.setCreateTime(new Date());
				t.setModifyTime(t.getCreateTime());
				session.save(t);
				SMSPubService smsPubService = SynthesisActivator.getService(SMSPubService.class);
				SysEmailSenderPubService sysEmailSenderPubService = SynthesisActivator.getService(SysEmailSenderPubService.class);
				if(!t.getPromot().equals(PromotEnum.NOPROMOT) ){
					if(smsPubService!=null && smsActive()){
						if(t.getSms().equals(BooleanEnum.YES)){
							String content = SynthesisActivator.getAppConfig().getConfig("scheduleRemind").getParameter("smsModule");
							content = content.replace("${title}", t.getTitle());
							String receiverMobile = SynthesisActivator.getSessionUser().getMobile();
							String receiverName = SynthesisActivator.getSessionUser().getRealName();
							smsPubService.send(receiverMobile, content, receiverName);
							session.merge(t);
						}
					}
					if(sysEmailSenderPubService!=null && emailActive()){
						if(t.getEmail().equals(BooleanEnum.YES)||t.getDefaultEmail().equals(BooleanEnum.YES)){
							String content = parseHTML("web/msgRemindModule/msgRemindModule.html").toString();
							String subject = "日程提醒";
							String receviceName =  SynthesisActivator.getSessionUser().getRealName();
							content = content.replace("${subject}", t.getTitle());
							content = content.replace("${msgType}", "日程提醒");
							content = content.replace("${url}", basePath()+"parkmanager.oa/schedule!view.action?id="+t.getId());
							content = content.replace("${receiver}",receviceName);
							content = content.replace("${customerName}",receviceName);
							Long userId = Long.valueOf(SynthesisActivator.getAppConfig().getConfig("center").getParameter("name"));
							String userName = SynthesisActivator.getUserById(userId).getRealName();
							content = content.replace("${sender}", userName);
							content = content.replace("${content}", t.getMemo());
							content = content.replace("${msgLink}", SynthesisActivator.getHttpSessionService().getRemindEmailLink());
							sysEmailSenderPubService.send(SynthesisActivator.getSessionUser().getEmail(), content, subject);
							session.merge(t);
						}
					}
				}
			}
			tr.commit();
			return Result.success();
		} catch (Exception e) {
			tr.rollback();
			return Result.failure("");
		} finally {
			session.close();
		}
	}

}
