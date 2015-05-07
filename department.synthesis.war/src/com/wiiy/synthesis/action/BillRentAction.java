package com.wiiy.synthesis.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.synthesis.entity.SynthesisBillPlanRent;
import com.wiiy.synthesis.entity.SynthesisBillRent;
import com.wiiy.synthesis.entity.SynthesisContract;
import com.wiiy.synthesis.service.SynthesisBillPlanRentService;
import com.wiiy.synthesis.service.SynthesisBillRentService;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public class BillRentAction extends JqGridBaseAction<SynthesisBillRent>{
	
	private SynthesisBillRentService billRentService;
	private SynthesisBillPlanRentService billPlanRentService;
	private Result result;
	private SynthesisBillRent billRent;
	private Long id;
	private String ids;
	
	
	public String add(){
		Filter filter = new Filter(SynthesisBillPlanRent.class);
		filter.eq("id", id);
		SynthesisBillPlanRent billPlanRent = billPlanRentService.getBeanByFilter(filter).getValue();
		SynthesisContract contract = billPlanRent.getContract();
		if (contract != null) {
			String sql = "SELECT SUM(settlement_fee) from synthesis_synthesis_bill_rent WHERE contract_id="+id;
			List list = (List) billRentService.getResultBySql(sql).getValue();
			contract.setExt1("0.00");
			if (list.size() > 0 && list.get(0) != null) {
				contract.setExt1(list.get(0).toString());
			}
		}
		billPlanRent.setContract(contract);
		result = Result.value(billPlanRent);
		return "add";
	}
	
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
		SynthesisBillRent dbBean = billRentService.getBeanById(billRent.getId()).getValue();
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
		Filter filter = new Filter(SynthesisBillRent.class);
		if(id != null){
			filter.eq("contractId", id);
		}
		return refresh(filter);
	}
	
	@Override
	protected List<SynthesisBillRent> getListByFilter(Filter fitler) {
		return billRentService.getListByFilter(fitler).getValue();
	}
	
	
	public SynthesisBillRent getBillRent() {
		return billRent;
	}

	public void setBillRent(SynthesisBillRent billRent) {
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

	public void setBillRentService(SynthesisBillRentService billRentService) {
		this.billRentService = billRentService;
	}

	public void setBillPlanRentService(
			SynthesisBillPlanRentService billPlanRentService) {
		this.billPlanRentService = billPlanRentService;
	}
	
}
