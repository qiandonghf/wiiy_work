package com.wiiy.estate.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.entity.EstateContractAgreementAtt;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.service.ContractAgreementAttService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public class ContractAgreementAttAction extends JqGridBaseAction<EstateContractAgreementAtt>{
	
	private ContractAgreementAttService contractAgreementAttService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private EstateContractAgreementAtt contractAgreementAtt;
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
	
	public String saveToSession(){
		if(getSessionContractAgreementAttList()==null){
			List<EstateContractAgreementAtt> contractAgreementAttList = new ArrayList<EstateContractAgreementAtt>();
			getSession().setAttribute("contractAgreementAttList", contractAgreementAttList);
		}
		contractAgreementAtt.setId((new Date()).getTime());
		getSessionContractAgreementAttList().add(contractAgreementAtt);
		result = Result.success(R.EstateContractAgreementAtt.SAVE_SUCCESS,contractAgreementAtt);
		return JSON;
	}
	
	public String deleteFromSession(){
		List<EstateContractAgreementAtt> list = getSessionContractAgreementAttList();
		EstateContractAgreementAtt delete = null;
		if(list!=null){
			for (EstateContractAgreementAtt task : list) {
				if(task.getId().longValue()==id.longValue()){
					delete = task;
					break;
				}
			}
			if(delete!=null){
				EstateActivator.getResourcesService().delete(delete.getNewName());
				list.remove(delete);
			}
		}
		result = Result.success(R.EstateContractAgreementAtt.DELETE_SUCCESS);
		return JSON;
	}
	
	
	public String save(){
		result = contractAgreementAttService.save(contractAgreementAtt);
		return JSON;
	}
	
	public String view(){
		result = contractAgreementAttService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = contractAgreementAttService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		EstateContractAgreementAtt dbBean = contractAgreementAttService.getBeanById(contractAgreementAtt.getId()).getValue();
		BeanUtil.copyProperties(contractAgreementAtt, dbBean);
		result = contractAgreementAttService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = contractAgreementAttService.deleteById(id);
		} else if(ids!=null){
			result = contractAgreementAttService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(EstateContractAgreementAtt.class));
	}
	
	@Override
	protected List<EstateContractAgreementAtt> getListByFilter(Filter fitler) {
		return contractAgreementAttService.getListByFilter(fitler).getValue();
	}
	
	
	public EstateContractAgreementAtt getContractAgreementAtt() {
		return contractAgreementAtt;
	}

	public void setContractAgreementAtt(EstateContractAgreementAtt contractAgreementAtt) {
		this.contractAgreementAtt = contractAgreementAtt;
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

	public void setContractAgreementAttService(ContractAgreementAttService contractAgreementAttService) {
		this.contractAgreementAttService = contractAgreementAttService;
	}
	
	@SuppressWarnings("rawtypes")
	public Result getResult() {
		return result;
	}
	
}
