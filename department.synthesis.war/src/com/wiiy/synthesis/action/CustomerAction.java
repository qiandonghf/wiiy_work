package com.wiiy.synthesis.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.common.preferences.enums.CustomerSupplierTypeEnum;
import com.wiiy.synthesis.entity.SynthesisCustomer;
import com.wiiy.synthesis.entity.SynthesisCustomerInfo;
import com.wiiy.synthesis.service.SynthesisCustomerService;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public class CustomerAction extends JqGridBaseAction<SynthesisCustomer>{
	
	private SynthesisCustomerService customerService;
	private Result result;
	private SynthesisCustomer customer;
	private SynthesisCustomerInfo customerInfo;
	private Long id;
	private String ids;
	private CustomerSupplierTypeEnum customerType;
	
	public String generateCode() {
		result = customerService.generateCode();
		return JSON;
	}
	
	public String save(){
		if ((customer.getSourceId() != null && //
				"".equals(customer.getSourceId()) || //
				customer.getSourceId() == null)) {
			customer.setSourceId(null);
			customer.setSource(null);
		}
		if ((customer.getTechnicId() != null && //
				"".equals(customer.getTechnicId()) || //
				customer.getTechnicId() == null)) {
			customer.setTechnicId(null);
			customer.setTechnic(null);
		}
		if (customer.getType() != null && //
				"".equals(customer.getType())) {
			customer.setType(null);
		}
		if (customerInfo.getRegTypeId() != null && 
				"".equals(customerInfo.getRegTypeId())) {
			customerInfo.setRegTypeId(null);
			customerInfo.setRegType(null);
		}
		if (customerInfo.getCurrencyTypeId() != null && 
				"".equals(customerInfo.getCurrencyTypeId())) {
			customerInfo.setCurrencyTypeId(null);
			customerInfo.setCurrencyType(null);
		}
		if (customerInfo.getDocumentTypeId() != null && 
				"".equals(customerInfo.getDocumentTypeId())) {
			customerInfo.setDocumentTypeId(null);
			customerInfo.setDocumentType(null);
		}
		result = customerService.save(customer,customerInfo);
		return JSON;
	}
	
	public String view(){
		Filter filter = new Filter(SynthesisCustomer.class);
		filter.eq("id", id);
		filter.createAlias("customerInfo", "customerInfo");
		result = customerService.getBeanByFilter(filter);
		return VIEW;
	}
	
	public String edit(){
		Filter filter = new Filter(SynthesisCustomer.class);
		filter.eq("id", id);
		filter.createAlias("customerInfo", "customerInfo");
		result = customerService.getBeanByFilter(filter);
		return EDIT;
	}
	
	public String update(){
		Filter filter = new Filter(SynthesisCustomer.class);
		filter.eq("id", id);
		filter.createAlias("customerInfo", "customerInfo");
		SynthesisCustomer dbCustomer = customerService.getBeanByFilter(filter).getValue();
		SynthesisCustomerInfo dbcCustomerInfo = dbCustomer.getCustomerInfo();
		BeanUtil.copyProperties(customer, dbCustomer);
		BeanUtil.copyProperties(customerInfo, dbcCustomerInfo);
		if ((customer.getSourceId() != null && //
				"".equals(customer.getSourceId()) || //
				customer.getSourceId() == null)) {
			dbCustomer.setSourceId(null);
			dbCustomer.setSource(null);
		}
		if ((customer.getTechnicId() != null && //
				"".equals(customer.getTechnicId()) || //
				customer.getTechnicId() == null)) {
			dbCustomer.setTechnicId(null);
			dbCustomer.setTechnic(null);
		}
		if (customer.getType() != null && //
				"".equals(customer.getType())) {
			dbCustomer.setType(null);
		}
		if (customerInfo.getRegTypeId() != null && 
				"".equals(customerInfo.getRegTypeId())) {
			dbcCustomerInfo.setRegTypeId(null);
			dbcCustomerInfo.setRegType(null);
		}
		if (customerInfo.getCurrencyTypeId() != null && 
				"".equals(customerInfo.getCurrencyTypeId())) {
			dbcCustomerInfo.setCurrencyTypeId(null);
			dbcCustomerInfo.setCurrencyType(null);
		}
		if (customerInfo.getDocumentTypeId() != null && 
				"".equals(customerInfo.getDocumentTypeId())) {
			dbcCustomerInfo.setDocumentTypeId(null);
			dbcCustomerInfo.setDocumentType(null);
		}
		result = customerService.update(dbCustomer,dbcCustomerInfo);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = customerService.deleteById(id);
		} else if(ids!=null){
			result = customerService.deleteByIds(ids);
		}
		return JSON;
	}
	
	
	public String simpleList() {
		Filter filter = new Filter(SynthesisCustomer.class);
		filter.createAlias("customerInfo","customerInfo");
		if (customerType != null) {
			filter.eq("customerType", customerType);
		}
		String[] includes = {"id","name"};
		filter.include(includes);
		return refresh(filter);
	}
	
	public String list(){
		Filter filter = new Filter(SynthesisCustomer.class);
		filter.createAlias("customerInfo","customerInfo");
		if (customerType != null) {
			filter.eq("customerType", customerType);
		}
		String[] includes = {"id","name","shortName","code","type"};
		filter.include(includes);
		return refresh(filter);
	}
	
	@Override
	protected List<SynthesisCustomer> getListByFilter(Filter fitler) {
		return customerService.getListByFilter(fitler).getValue();
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

	public Result getResult() {
		return result;
	}

	public void setCustomerType(CustomerSupplierTypeEnum customerType) {
		this.customerType = customerType;
	}

	public SynthesisCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(SynthesisCustomer customer) {
		this.customer = customer;
	}

	public SynthesisCustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(SynthesisCustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public void setCustomerService(SynthesisCustomerService customerService) {
		this.customerService = customerService;
	}
	
}
