package com.wiiy.business.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.business.entity.Rate;
import com.wiiy.business.service.RateService;

/**
 * @author my
 */
public class RateAction extends JqGridBaseAction<Rate>{
	
	private RateService rateService;
	private Result result;
	private Rate rate;
	private Long id;
	private String ids;
	
	
	public String save(){
		result = rateService.save(rate);
		return JSON;
	}
	
	public String view(){
		result = rateService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = rateService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		Rate dbBean = rateService.getBeanById(rate.getId()).getValue();
		BeanUtil.copyProperties(rate, dbBean);
		result = rateService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = rateService.deleteById(id);
		} else if(ids!=null){
			result = rateService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(Rate.class));
	}
	
	@Override
	protected List<Rate> getListByFilter(Filter fitler) {
		return rateService.getListByFilter(fitler).getValue();
	}
	
	
	public Rate getRate() {
		return rate;
	}

	public void setRate(Rate rate) {
		this.rate = rate;
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

	public void setRateService(RateService rateService) {
		this.rateService = rateService;
	}
	
	public Result getResult() {
		return result;
	}
	
}
