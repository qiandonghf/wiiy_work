package com.wiiy.engineering.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.engineering.entity.EngineeringContract;
import com.wiiy.engineering.service.EngineeringContractService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public class ContractAction extends JqGridBaseAction<EngineeringContract>{
	
	private EngineeringContractService contractService;
	private Result result;
	private EngineeringContract contract;
	private Long id;
	private String ids;
	
	public String generateCode() {
		result = contractService.generateCode();
		return JSON;
	}
	
	public String save(){
		if ((contract.getCategoryId() != null && //
				"".equals(contract.getCategoryId()) || //
				contract.getCategoryId() == null)) {
			contract.setCategoryId(null);
			contract.setCategory(null);
		}
		if (contract.getContractForm() != null && //
				"".equals(contract.getContractForm())) {
			contract.setContractForm(null);
		}
		if (contract.getContractStatus() != null && //
				"".equals(contract.getContractStatus())) {
			contract.setContractStatus(null);
		}
		if ((contract.getCurrencyTypeId() != null &&//
				"".equals(contract.getCurrencyTypeId()))||//
				contract.getCurrencyTypeId() == null) {
			contract.setCurrencyTypeId(null);
			contract.setCurrencyType(null);
		}
		if (contract.getPayment() != null && 
				"".equals(contract.getPayment())) {
			contract.setPayment(null);
		}
		if (contract.getSettlement() != null&& //
				"".equals(contract.getSettlement())) {
			contract.setSettlement(null);
		}
		if (contract.getAudit() == null) {
			contract.setAudit(BooleanEnum.NO);
		}
		if (contract.getFinished() == null) {
			contract.setFinished(BooleanEnum.NO);
		}
		if (contract.getPublished() == null) {
			contract.setPublished(BooleanEnum.NO);
		}
		result = contractService.save(contract);
		return JSON;
	}
	
	public String view(){
		result = contractService.getBeanById(id);
		return VIEW;
	}
	
	public String viewById(){
		return "viewById";
	}
	
	public String edit(){
		result = contractService.getBeanById(id);
		return EDIT;
	}
	
	public String editById(){
		return "editById";
	}
	
	public String update(){
		EngineeringContract dbBean = contractService.getBeanById(contract.getId()).getValue();
		BeanUtil.copyProperties(contract, dbBean);
		if ((contract.getCategoryId() != null && //
				"".equals(contract.getCategoryId()) || //
				contract.getCategoryId() == null)) {
			dbBean.setCategoryId(null);
			dbBean.setCategory(null);
		}
		if (contract.getContractForm() != null && //
				"".equals(contract.getContractForm())) {
			dbBean.setContractForm(null);
		}
		if (contract.getContractStatus() != null && //
				"".equals(contract.getContractStatus())) {
			dbBean.setContractStatus(null);
		}
		if ((contract.getCurrencyTypeId() != null &&//
				"".equals(contract.getCurrencyTypeId()))||//
				contract.getCurrencyTypeId() == null) {
			dbBean.setCurrencyTypeId(null);
			dbBean.setCurrencyType(null);
		}
		if (contract.getPayment() != null && 
				"".equals(contract.getPayment())) {
			dbBean.setPayment(null);
		}
		if (contract.getSettlement() != null&& //
				"".equals(contract.getSettlement())) {
			dbBean.setSettlement(null);
		}
		if (contract.getAudit() == null) {
			dbBean.setAudit(BooleanEnum.NO);
		}
		if (contract.getFinished() == null) {
			dbBean.setFinished(BooleanEnum.NO);
		}
		if (contract.getPublished() == null) {
			dbBean.setPublished(BooleanEnum.NO);
		}
		result = contractService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = contractService.deleteById(id);
		} else if(ids!=null){
			result = contractService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String loadContractData(){
		Filter filter = new Filter(EngineeringContract.class);
		filter.include("id").include("name").include("signDate");
		if (id != null) {
			filter.eq("projectId", id);
		}
		filter.orderBy("signDate", Filter.ASC);
		result = contractService.getListByFilter(filter);
		return JSON;	
	}
	
	public String list(){
		Filter filter = new Filter(EngineeringContract.class);
		if (id != null) {
			filter.eq("projectId", id);
		}
		return refresh(filter);	
	}
	
	@Override
	protected List<EngineeringContract> getListByFilter(Filter fitler) {
		return contractService.getListByFilter(fitler).getValue();
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

	public void setContractService(EngineeringContractService contractService) {
		this.contractService = contractService;
	}

	public EngineeringContract getContract() {
		return contract;
	}

	public void setContract(EngineeringContract contract) {
		this.contract = contract;
	}
	
}
