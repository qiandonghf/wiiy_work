package com.wiiy.engineering.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.engineering.entity.EngineeringPlan;
import com.wiiy.engineering.entity.EngineeringProject;
import com.wiiy.engineering.service.EngineeringPlanService;
import com.wiiy.engineering.service.EngineeringProjectService;

/**
 * @author my
 */
public class PlanAction extends JqGridBaseAction<EngineeringPlan>{
	
	private EngineeringPlanService planService;
	private EngineeringProjectService projectService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private EngineeringPlan plan;
	private Long id;
	private String ids;
	
	
	public String findPlanById(){
		Filter filter = new Filter(EngineeringPlan.class);
		filter.eq("id", id);
		filter.include("time").include("schedule");
		result = planService.getBeanByFilter(filter);
		return JSON;
	}
	
	public String findPlansByProjectId(){
		Filter filter = new Filter(EngineeringPlan.class);
		filter.eq("projectId", id);
		filter.include("time").include("schedule");
		filter.orderBy("time", Filter.ASC);
		result = planService.getListByFilter(filter);
		return JSON;
	}
	
	public String generateCode() {
		result = planService.generateCode();
		return JSON;
	}
	
	public String add() {
		Filter filter = new Filter(EngineeringProject.class);
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
		EngineeringPlan dbBean = planService.getBeanById(plan.getId()).getValue();
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
		Filter filter = new Filter(EngineeringPlan.class);
		filter.include("id").include("code");
		return refresh(filter);
	}
	
	public String list(){
		return refresh(new Filter(EngineeringPlan.class));
	}
	
	@Override
	protected List<EngineeringPlan> getListByFilter(Filter fitler) {
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

	public void setPlanService(EngineeringPlanService planService) {
		this.planService = planService;
	}

	public void setProjectService(EngineeringProjectService projectService) {
		this.projectService = projectService;
	}

	public EngineeringPlan getPlan() {
		return plan;
	}

	public void setPlan(EngineeringPlan plan) {
		this.plan = plan;
	}
	
}
