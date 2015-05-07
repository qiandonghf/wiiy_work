package com.wiiy.synthesis.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.synthesis.entity.SealRegistrationAtt;
import com.wiiy.synthesis.service.SealRegistrationAttService;

/**
 * @author my
 */
public class SealRegistrationAttAction extends JqGridBaseAction<SealRegistrationAtt>{
	
	private SealRegistrationAttService sealRegistrationAttService;
	private Result result;
	private SealRegistrationAtt sealRegistrationAtt;
	private Long id;
	private String ids;
	
	
	public String save(){
		result = sealRegistrationAttService.save(sealRegistrationAtt);
		return JSON;
	}
	
	public String view(){
		result = sealRegistrationAttService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = sealRegistrationAttService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		SealRegistrationAtt dbBean = sealRegistrationAttService.getBeanById(sealRegistrationAtt.getId()).getValue();
		BeanUtil.copyProperties(sealRegistrationAtt, dbBean);
		result = sealRegistrationAttService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = sealRegistrationAttService.deleteById(id);
		} else if(ids!=null){
			result = sealRegistrationAttService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(SealRegistrationAtt.class));
	}
	
	@Override
	protected List<SealRegistrationAtt> getListByFilter(Filter fitler) {
		return sealRegistrationAttService.getListByFilter(fitler).getValue();
	}
	
	
	public SealRegistrationAtt getSealRegistrationAtt() {
		return sealRegistrationAtt;
	}

	public void setSealRegistrationAtt(SealRegistrationAtt sealRegistrationAtt) {
		this.sealRegistrationAtt = sealRegistrationAtt;
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

	public void setSealRegistrationAttService(SealRegistrationAttService sealRegistrationAttService) {
		this.sealRegistrationAttService = sealRegistrationAttService;
	}
	
	public Result getResult() {
		return result;
	}
	
}
