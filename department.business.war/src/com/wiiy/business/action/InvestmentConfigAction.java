package com.wiiy.business.action;

import java.util.List;

import com.wiiy.business.entity.InvestmentConfig;
import com.wiiy.business.entity.InvestmentContectInfo;
import com.wiiy.business.service.InvestmentConfigService;
import com.wiiy.business.service.InvestmentContectInfoService;
import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public class InvestmentConfigAction extends JqGridBaseAction<InvestmentConfig>{
	
	private InvestmentConfigService investmentConfigService;
	private InvestmentContectInfoService investmentContectInfoService;

	private Result result;
	private InvestmentConfig investmentConfig;
	private Long id;
	private String ids;
	private Long investmentId;
	
	private List<InvestmentContectInfo> contectInfos;
	
	public String contectInfoList(){
		Filter filter = new Filter(InvestmentContectInfo.class);
		return refresh(filter);
	}
	
	public String select(){
		return SELECT;
	}
	
	public String save(){
		result = investmentConfigService.save(id,ids);
		return JSON;
	}
	
	public String view(){
		result = investmentConfigService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = investmentConfigService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		InvestmentConfig dbBean = investmentConfigService.getBeanById(investmentConfig.getId()).getValue();
		BeanUtil.copyProperties(investmentConfig, dbBean);
		result = investmentConfigService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = investmentConfigService.deleteById(id,investmentId);
		} else if(ids!=null){
			result = investmentConfigService.deleteByIds(ids,investmentId);
		}
		return JSON;
	}
	
	public String myList(){
		return "myList";
	}
	
	public String list(){
		List<InvestmentConfig> list = investmentConfigService.getListByFilter(new Filter(InvestmentConfig.class).eq("investmentId", id).include("id").include("investmentContectInfoId")).getValue();
		Long[] investmentContectInfoIds = null;
		if(list!=null && list.size()>0){
			investmentContectInfoIds = new Long[list.size()];
 			int i = 0;
 			for (InvestmentConfig investmentConfig : list) {
 				investmentContectInfoIds[i] = investmentConfig.getInvestmentContectInfoId();
 				i++;
 			}
 		}
		
		Filter filter = new Filter(InvestmentContectInfo.class);
		if(investmentContectInfoIds!=null){
			filter.in("id", investmentContectInfoIds);
		}else{
			filter.in("id", 0L);
		}
		return refresh(filter);
	}
	
	@Override
	protected List<InvestmentConfig> getListByFilter(Filter fitler) {
		return investmentConfigService.getListByFilter(fitler).getValue();
	}
	
	
	public InvestmentConfig getInvestmentConfig() {
		return investmentConfig;
	}

	public void setInvestmentConfig(InvestmentConfig investmentConfig) {
		this.investmentConfig = investmentConfig;
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

	public void setInvestmentConfigService(InvestmentConfigService investmentConfigService) {
		this.investmentConfigService = investmentConfigService;
	}
	
	public Result getResult() {
		return result;
	}
	public void setInvestmentContectInfoService(
			InvestmentContectInfoService investmentContectInfoService) {
		this.investmentContectInfoService = investmentContectInfoService;
	}

	public List<InvestmentContectInfo> getContectInfos() {
		return contectInfos;
	}

	public void setContectInfos(List<InvestmentContectInfo> contectInfos) {
		this.contectInfos = contectInfos;
	}

	public Long getInvestmentId() {
		return investmentId;
	}

	public void setInvestmentId(Long investmentId) {
		this.investmentId = investmentId;
	}
}
