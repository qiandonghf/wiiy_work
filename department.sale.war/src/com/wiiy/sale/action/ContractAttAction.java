package com.wiiy.sale.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.sale.entity.SaleContractAtt;
import com.wiiy.sale.service.SaleContractAttService;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public class ContractAttAction extends JqGridBaseAction<SaleContractAtt>{
	
	private SaleContractAttService contractAttService;
	private Result result;
	private SaleContractAtt contractAtt;
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
		SaleContractAtt dbBean = contractAttService.getBeanById(contractAtt.getId()).getValue();
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
		Filter filter = new Filter(SaleContractAtt.class);
		if (id != null) {
			filter.eq("contractId", id);
		}
		return refresh(filter);
	}
	
	@Override
	protected List<SaleContractAtt> getListByFilter(Filter fitler) {
		return contractAttService.getListByFilter(fitler).getValue();
	}
	
	
	public SaleContractAtt getContractAtt() {
		return contractAtt;
	}

	public void setContractAtt(SaleContractAtt contractAtt) {
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

	public void setContractAttService(SaleContractAttService contractAttService) {
		this.contractAttService = contractAttService;
	}
	
	public Result getResult() {
		return result;
	}
	
}
