package com.wiiy.estate.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.core.entity.User;
import com.wiiy.estate.entity.EstateBillRent;
import com.wiiy.estate.service.EstateBillRentService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public class BillRentAction extends JqGridBaseAction<EstateBillRent>{
	
	private EstateBillRentService billRentService;
	private Result result;
	private EstateBillRent billRent;
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
		EstateBillRent dbBean = billRentService.getBeanById(billRent.getId()).getValue();
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
		Filter filter = new Filter(EstateBillRent.class);
		if(id != null){
			filter.eq("contractId", id);
		}
		return refresh(filter);
	}
	
	@Override
	protected List<EstateBillRent> getListByFilter(Filter fitler) {
		return billRentService.getListByFilter(fitler).getValue();
	}
	
	
	public EstateBillRent getBillRent() {
		return billRent;
	}

	public void setBillRent(EstateBillRent billRent) {
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

	public void setBillRentService(EstateBillRentService billRentService) {
		this.billRentService = billRentService;
	}

	public User getUser() {
		return user;
	}
	
}
