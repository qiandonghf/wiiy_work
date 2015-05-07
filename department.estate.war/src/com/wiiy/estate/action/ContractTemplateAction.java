package com.wiiy.estate.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.entity.EstateContractTemplate;
import com.wiiy.estate.service.ContractTemplateService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public class ContractTemplateAction extends JqGridBaseAction<EstateContractTemplate>{
	
	private ContractTemplateService contractTemplateService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private EstateContractTemplate contractTemplate;
	private Long id;
	private String ids;
	
	
	public String save(){
		result = contractTemplateService.save(contractTemplate);
		return JSON;
	}
	
	public String view(){
		result = contractTemplateService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = contractTemplateService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		EstateContractTemplate dbBean = contractTemplateService.getBeanById(contractTemplate.getId()).getValue();
		BeanUtil.copyProperties(contractTemplate, dbBean);
		result = contractTemplateService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = contractTemplateService.deleteById(id);
		} else if(ids!=null){
			result = contractTemplateService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(EstateContractTemplate.class));
	}
	
	@Override
	protected List<EstateContractTemplate> getListByFilter(Filter fitler) {
		return contractTemplateService.getListByFilter(fitler).getValue();
	}
	
	
	public EstateContractTemplate getEstateContractTemplate() {
		return contractTemplate;
	}

	public void setEstateContractTemplate(EstateContractTemplate contractTemplate) {
		this.contractTemplate = contractTemplate;
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

	public void setEstateContractTemplateService(ContractTemplateService contractTemplateService) {
		this.contractTemplateService = contractTemplateService;
	}
	
	@SuppressWarnings("rawtypes")
	public Result getResult() {
		return result;
	}
	
}
