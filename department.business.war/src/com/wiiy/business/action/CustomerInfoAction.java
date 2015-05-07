package com.wiiy.business.action;


import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.business.entity.CustomerInfo;
import com.wiiy.business.service.CustomerInfoService;

/**
 * @author my
 */
public class CustomerInfoAction extends JqGridBaseAction<CustomerInfo>{
	
	private CustomerInfoService customerInfoService;
	private Result result;
	private CustomerInfo customerInfo;
	private Long id;
	private String ids;
	private List<Integer> years;
	private String yearNo;
	private String monthNo;
	
	public String save(){
		result = customerInfoService.save(customerInfo);
		return JSON;
	}
	
	public String view(){
		result = customerInfoService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = customerInfoService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		result = customerInfoService.update(customerInfo);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = customerInfoService.deleteById(id);
		} else if(ids!=null){
			result = customerInfoService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(CustomerInfo.class));
	}
	
	@Override
	protected List<CustomerInfo> getListByFilter(Filter fitler) {
		return customerInfoService.getListByFilter(fitler).getValue();
	}
	
	
	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
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

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	
	public Result getResult() {
		return result;
	}
	
}
