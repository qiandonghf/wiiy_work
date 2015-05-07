package com.wiiy.sale.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.sale.preferences.enums.OwnerEnum;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.sale.activator.SaleActivator;
import com.wiiy.sale.entity.SaleCustomerCategory;
import com.wiiy.sale.service.SaleCustomerCategoryService;

/**
 * @author my
 */
public class SaleCustomerCategoryAction extends JqGridBaseAction<SaleCustomerCategory>{
	
	private SaleCustomerCategoryService customerCategoryService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private SaleCustomerCategory customerCategory;
	private Long id;
	private String ids;
	
	
	public String save(){
		customerCategory.setOwnerEnum(OwnerEnum.PUBLIC);
		customerCategory.setOwnerId(SaleActivator.getSessionUser().getId());
		result = customerCategoryService.save(customerCategory);
		return JSON;
	}
	
	public String view(){
		result = customerCategoryService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = customerCategoryService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		SaleCustomerCategory dbBean = customerCategoryService.getBeanById(customerCategory.getId()).getValue();
		BeanUtil.copyProperties(customerCategory, dbBean);
		result = customerCategoryService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = customerCategoryService.deleteById(id);
		} else if(ids!=null){
			result = customerCategoryService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(SaleCustomerCategory.class));
	}
	
	@Override
	protected List<SaleCustomerCategory> getListByFilter(Filter fitler) {
		return customerCategoryService.getListByFilter(fitler).getValue();
	}
	
	
	public SaleCustomerCategory getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(SaleCustomerCategory customerCategory) {
		this.customerCategory = customerCategory;
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

	public void setCustomerCategoryService(SaleCustomerCategoryService customerCategoryService) {
		this.customerCategoryService = customerCategoryService;
	}
	
	@SuppressWarnings("rawtypes")
	public Result getResult() {
		return result;
	}
	
}
