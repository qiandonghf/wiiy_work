package com.wiiy.synthesis.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.synthesis.entity.SynthesisFact;
import com.wiiy.synthesis.entity.SynthesisProject;
import com.wiiy.synthesis.service.SynthesisFactService;
import com.wiiy.synthesis.service.SynthesisProjectService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public class FactAction extends JqGridBaseAction<SynthesisFact>{
	
	private SynthesisFactService factService;
	private SynthesisProjectService projectService;
	private Result result;
	private SynthesisFact fact;
	private Long id;
	private String ids;
	
	public String add() {
		Filter filter = new Filter(SynthesisProject.class);
		filter.eq("id", id);
		filter.include("id").include("schedules");
		result = projectService.getBeanByFilter(filter);
		return "add";
	}
	
	public String save(){
		result = factService.save(fact);
		return JSON;
	}
	
	public String view(){
		result = factService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = factService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		SynthesisFact dbBean = factService.getBeanById(fact.getId()).getValue();
		BeanUtil.copyProperties(fact, dbBean);
		result = factService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = factService.deleteById(id);
		} else if(ids!=null){
			result = factService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(SynthesisFact.class));
	}
	
	@Override
	protected List<SynthesisFact> getListByFilter(Filter fitler) {
		return factService.getListByFilter(fitler).getValue();
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

	public void setProjectService(SynthesisProjectService projectService) {
		this.projectService = projectService;
	}

	public void setFactService(SynthesisFactService factService) {
		this.factService = factService;
	}

	public SynthesisFact getFact() {
		return fact;
	}

	public void setFact(SynthesisFact fact) {
		this.fact = fact;
	}
	
}
