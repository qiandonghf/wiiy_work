package com.wiiy.engineering.action;

import java.util.Calendar;
import java.util.List;

import com.wiiy.common.preferences.enums.ApprovalStatusEnum;
import com.wiiy.common.preferences.enums.BillPlanStatusEnum;
import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.engineering.entity.EngineeringBillPlanRent;
import com.wiiy.engineering.service.EngineeringBillPlanRentService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public class BillPlanRentAction extends JqGridBaseAction<EngineeringBillPlanRent>{
	
	private EngineeringBillPlanRentService billPlanRentService;
	private Result result;
	private EngineeringBillPlanRent billPlanRent;
	private Long id;
	private String ids;
	
	public String simpleList() {
		Filter filter = new Filter(EngineeringBillPlanRent.class);
		filter.createAlias("contract", "contract");
		filter.eq("status", BillPlanStatusEnum.UNCHECK);
		filter.eq("audit", BooleanEnum.YES);
		filter.eq("approvalStatus", ApprovalStatusEnum.NOAPPLICATION);
		String[] includes = {"id","code","contract.name","contract.id","contract.contractAmount","planFee","planPayDate"};
		filter.include(includes);
		return refresh(filter);
	}
	
	public String findPlanByContractId() {
		Filter filter = new Filter(EngineeringBillPlanRent.class);
		filter.eq("contractId", id);
		filter.include("id").include("planFee").include("planPayDate");
		filter.orderBy("planPayDate", Filter.ASC);
		result = billPlanRentService.getListByFilter(filter);
		return JSON;
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
		EngineeringBillPlanRent dbBean = billPlanRentService.getBeanById(billPlanRent.getId()).getValue();
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
	
	/**
	 * 计划付款提醒列表<br/>
	 * 
	 * @return
	 */
	public String remindList(){
		Filter filter = new Filter(EngineeringBillPlanRent.class);
		filter.eq("audit", BooleanEnum.YES).eq("approvalStatus", ApprovalStatusEnum.NOAPPLICATION);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -15);
		filter.le("planPayDate", calendar.getTime());
		String[] includes = {"id","code","audit","approvalStatus","planPayDate","planFee"};
		filter.include(includes);
		return refresh(filter);
	}
	
	/**
	 * 查询提醒记录数
	 * @return
	 */
	public String remindCount(){
		remindList();
		result = Result.value(root == null ? 0 : root.size());
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(EngineeringBillPlanRent.class);
		if(id != null){
			filter.eq("contractId", id);
		}
		return refresh(filter);
	}
	
	@Override
	protected List<EngineeringBillPlanRent> getListByFilter(Filter fitler) {
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
			EngineeringBillPlanRentService billPlanRentService) {
		this.billPlanRentService = billPlanRentService;
	}

	public EngineeringBillPlanRent getBillPlanRent() {
		return billPlanRent;
	}

	public void setBillPlanRent(EngineeringBillPlanRent billPlanRent) {
		this.billPlanRent = billPlanRent;
	}
	
}
