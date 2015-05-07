package com.wiiy.business.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.business.entity.Bail;
import com.wiiy.business.preferences.enums.ContractRentStatusEnum;
import com.wiiy.business.service.BailService;

/**
 * @author my
 */
public class BailAction extends JqGridBaseAction<Bail>{
	
	private BailService bailService;
	private Result result;
	private Bail bail;
	private Long id;
	private String ids;
	
	private ContractRentStatusEnum type;
	
	public String save(){
		result = bailService.save(bail);
		return JSON;
	}
	
	public String view(){
		result = bailService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = bailService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		Bail dbBean = bailService.getBeanById(bail.getId()).getValue();
		BeanUtil.copyProperties(bail, dbBean);
		result = bailService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = bailService.deleteById(id);
		} else if(ids!=null){
			result = bailService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(Bail.class);
		if(type != null){
			filter.eq("rentState", type);
		}
		return refresh(filter);
	}
	
	@Override
	protected List<Bail> getListByFilter(Filter fitler) {
		return bailService.getListByFilter(fitler).getValue();
	}
	
	
	public Bail getBail() {
		return bail;
	}

	public void setBail(Bail bail) {
		this.bail = bail;
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

	public void setBailService(BailService bailService) {
		this.bailService = bailService;
	}
	
	public Result getResult() {
		return result;
	}

	public ContractRentStatusEnum getType() {
		return type;
	}

	public void setType(ContractRentStatusEnum type) {
		this.type = type;
	}
	
}
