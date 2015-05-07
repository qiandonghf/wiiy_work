package com.wiiy.business.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.business.entity.InvestmentRepeatedViste;
import com.wiiy.business.service.InvestmentRepeatedVisteService;

/**
 * @author my
 */
public class InvestmentRepeatedVisteAction extends JqGridBaseAction<InvestmentRepeatedViste>{
	
	private InvestmentRepeatedVisteService investmentRepeatedVisteService;
	private Result result;
	private InvestmentRepeatedViste investmentRepeatedViste;
	private Long id;
	private String ids;
	
	public String add(){
		result = investmentRepeatedVisteService.getBeanById(id);
		return "add";
	}
	
	public String save(){
		if(investmentRepeatedViste.getReceiverId() != null && //
				"".equals(investmentRepeatedViste.getReceiverId())){
			investmentRepeatedViste.setReceiverId(null);
			investmentRepeatedViste.setReceiver(null);
		}
		result = investmentRepeatedVisteService.save(investmentRepeatedViste);
		return JSON;
	}
	
	public String view(){
		result = investmentRepeatedVisteService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = investmentRepeatedVisteService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		InvestmentRepeatedViste dbBean = investmentRepeatedVisteService.getBeanById(investmentRepeatedViste.getId()).getValue();
		BeanUtil.copyProperties(investmentRepeatedViste, dbBean);
		if(investmentRepeatedViste.getReceiverId() != null && //
				"".equals(investmentRepeatedViste.getReceiverId())){
			dbBean.setReceiverId(null);
			dbBean.setReceiver(null);
		}
		if((investmentRepeatedViste.getRemindTime() != null && //
				"".equals(investmentRepeatedViste.getRemindTime())) //
				|| investmentRepeatedViste.getRemindTime() == null){
			dbBean.setRemindTime(null);
		}
		result = investmentRepeatedVisteService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = investmentRepeatedVisteService.deleteById(id);
		} else if(ids!=null){
			result = investmentRepeatedVisteService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(InvestmentRepeatedViste.class));
	}
	
	@Override
	protected List<InvestmentRepeatedViste> getListByFilter(Filter fitler) {
		return investmentRepeatedVisteService.getListByFilter(fitler).getValue();
	}
	
	
	public InvestmentRepeatedViste getInvestmentRepeatedViste() {
		return investmentRepeatedViste;
	}

	public void setInvestmentRepeatedViste(InvestmentRepeatedViste investmentRepeatedViste) {
		this.investmentRepeatedViste = investmentRepeatedViste;
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

	public void setInvestmentRepeatedVisteService(InvestmentRepeatedVisteService investmentRepeatedVisteService) {
		this.investmentRepeatedVisteService = investmentRepeatedVisteService;
	}
	
	public Result getResult() {
		return result;
	}
	
}
