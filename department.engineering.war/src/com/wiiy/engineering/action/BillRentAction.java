package com.wiiy.engineering.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.core.entity.User;
import com.wiiy.engineering.entity.EngineeringBillRent;
import com.wiiy.engineering.service.EngineeringBillRentService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public class BillRentAction extends JqGridBaseAction<EngineeringBillRent>{
	
	private EngineeringBillRentService billRentService;
	private Result result;
	private EngineeringBillRent billRent;
	private Long id;
	private String ids;
	private User user;
	
	public String save(){
		if (billRent.getSettlementType() != null && 
				"".equals(billRent.getSettlementType())) {
			billRent.setSettlementType(null);
		}
		if((billRent.getAudit() != null && "".equals(billRent.getAudit())) ||
				billRent.getAudit() == null){
			billRent.setAudit(BooleanEnum.NO);
		}
		result = billRentService.save(billRent);
		return JSON;
	}
	
	public String view(){
		result = billRentService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = billRentService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		EngineeringBillRent dbBean = billRentService.getBeanById(billRent.getId()).getValue();
		BeanUtil.copyProperties(billRent, dbBean);
		if (billRent.getSettlementType() != null && 
				"".equals(billRent.getSettlementType())) {
			dbBean.setSettlementType(null);
		}
		if((billRent.getAudit() != null && "".equals(billRent.getAudit())) ||
				billRent.getAudit() == null){
			dbBean.setAudit(BooleanEnum.NO);
		}
		result = billRentService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = billRentService.deleteById(id);
		} else if(ids!=null){
			result = billRentService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(EngineeringBillRent.class);
		if(id != null){
			filter.eq("contractId", id);
		}
		return refresh(filter);
	}
	
	@Override
	protected List<EngineeringBillRent> getListByFilter(Filter fitler) {
		return billRentService.getListByFilter(fitler).getValue();
	}
	
	
	public EngineeringBillRent getBillRent() {
		return billRent;
	}

	public void setBillRent(EngineeringBillRent billRent) {
		this.billRent = billRent;
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

	public void setBillRentService(EngineeringBillRentService billRentService) {
		this.billRentService = billRentService;
	}

	public User getUser() {
		return user;
	}
	
}
