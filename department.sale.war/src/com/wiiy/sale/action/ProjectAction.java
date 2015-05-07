package com.wiiy.sale.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.sale.entity.SaleProject;
import com.wiiy.sale.service.SaleProjectService;
/**
 * @author my
 */
public class ProjectAction extends JqGridBaseAction<SaleProject>{
	
	private SaleProjectService projectService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private SaleProject sale;
	private Long id;
	private String ids;
	
	public String generateCode() {
		result = projectService.generateCode();
		return JSON;
	}
	
	public String contractAdd() {
		Filter filter = new Filter(SaleProject.class);
		filter.include("id").include("name");
		result = projectService.getBeanByFilter(filter);
		return "contractAdd";
	}
	
	public String save(){
		if (sale.getStatus() != null && "".equals(sale.getStatus())) {
			sale.setStatus(null);
		}
		if (sale.getPriority() != null && "".equals(sale.getPriority())) {
			sale.setPriority(null);
		}
		if (sale.getCurrencyTypeId() != null && "".equals(sale.getCurrencyTypeId())) {
			sale.setCurrencyTypeId(null);
			sale.setCurrencyType(null);
		}
		if (sale.getPayment() != null && "".equals(sale.getPayment())) {
			sale.setPayment(null);
		}
		if (sale.getSettlement() != null && "".equals(sale.getSettlement())) {
			sale.setSettlement(null);
		}
		if (sale.getAudit() == null) {
			sale.setAudit(BooleanEnum.NO);
		}
		if (sale.getFinished() == null) {
			sale.setFinished(BooleanEnum.NO);
		}
		if (sale.getPublished() == null) {
			sale.setPublished(BooleanEnum.NO);
		}
		result = projectService.save(sale);
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
		SaleProject dbBean = projectService.getBeanById(sale.getId()).getValue();
		BeanUtil.copyProperties(sale, dbBean);
		if (sale.getStatus() != null && //
				"".equals(sale.getStatus())) {
			dbBean.setStatus(null);
		}
		if (sale.getPriority() != null && //
				"".equals(sale.getPriority())) {
			dbBean.setPriority(null);
		}
		if (sale.getCurrencyTypeId() != null && //
				"".equals(sale.getCurrencyTypeId())) {
			dbBean.setCurrencyTypeId(null);
			dbBean.setCurrencyType(null);
		}
		if (sale.getPayment() != null && //
				"".equals(sale.getPayment())) {
			dbBean.setPayment(null);
		}
		if (sale.getSettlement() != null && //
				"".equals(sale.getSettlement())) {
			dbBean.setSettlement(null);
		}
		if (sale.getAudit() == null) dbBean.setAudit(BooleanEnum.NO);
		if (sale.getFinished() == null) dbBean.setFinished(BooleanEnum.NO);
		if (sale.getPublished() == null) dbBean.setPublished(BooleanEnum.NO);
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
		Filter filter = new Filter(SaleProject.class);
		filter.include("id").include("name");
		return refresh(filter);
	}
	
	public String list(){
		return refresh(new Filter(SaleProject.class));
	}
	
	
	@Override
	protected List<SaleProject> getListByFilter(Filter fitler) {
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

	public SaleProject getSale() {
		return sale;
	}

	public void setSale(SaleProject sale) {
		this.sale = sale;
	}

	public void setProjectService(SaleProjectService projectService) {
		this.projectService = projectService;
	}
	
}
