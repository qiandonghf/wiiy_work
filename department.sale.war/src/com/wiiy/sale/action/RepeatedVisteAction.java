package com.wiiy.sale.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.sale.entity.RepeatedViste;
import com.wiiy.sale.service.RepeatedVisteService;

/**
 * @author my
 */
public class RepeatedVisteAction extends JqGridBaseAction<RepeatedViste>{
	
	private RepeatedVisteService repeatedVisteService;
	private Result result;
	private RepeatedViste repeatedViste;
	private Long id;
	private String ids;
	
	
	public String save(){
		result = repeatedVisteService.save(repeatedViste);
		return JSON;
	}
	
	public String view(){
		result = repeatedVisteService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = repeatedVisteService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		RepeatedViste dbBean = repeatedVisteService.getBeanById(repeatedViste.getId()).getValue();
		BeanUtil.copyProperties(repeatedViste, dbBean);
		result = repeatedVisteService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = repeatedVisteService.deleteById(id);
		} else if(ids!=null){
			result = repeatedVisteService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(RepeatedViste.class));
	}
	
	@Override
	protected List<RepeatedViste> getListByFilter(Filter fitler) {
		return repeatedVisteService.getListByFilter(fitler).getValue();
	}
	
	
	public RepeatedViste getRepeatedViste() {
		return repeatedViste;
	}

	public void setRepeatedViste(RepeatedViste repeatedViste) {
		this.repeatedViste = repeatedViste;
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

	public void setRepeatedVisteService(RepeatedVisteService repeatedVisteService) {
		this.repeatedVisteService = repeatedVisteService;
	}
	
	public Result getResult() {
		return result;
	}
	
}
