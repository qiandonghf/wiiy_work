package com.wiiy.synthesis.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.synthesis.entity.SynthesisContractAtt;
import com.wiiy.synthesis.service.SynthesisContractAttService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public class ContractAttAction extends JqGridBaseAction<SynthesisContractAtt>{
	
	private SynthesisContractAttService contractAttService;
	private Result result;
	private SynthesisContractAtt contractAtt;
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
		SynthesisContractAtt dbBean = contractAttService.getBeanById(contractAtt.getId()).getValue();
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
		Filter filter = new Filter(SynthesisContractAtt.class);
		if (id != null) {
			filter.eq("contractId", id);
		}
		return refresh(filter);
	}
	
	@Override
	protected List<SynthesisContractAtt> getListByFilter(Filter fitler) {
		return contractAttService.getListByFilter(fitler).getValue();
	}
	
	
	public SynthesisContractAtt getContractAtt() {
		return contractAtt;
	}

	public void setContractAtt(SynthesisContractAtt contractAtt) {
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

	public void setContractAttService(SynthesisContractAttService contractAttService) {
		this.contractAttService = contractAttService;
	}
	
	public Result getResult() {
		return result;
	}
	
}
