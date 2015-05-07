package com.wiiy.sale.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.sale.entity.SaleFact;
import com.wiiy.sale.entity.SaleProject;
import com.wiiy.sale.service.SaleFactService;
import com.wiiy.sale.service.SaleProjectService;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public class FactAction extends JqGridBaseAction<SaleFact>{
	
	private SaleFactService factService;
	private SaleProjectService projectService;
	private Result result;
	private SaleFact fact;
	private Long id;
	private String ids;
	
	public String add() {
		Filter filter = new Filter(SaleProject.class);
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
		SaleFact dbBean = factService.getBeanById(fact.getId()).getValue();
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
		return refresh(new Filter(SaleFact.class));
	}
	
	@Override
	protected List<SaleFact> getListByFilter(Filter fitler) {
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

	public void setProjectService(SaleProjectService projectService) {
		this.projectService = projectService;
	}

	public void setFactService(SaleFactService factService) {
		this.factService = factService;
	}

	public SaleFact getFact() {
		return fact;
	}

	public void setFact(SaleFact fact) {
		this.fact = fact;
	}
	
}
