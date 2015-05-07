package com.wiiy.sale.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.common.preferences.enums.ContractTypeEnum;
import com.wiiy.sale.entity.SaleContract;
import com.wiiy.sale.service.SaleContractService;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public class ContractAction extends JqGridBaseAction<SaleContract>{
	
	private SaleContractService contractService;
	private Result result;
	private SaleContract contract;
	private Long id;
	private String ids;
	private ContractTypeEnum type; 
	
	public String generateCode() {
		result = contractService.generateCode();
		return JSON;
	}
	
	public String save(){
		if (contract.getContractType() != null && //
				"".equals(contract.getContractType())) {
			contract.setContractType(null);
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
		SaleContract dbBean = contractService.getBeanById(contract.getId()).getValue();
		BeanUtil.copyProperties(contract, dbBean);
		if (contract.getContractType() != null && //
				"".equals(contract.getContractType())) {
			dbBean.setContractType(null);
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
	
	public String list(){
		Filter filter = new Filter(SaleContract.class);
		if (id != null) {
			filter.eq("projectId", id);
		}
		if (type != null) {
			filter.eq("contractType", type);
		}
		return refresh(filter);
	}
	
	@Override
	protected List<SaleContract> getListByFilter(Filter fitler) {
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

	public void setContractService(SaleContractService contractService) {
		this.contractService = contractService;
	}

	public SaleContract getContract() {
		return contract;
	}

	public void setContract(SaleContract contract) {
		this.contract = contract;
	}

	public ContractTypeEnum getType() {
		return type;
	}

	public void setType(ContractTypeEnum type) {
		this.type = type;
	}
	
}
