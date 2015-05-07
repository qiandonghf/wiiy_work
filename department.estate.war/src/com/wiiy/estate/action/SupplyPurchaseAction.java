package com.wiiy.estate.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.estate.entity.SupplyPurchase;
import com.wiiy.estate.service.SupplyPurchaseService;

/**
 * @author my
 */
public class SupplyPurchaseAction extends JqGridBaseAction<SupplyPurchase>{
	
	private SupplyPurchaseService supplyPurchaseService;
	private Result result;
	private SupplyPurchase supplyPurchase;
	private Long id;
	private String ids;
	
	
	public String save(){
		result = supplyPurchaseService.save(supplyPurchase);
		return JSON;
	}
	
	public String view(){
		result = supplyPurchaseService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = supplyPurchaseService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		SupplyPurchase dbBean = supplyPurchaseService.getBeanById(supplyPurchase.getId()).getValue();
		BeanUtil.copyProperties(supplyPurchase, dbBean);
		result = supplyPurchaseService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = supplyPurchaseService.deleteById(id);
		} else if(ids!=null){
			result = supplyPurchaseService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(SupplyPurchase.class));
	}
	
	@Override
	protected List<SupplyPurchase> getListByFilter(Filter fitler) {
		return supplyPurchaseService.getListByFilter(fitler).getValue();
	}
	
	
	public SupplyPurchase getSupplyPurchase() {
		return supplyPurchase;
	}

	public void setSupplyPurchase(SupplyPurchase supplyPurchase) {
		this.supplyPurchase = supplyPurchase;
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

	public void setSupplyPurchaseService(SupplyPurchaseService supplyPurchaseService) {
		this.supplyPurchaseService = supplyPurchaseService;
	}
	
	public Result getResult() {
		return result;
	}
	
}
