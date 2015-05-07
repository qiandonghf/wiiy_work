package com.wiiy.estate.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.entity.EstateContractAgreement;
import com.wiiy.estate.entity.EstateContractAgreementAtt;
import com.wiiy.estate.service.ContractAgreementService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public class ContractAgreementAction extends JqGridBaseAction<EstateContractAgreement>{
	
	private ContractAgreementService contractAgreementService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private EstateContractAgreement contractAgreement;
	private Long id;
	private String ids;
	
	private HttpSession getSession(){
		return ServletActionContext.getRequest().getSession();
	}
	@SuppressWarnings("unchecked")
	private List<EstateContractAgreementAtt> getSessionContractAgreementAttList(){
		HttpSession session = getSession();
		return (List<EstateContractAgreementAtt>) session.getAttribute("contractAgreementAttList");
	}
	
	public String save(){
		result = contractAgreementService.save(contractAgreement,getSessionContractAgreementAttList());
		return JSON;
	}
	
	public String view(){
		getSession().removeAttribute("contractAgreementAttList");
		contractAgreement = contractAgreementService.getBeanById(id).getValue();
		result = Result.value(contractAgreement);
		getSession().setAttribute("contractAgreementAttList", new ArrayList<EstateContractAgreementAtt>(contractAgreement.getAtts()));
		return VIEW;
	}
	
	public String edit(){
		getSession().removeAttribute("contractAgreementAttList");
		contractAgreement = contractAgreementService.getBeanById(id).getValue();
		result = Result.value(contractAgreement);
		getSession().setAttribute("contractAgreementAttList", new ArrayList<EstateContractAgreementAtt>(contractAgreement.getAtts()));
		return EDIT;
	}
	
	public String update(){
		EstateContractAgreement dbBean = contractAgreementService.getBeanById(contractAgreement.getId()).getValue();
		BeanUtil.copyProperties(contractAgreement, dbBean);
		result = contractAgreementService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = contractAgreementService.deleteById(id);
		} else if(ids!=null){
			result = contractAgreementService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(EstateContractAgreement.class));
	}
	
	@Override
	protected List<EstateContractAgreement> getListByFilter(Filter fitler) {
		return contractAgreementService.getListByFilter(fitler).getValue();
	}
	
	
	public EstateContractAgreement getContractAgreement() {
		return contractAgreement;
	}

	public void setContractAgreement(EstateContractAgreement contractAgreement) {
		this.contractAgreement = contractAgreement;
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

	public void setContractAgreementService(ContractAgreementService contractAgreementService) {
		this.contractAgreementService = contractAgreementService;
	}
	
	@SuppressWarnings("rawtypes")
	public Result getResult() {
		return result;
	}
	
}
