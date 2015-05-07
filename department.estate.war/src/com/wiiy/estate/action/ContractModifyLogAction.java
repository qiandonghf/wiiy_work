package com.wiiy.estate.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.entity.EstateContractModifyLog;
import com.wiiy.estate.service.ContractModifyLogService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public class ContractModifyLogAction extends JqGridBaseAction<EstateContractModifyLog>{
	
	private ContractModifyLogService contractModifyLogService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private EstateContractModifyLog contractModifyLog;
	private Long id;
	private String ids;
	
	
	public String save(){
		result = contractModifyLogService.save(contractModifyLog);
		return JSON;
	}
	
	public String view(){
		result = contractModifyLogService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = contractModifyLogService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		EstateContractModifyLog dbBean = contractModifyLogService.getBeanById(contractModifyLog.getId()).getValue();
		BeanUtil.copyProperties(contractModifyLog, dbBean);
		result = contractModifyLogService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = contractModifyLogService.deleteById(id);
		} else if(ids!=null){
			result = contractModifyLogService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(EstateContractModifyLog.class));
	}
	
	@Override
	protected List<EstateContractModifyLog> getListByFilter(Filter fitler) {
		return contractModifyLogService.getListByFilter(fitler).getValue();
	}
	
	
	public EstateContractModifyLog getContractModifyLog() {
		return contractModifyLog;
	}

	public void setContractModifyLog(EstateContractModifyLog contractModifyLog) {
		this.contractModifyLog = contractModifyLog;
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

	public void setContractModifyLogService(ContractModifyLogService contractModifyLogService) {
		this.contractModifyLogService = contractModifyLogService;
	}
	
	@SuppressWarnings("rawtypes")
	public Result getResult() {
		return result;
	}
	
}
