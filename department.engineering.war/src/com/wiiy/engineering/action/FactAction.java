package com.wiiy.engineering.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.engineering.entity.EngineeringFact;
import com.wiiy.engineering.entity.EngineeringPlan;
import com.wiiy.engineering.entity.EngineeringProject;
import com.wiiy.engineering.service.EngineeringFactService;
import com.wiiy.engineering.service.EngineeringProjectService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public class FactAction extends JqGridBaseAction<EngineeringFact>{
	
	private EngineeringFactService factService;
	private EngineeringProjectService projectService;
	private Result result;
	private EngineeringFact fact;
	private Long id;
	private String ids;
	
	public String add() {
		Filter filter = new Filter(EngineeringProject.class);
		filter.eq("id", id);
		filter.include("id").include("schedules");
		result = projectService.getBeanByFilter(filter);
		return "add";
	}
	
	public String findFactsByProjectId(){
		Filter filter = new Filter(EngineeringFact.class);
		filter.eq("projectId", id);
		filter.include("time").include("schedule");
		filter.orderBy("time", Filter.ASC);
		result = factService.getListByFilter(filter);
		return JSON;
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
		EngineeringFact dbBean = factService.getBeanById(fact.getId()).getValue();
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
		return refresh(new Filter(EngineeringFact.class));
	}
	
	@Override
	protected List<EngineeringFact> getListByFilter(Filter fitler) {
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

	public void setProjectService(EngineeringProjectService projectService) {
		this.projectService = projectService;
	}

	public void setFactService(EngineeringFactService factService) {
		this.factService = factService;
	}

	public EngineeringFact getFact() {
		return fact;
	}

	public void setFact(EngineeringFact fact) {
		this.fact = fact;
	}
	
}
