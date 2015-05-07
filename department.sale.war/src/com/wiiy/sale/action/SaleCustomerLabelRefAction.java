package com.wiiy.sale.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.sale.entity.SaleCustomerLabelRef;
import com.wiiy.sale.service.SaleCustomerLabelRefService;

/**
 * @author my
 */
public class SaleCustomerLabelRefAction extends JqGridBaseAction<SaleCustomerLabelRef>{
	
	private SaleCustomerLabelRefService customerLabelRefService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private SaleCustomerLabelRef customerLabelRef;
	private Long id;
	private String ids;
	
	
	public String save(){
		result = customerLabelRefService.save(customerLabelRef);
		return JSON;
	}
	
	public String view(){
		result = customerLabelRefService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = customerLabelRefService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		SaleCustomerLabelRef dbBean = customerLabelRefService.getBeanById(customerLabelRef.getId()).getValue();
		BeanUtil.copyProperties(customerLabelRef, dbBean);
		result = customerLabelRefService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = customerLabelRefService.deleteById(id);
		} else if(ids!=null){
			result = customerLabelRefService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(SaleCustomerLabelRef.class));
	}
	
	@Override
	protected List<SaleCustomerLabelRef> getListByFilter(Filter fitler) {
		return customerLabelRefService.getListByFilter(fitler).getValue();
	}
	
	
	public SaleCustomerLabelRef getCustomerLabelRef() {
		return customerLabelRef;
	}

	public void setCustomerLabelRef(SaleCustomerLabelRef customerLabelRef) {
		this.customerLabelRef = customerLabelRef;
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

	public void setCustomerLabelRefService(SaleCustomerLabelRefService customerLabelRefService) {
		this.customerLabelRefService = customerLabelRefService;
	}
	
	@SuppressWarnings("rawtypes")
	public Result getResult() {
		return result;
	}
	
}
