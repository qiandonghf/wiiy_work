package com.wiiy.estate.action;

import java.util.List;

import com.wiiy.estate.entity.EstateContractAtt;
import com.wiiy.estate.service.ContractAttService;
import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public class ContractAttAction extends JqGridBaseAction<EstateContractAtt>{
	
	private ContractAttService contractAttService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private EstateContractAtt contractAtt;
	private Long id;
	private String ids;
	
	
	public String save(){
		result = contractAttService.save(contractAtt);
		return JSON;
	}
	
	public String view(){
		result = contractAttService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = contractAttService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		EstateContractAtt dbBean = contractAttService.getBeanById(contractAtt.getId()).getValue();
		contractAtt.setName(contractAtt.getName()+dbBean.getNewName().substring(dbBean.getNewName().lastIndexOf(".")));
		BeanUtil.copyProperties(contractAtt, dbBean);
		result = contractAttService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = contractAttService.deleteById(id);
		} else if(ids!=null){
			result = contractAttService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(EstateContractAtt.class));
	}
	
	@Override
	protected List<EstateContractAtt> getListByFilter(Filter fitler) {
		return contractAttService.getListByFilter(fitler).getValue();
	}
	
	
	public EstateContractAtt getContractAtt() {
		return contractAtt;
	}

	public void setContractAtt(EstateContractAtt contractAtt) {
		this.contractAtt = contractAtt;
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

	public void setContractAttService(ContractAttService contractAttService) {
		this.contractAttService = contractAttService;
	}
	
	@SuppressWarnings("rawtypes")
	public Result getResult() {
		return result;
	}
	
}
