package com.wiiy.sale.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.common.preferences.enums.ApprovalStatusEnum;
import com.wiiy.common.preferences.enums.BillPlanStatusEnum;
import com.wiiy.sale.entity.SaleBillPlanRent;
import com.wiiy.sale.service.SaleBillPlanRentService;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public class BillPlanRentAction extends JqGridBaseAction<SaleBillPlanRent>{
	
	private SaleBillPlanRentService billPlanRentService;
	private Result result;
	private SaleBillPlanRent billPlanRent;
	private Long id;
	private String ids;
	
	public String simpleList() {
		Filter filter = new Filter(SaleBillPlanRent.class);
		filter.createAlias("contract", "contract");
		filter.eq("status", BillPlanStatusEnum.UNCHECK);
		filter.eq("audit", BooleanEnum.YES);
		filter.eq("approvalStatus", ApprovalStatusEnum.NOAPPLICATION);
		String[] includes = {"id","code","contract.name","contract.id","contract.contractAmount","planFee","planPayDate"};
		filter.include(includes);
		return refresh(filter);
	}
	
	public String findPlanById() {
		result = billPlanRentService.getBeanById(id);
		return JSON;
	}
	
	public String generateCode() {
		result = billPlanRentService.generateCode();
		return JSON;
	}
	
	public String save(){
		if ((billPlanRent.getAudit() != null && //
				"".equals(billPlanRent.getAudit()))||//
				billPlanRent.getAudit() == null) {
			billPlanRent.setAudit(BooleanEnum.NO);
		}
		result = billPlanRentService.save(billPlanRent);
		return JSON;
	}
	
	public String view(){
		result = billPlanRentService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = billPlanRentService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		SaleBillPlanRent dbBean = billPlanRentService.getBeanById(billPlanRent.getId()).getValue();
		BeanUtil.copyProperties(billPlanRent, dbBean);
		if ((billPlanRent.getAudit() != null && //
				"".equals(billPlanRent.getAudit()))||//
				billPlanRent.getAudit() == null) {
			dbBean.setAudit(BooleanEnum.NO);
		}
		result = billPlanRentService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = billPlanRentService.deleteById(id);
		} else if(ids!=null){
			result = billPlanRentService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(SaleBillPlanRent.class);
		if(id != null){
			filter.eq("contractId", id);
		}
		return refresh(filter);
	}
	
	@Override
	protected List<SaleBillPlanRent> getListByFilter(Filter fitler) {
		return billPlanRentService.getListByFilter(fitler).getValue();
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

	public void setBillPlanRentService(
			SaleBillPlanRentService billPlanRentService) {
		this.billPlanRentService = billPlanRentService;
	}

	public SaleBillPlanRent getBillPlanRent() {
		return billPlanRent;
	}

	public void setBillPlanRent(SaleBillPlanRent billPlanRent) {
		this.billPlanRent = billPlanRent;
	}
	
}
