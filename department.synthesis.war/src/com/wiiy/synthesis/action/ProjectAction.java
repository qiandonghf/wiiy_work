package com.wiiy.synthesis.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.SynthesisProject;
import com.wiiy.synthesis.service.SynthesisProjectService;
/**
 * @author my
 */
public class ProjectAction extends JqGridBaseAction<SynthesisProject>{
	
	private SynthesisProjectService projectService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private SynthesisProject synthesis;
	private Long id;
	private String ids;
	
	public String generateCode() {
		result = projectService.generateCode();
		return JSON;
	}
	
	public String contractAdd() {
		Filter filter = new Filter(SynthesisProject.class);
		filter.include("id").include("name");
		result = projectService.getBeanByFilter(filter);
		return "contractAdd";
	}
	
	public String save(){
		if (synthesis.getStatus() != null && "".equals(synthesis.getStatus())) {
			synthesis.setStatus(null);
		}
		if (synthesis.getPriority() != null && "".equals(synthesis.getPriority())) {
			synthesis.setPriority(null);
		}
		if (synthesis.getCurrencyTypeId() != null && "".equals(synthesis.getCurrencyTypeId())) {
			synthesis.setCurrencyTypeId(null);
			synthesis.setCurrencyType(null);
		}
		if (synthesis.getPayment() != null && "".equals(synthesis.getPayment())) {
			synthesis.setPayment(null);
		}
		if (synthesis.getSettlement() != null && "".equals(synthesis.getSettlement())) {
			synthesis.setSettlement(null);
		}
		if (synthesis.getAudit() == null) {
			synthesis.setAudit(BooleanEnum.NO);
		}
		if (synthesis.getFinished() == null) {
			synthesis.setFinished(BooleanEnum.NO);
		}
		if (synthesis.getPublished() == null) {
			synthesis.setPublished(BooleanEnum.NO);
		}
		result = projectService.save(synthesis);
		return JSON;
	}
	
	public String view(){
		result = projectService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = projectService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		SynthesisProject dbBean = projectService.getBeanById(synthesis.getId()).getValue();
		BeanUtil.copyProperties(synthesis, dbBean);
		if (synthesis.getStatus() != null && //
				"".equals(synthesis.getStatus())) {
			dbBean.setStatus(null);
		}
		if (synthesis.getPriority() != null && //
				"".equals(synthesis.getPriority())) {
			dbBean.setPriority(null);
		}
		if (synthesis.getCurrencyTypeId() != null && //
				"".equals(synthesis.getCurrencyTypeId())) {
			dbBean.setCurrencyTypeId(null);
			dbBean.setCurrencyType(null);
		}
		if (synthesis.getPayment() != null && //
				"".equals(synthesis.getPayment())) {
			dbBean.setPayment(null);
		}
		if (synthesis.getSettlement() != null && //
				"".equals(synthesis.getSettlement())) {
			dbBean.setSettlement(null);
		}
		if (synthesis.getAudit() == null) dbBean.setAudit(BooleanEnum.NO);
		if (synthesis.getFinished() == null) dbBean.setFinished(BooleanEnum.NO);
		if (synthesis.getPublished() == null) dbBean.setPublished(BooleanEnum.NO);
		result = projectService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = projectService.deleteById(id);
		} else if(ids!=null){
			result = projectService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String simpleList() {
		Filter filter = new Filter(SynthesisProject.class);
		filter.include("id").include("name");
		return refresh(filter);
	}
	
	public String list(){
		return refresh(new Filter(SynthesisProject.class));
	}
	
	
	@Override
	protected List<SynthesisProject> getListByFilter(Filter fitler) {
		return projectService.getListByFilter(fitler).getValue();
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
	
	@SuppressWarnings("rawtypes")
	public Result getResult() {
		return result;
	}

	public SynthesisProject getSynthesis() {
		return synthesis;
	}

	public void setSynthesis(SynthesisProject synthesis) {
		this.synthesis = synthesis;
	}

	public void setProjectService(SynthesisProjectService projectService) {
		this.projectService = projectService;
	}
	
}
