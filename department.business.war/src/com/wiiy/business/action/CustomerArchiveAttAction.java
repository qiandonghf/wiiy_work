package com.wiiy.business.action;

import java.text.Format;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.entity.BusinessCustomer;
import com.wiiy.business.entity.CustomerArchiveAtt;
import com.wiiy.business.entity.InvestmentArchiveAtt;
import com.wiiy.business.service.CustomerArchiveAttService;
import com.wiiy.business.service.CustomerService;

/**
 * @author my
 */
public class CustomerArchiveAttAction extends JqGridBaseAction<CustomerArchiveAtt>{
	
	private CustomerArchiveAttService customerArchiveAttService;
	private CustomerService customerService;
	private Result result;
	private CustomerArchiveAtt customerArchiveAtt;
	private List<CustomerArchiveAtt> customerArchiveAttList;
	private BusinessCustomer customer;
	private Long id;
	private String ids;
	
	
	public String save(){
		result = customerArchiveAttService.save(customerArchiveAtt);
		return JSON;
	}
	public String view(){
		result = customerArchiveAttService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = customerArchiveAttService.getBeanById(id);
		return EDIT;
	}
	public String update(){
		CustomerArchiveAtt dbBean = customerArchiveAttService.getBeanById(customerArchiveAtt.getId()).getValue();
		customerArchiveAtt.setName(customerArchiveAtt.getName()+dbBean.getNewName().substring(dbBean.getNewName().lastIndexOf(".")));
		BeanUtil.copyProperties(customerArchiveAtt, dbBean);
		result = customerArchiveAttService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = customerArchiveAttService.deleteById(id);
		} else if(ids!=null){
			result = customerArchiveAttService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		customer = customerService.getBeanById(id).getValue();
		result = customerArchiveAttService.getListByFilter(new Filter(CustomerArchiveAtt.class).eq("customerId", id));
		return LIST;
	}
	public String myEdit(){
		result = customerArchiveAttService.getBeanById(id);
		return "my"+EDIT;
	}
	
	public String myUpdate(){
		CustomerArchiveAtt dbBean = customerArchiveAttService.getBeanById(customerArchiveAtt.getId()).getValue();
		customerArchiveAtt.setName(customerArchiveAtt.getName()+dbBean.getNewName().substring(dbBean.getNewName().lastIndexOf(".")));
		BeanUtil.copyProperties(customerArchiveAtt, dbBean);
		result = customerArchiveAttService.update(dbBean);
		return JSON;
	}
	public String myDelete(){
		if(id!=null){
			result = customerArchiveAttService.deleteById(id);
		} else if(ids!=null){
			result = customerArchiveAttService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String myList(){
		customer = customerService.getBeanById(id).getValue();
		result = customerArchiveAttService.getListByFilter(new Filter(CustomerArchiveAtt.class).eq("investmentId", id));
		return "my"+LIST;
	}
	
	@Override
	protected List<CustomerArchiveAtt> getListByFilter(Filter fitler) {
		return customerArchiveAttService.getListByFilter(fitler).getValue();
	}
	
	
	public CustomerArchiveAtt getCustomerArchiveAtt() {
		return customerArchiveAtt;
	}

	public void setCustomerArchiveAtt(CustomerArchiveAtt customerArchiveAtt) {
		this.customerArchiveAtt = customerArchiveAtt;
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

	public void setCustomerArchiveAttService(CustomerArchiveAttService customerArchiveAttService) {
		this.customerArchiveAttService = customerArchiveAttService;
	}
	
	public Result getResult() {
		return result;
	}

	public BusinessCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(BusinessCustomer customer) {
		this.customer = customer;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	public List<CustomerArchiveAtt> getCustomerArchiveAttList() {
		return customerArchiveAttList;
	}
	public void setCustomerArchiveAttList(
			List<CustomerArchiveAtt> customerArchiveAttList) {
		this.customerArchiveAttList = customerArchiveAttList;
	}
	
}
