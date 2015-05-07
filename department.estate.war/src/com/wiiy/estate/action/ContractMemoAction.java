package com.wiiy.estate.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.entity.EstateContractMemo;
import com.wiiy.estate.service.ContractMemoService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public class ContractMemoAction extends JqGridBaseAction<EstateContractMemo>{
	
	private ContractMemoService contractMemoService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private EstateContractMemo contractMemo;
	private Long id;
	private String ids;
	
	
	public String save(){
		result = contractMemoService.save(contractMemo);
		return JSON;
	}
	
	public String view(){
		result = contractMemoService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = contractMemoService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		EstateContractMemo dbBean = contractMemoService.getBeanById(contractMemo.getId()).getValue();
		BeanUtil.copyProperties(contractMemo, dbBean);
		result = contractMemoService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = contractMemoService.deleteById(id);
		} else if(ids!=null){
			result = contractMemoService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(EstateContractMemo.class));
	}
	
	@Override
	protected List<EstateContractMemo> getListByFilter(Filter fitler) {
		return contractMemoService.getListByFilter(fitler).getValue();
	}
	
	
	public EstateContractMemo getContractMemo() {
		return contractMemo;
	}

	public void setContractMemo(EstateContractMemo contractMemo) {
		this.contractMemo = contractMemo;
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

	public void setContractMemoService(ContractMemoService contractMemoService) {
		this.contractMemoService = contractMemoService;
	}
	
	@SuppressWarnings("rawtypes")
	public Result getResult() {
		return result;
	}
	
}
