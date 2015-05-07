package com.wiiy.pf.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.pf.entity.ContactBaseLog;
import com.wiiy.pf.service.ContactBaseLogService;

/**
 * @author my
 */
public class ContactBaseLogAction extends JqGridBaseAction<ContactBaseLog>{
	
	private ContactBaseLogService contactBaseLogService;
	private Result result;
	private ContactBaseLog contactBaseLog;
	private Long id;
	private String ids;
	
	
	public String save(){
		result = contactBaseLogService.save(contactBaseLog);
		return JSON;
	}
	
	public String view(){
		result = contactBaseLogService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = contactBaseLogService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		ContactBaseLog dbBean = contactBaseLogService.getBeanById(contactBaseLog.getId()).getValue();
		BeanUtil.copyProperties(contactBaseLog, dbBean);
		result = contactBaseLogService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = contactBaseLogService.deleteById(id);
		} else if(ids!=null){
			result = contactBaseLogService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(ContactBaseLog.class));
	}
	
	@Override
	protected List<ContactBaseLog> getListByFilter(Filter fitler) {
		return contactBaseLogService.getListByFilter(fitler).getValue();
	}
	
	
	public ContactBaseLog getContactBaseLog() {
		return contactBaseLog;
	}

	public void setContactBaseLog(ContactBaseLog contactBaseLog) {
		this.contactBaseLog = contactBaseLog;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setContactBaseLogService(ContactBaseLogService contactBaseLogService) {
		this.contactBaseLogService = contactBaseLogService;
	}
	
	public Result getResult() {
		return result;
	}
	
}
