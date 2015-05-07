package com.wiiy.sale.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.sale.entity.RegistrationBook;
import com.wiiy.sale.service.RegistrationBookService;
import com.wiiy.sale.service.SaleCustomerService;

/**
 * @author my
 */
public class RegistrationBookAction extends JqGridBaseAction<RegistrationBook>{
	
	private RegistrationBookService registrationBookService;
	private Result result;
	private RegistrationBook registrationBook;
	private SaleCustomerService customerService;
	private Long id;
	private String ids;
	
	public String addBySaleCustomerId(){
		result = customerService.getBeanById(id);
		return "addBySaleCustomerId";
	}
	
	
	public String save(){
		result = registrationBookService.save(registrationBook);
		return JSON;
	}
	
	public String view(){
		result = registrationBookService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = registrationBookService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		RegistrationBook dbBean = registrationBookService.getBeanById(registrationBook.getId()).getValue();
		BeanUtil.copyProperties(registrationBook, dbBean);
		result = registrationBookService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = registrationBookService.deleteById(id);
		} else if(ids!=null){
			result = registrationBookService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(RegistrationBook.class);
		filter.createAlias("user", "user");
		filter.createAlias("customer", "customer");
		if (id != null) {
			filter.eq("roomId", id);
		}
		return refresh(filter);
	}
	
	public String changeState() {
		result = registrationBookService.changeState(id);
		return JSON;
	}
	
	@Override
	protected List<RegistrationBook> getListByFilter(Filter fitler) {
		return registrationBookService.getListByFilter(fitler).getValue();
	}
	
	
	public RegistrationBook getRegistrationBook() {
		return registrationBook;
	}

	public void setRegistrationBook(RegistrationBook registrationBook) {
		this.registrationBook = registrationBook;
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

	public void setRegistrationBookService(RegistrationBookService registrationBookService) {
		this.registrationBookService = registrationBookService;
	}
	
	public Result getResult() {
		return result;
	}

	public void setSaleCustomerService(SaleCustomerService customerService) {
		this.customerService = customerService;
	}
	
}
