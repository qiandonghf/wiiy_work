package com.wiiy.synthesis.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.synthesis.entity.SynthesisPlan;
import com.wiiy.synthesis.entity.SynthesisProject;
import com.wiiy.synthesis.service.SynthesisPlanService;
import com.wiiy.synthesis.service.SynthesisProjectService;

/**
 * @author my
 */
public class PlanAction extends JqGridBaseAction<SynthesisPlan>{
	
	private SynthesisPlanService planService;
	private SynthesisProjectService projectService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private SynthesisPlan plan;
	private Long id;
	private String ids;
	
	
	public String findPlanById(){
		Filter filter = new Filter(SynthesisPlan.class);
		filter.eq("id", id);
		filter.include("time").include("schedule");
		result = planService.getBeanByFilter(filter);
		return JSON;
	}
	
	public String generateCode() {
		result = planService.generateCode();
		return JSON;
	}
	
	public String add() {
		Filter filter = new Filter(SynthesisProject.class);
		filter.eq("id", id);
		filter.include("id").include("schedules");
		result = projectService.getBeanByFilter(filter);
		return "add";
	}
	
	public String save(){
		result = planService.save(plan);
		return JSON;
	}
	
	public String view(){
		result = planService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = planService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		SynthesisPlan dbBean = planService.getBeanById(plan.getId()).getValue();
		BeanUtil.copyProperties(plan, dbBean);
		result = planService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = planService.deleteById(id);
		} else if(ids!=null){
			result = planService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String simpleList() {
		Filter filter = new Filter(SynthesisPlan.class);
		filter.include("id").include("code");
		return refresh(filter);
	}
	
	public String list(){
		return refresh(new Filter(SynthesisPlan.class));
	}
	
	@Override
	protected List<SynthesisPlan> getListByFilter(Filter fitler) {
		return planService.getListByFilter(fitler).getValue();
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

	public void setPlanService(SynthesisPlanService planService) {
		this.planService = planService;
	}

	public void setProjectService(SynthesisProjectService projectService) {
		this.projectService = projectService;
	}

	public SynthesisPlan getPlan() {
		return plan;
	}

	public void setPlan(SynthesisPlan plan) {
		this.plan = plan;
	}
	
}
