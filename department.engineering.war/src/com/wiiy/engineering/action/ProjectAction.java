package com.wiiy.engineering.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.engineering.entity.EngineeringProject;
import com.wiiy.engineering.service.EngineeringProjectService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
/**
 * @author my
 */
public class ProjectAction extends JqGridBaseAction<EngineeringProject>{
	
	private EngineeringProjectService projectService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private EngineeringProject engineering;
	private Long id;
	private String ids;
	
	public String generateCode() {
		result = projectService.generateCode();
		return JSON;
	}
	
	public String contractAdd() {
		Filter filter = new Filter(EngineeringProject.class);
		filter.include("id").include("name");
		result = projectService.getBeanByFilter(filter);
		return "contractAdd";
	}
	
	public String save(){
		if (engineering.getStatus() != null && "".equals(engineering.getStatus())) {
			engineering.setStatus(null);
		}
		if (engineering.getPriority() != null && "".equals(engineering.getPriority())) {
			engineering.setPriority(null);
		}
		if (engineering.getCurrencyTypeId() != null && "".equals(engineering.getCurrencyTypeId())) {
			engineering.setCurrencyTypeId(null);
			engineering.setCurrencyType(null);
		}
		if (engineering.getPayment() != null && "".equals(engineering.getPayment())) {
			engineering.setPayment(null);
		}
		if (engineering.getSettlement() != null && "".equals(engineering.getSettlement())) {
			engineering.setSettlement(null);
		}
		if (engineering.getAudit() == null) {
			engineering.setAudit(BooleanEnum.NO);
		}
		if (engineering.getFinished() == null) {
			engineering.setFinished(BooleanEnum.NO);
		}
		if (engineering.getPublished() == null) {
			engineering.setPublished(BooleanEnum.NO);
		}
		result = projectService.save(engineering);
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
		EngineeringProject dbBean = projectService.getBeanById(engineering.getId()).getValue();
		BeanUtil.copyProperties(engineering, dbBean);
		if (engineering.getStatus() != null && //
				"".equals(engineering.getStatus())) {
			dbBean.setStatus(null);
		}
		if (engineering.getPriority() != null && //
				"".equals(engineering.getPriority())) {
			dbBean.setPriority(null);
		}
		if (engineering.getCurrencyTypeId() != null && //
				"".equals(engineering.getCurrencyTypeId())) {
			dbBean.setCurrencyTypeId(null);
			dbBean.setCurrencyType(null);
		}
		if (engineering.getPayment() != null && //
				"".equals(engineering.getPayment())) {
			dbBean.setPayment(null);
		}
		if (engineering.getSettlement() != null && //
				"".equals(engineering.getSettlement())) {
			dbBean.setSettlement(null);
		}
		if (engineering.getAudit() == null) dbBean.setAudit(BooleanEnum.NO);
		if (engineering.getFinished() == null) dbBean.setFinished(BooleanEnum.NO);
		if (engineering.getPublished() == null) dbBean.setPublished(BooleanEnum.NO);
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
		Filter filter = new Filter(EngineeringProject.class);
		filter.include("id").include("name");
		return refresh(filter);
	}
	
	public String loadProjectData() {
		Filter filter = new Filter(EngineeringProject.class);
		String[] includes = {"id","name","amount","createTime"//
				,"startDate","schedules"};
		filter.include(includes);
		filter.orderBy("createTime", Filter.DESC).maxResults(2);
		root = (List<EngineeringProject>) projectService.getListByFilter(filter).getValue();
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(EngineeringProject.class));
	}
	
	@Override
	protected List<EngineeringProject> getListByFilter(Filter fitler) {
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

	public EngineeringProject getEngineering() {
		return engineering;
	}

	public void setEngineering(EngineeringProject engineering) {
		this.engineering = engineering;
	}

	public void setProjectService(EngineeringProjectService projectService) {
		this.projectService = projectService;
	}
	
}
