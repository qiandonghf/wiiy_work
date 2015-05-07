package com.wiiy.estate.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.estate.entity.FixFee;
import com.wiiy.estate.service.FixFeeService;

/**
 * @author my
 */
public class FixFeeAction extends JqGridBaseAction<FixFee>{
	
	private FixFeeService fixFeeService;
	private Result result;
	private FixFee fixFee;
	private Long id;
	private String ids;
	
	public String save(){
		result = fixFeeService.save(fixFee);
		return JSON;
	}
	
	public String view(){
		result = fixFeeService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = fixFeeService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		FixFee dbBean = fixFeeService.getBeanById(fixFee.getId()).getValue();
		BeanUtil.copyProperties(fixFee, dbBean);
		result = fixFeeService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = fixFeeService.deleteById(id);
		} else if(ids!=null){
			result = fixFeeService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(FixFee.class));
	}
	
	@Override
	protected List<FixFee> getListByFilter(Filter fitler) {
		return fixFeeService.getListByFilter(fitler).getValue();
	}
	
	
	public FixFee getFixFee() {
		return fixFee;
	}

	public void setFixFee(FixFee fixFee) {
		this.fixFee = fixFee;
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

	public void setFixFeeService(FixFeeService fixFeeService) {
		this.fixFeeService = fixFeeService;
	}
	
	public Result getResult() {
		return result;
	}
	
}
