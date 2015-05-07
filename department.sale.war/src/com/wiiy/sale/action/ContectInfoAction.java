package com.wiiy.sale.action;

import java.util.Calendar;
import java.util.List;


import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.core.entity.User;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.sale.activator.SaleActivator;
import com.wiiy.sale.entity.ContectInfo;
import com.wiiy.sale.preferences.enums.InfoEnum;
import com.wiiy.sale.service.ContectInfoService;
import com.wiiy.sale.service.SaleCustomerService;

/**
 * @author my
 */
public class ContectInfoAction extends JqGridBaseAction<ContectInfo>{
	
	private ContectInfoService contectInfoService;
	private SaleCustomerService customerService;
	private Result result;
	private ContectInfo contectInfo;
	private Long id;
	private String ids;
	
	private String own;
	private InfoEnum state;
	private String day;
	
	public String changeState(){
		result = contectInfoService.changeState(id);
		return JSON;
	}
	
	public String contects(){
		result = Result.value(contectInfoService.contects());
		return JSON;
	}
	
	public String save(){
		if(contectInfo.getReceiverId() != null && //
				"".equals(contectInfo.getReceiverId())){
			contectInfo.setReceiverId(null);
			contectInfo.setReceiver(null);
		}
		if(contectInfo.getReturnVisit() != null && // 
				"".equals(contectInfo.getReturnVisit()) //
				|| contectInfo.getReturnVisit() == null){
			contectInfo.setReturnVisit(null);
		}
		result = contectInfoService.save(contectInfo);
		return JSON;
	}
	
	public String view(){
		result = contectInfoService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = contectInfoService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		ContectInfo dbBean = contectInfoService.getBeanById(contectInfo.getId()).getValue();
		BeanUtil.copyProperties(contectInfo, dbBean);
		if(contectInfo.getReceiverId() != null && //
				"".equals(contectInfo.getReceiverId())){
			dbBean.setReceiverId(null);
			dbBean.setReceiver(null);
		}
		if(contectInfo.getReturnVisit() != null && // 
				"".equals(contectInfo.getReturnVisit()) //
				|| contectInfo.getReturnVisit() == null){
			dbBean.setReturnVisit(null);
		}
		result = contectInfoService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = contectInfoService.deleteById(id);
		} else if(ids!=null){
			result = contectInfoService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(ContectInfo.class);
		filter.orderBy("infoStatus", Filter.DESC).orderBy("createTime", Filter.DESC);
		/*filter.createAlias("customer", "customer");
		filter.createAlias("type", "type");
		filter.createAlias("receiver", "receiver");
		String[] includes = {"id","customer.name","type.dataValue","receiver.realName","time","creator"};
		filter.include(includes);*/
		User user = SaleActivator.getSessionUser();
		if(own != null){
			filter.eq("receiverId", user.getId());
		}
		if(state != null){
			filter.eq("infoStatus", state);
		}
		if(day != null){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(day));
			filter.le("returnTime", calendar.getTime());
		}
		return refresh(filter);
	}
	
	@Override
	protected List<ContectInfo> getListByFilter(Filter fitler) {
		return contectInfoService.getListByFilter(fitler).getValue();
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

	public ContectInfo getContectInfo() {
		return contectInfo;
	}

	public void setContectInfo(ContectInfo contectInfo) {
		this.contectInfo = contectInfo;
	}

	public void setCustomerService(SaleCustomerService customerService) {
		this.customerService = customerService;
	}
	public void setContectInfoService(ContectInfoService contectInfoService) {
		this.contectInfoService = contectInfoService;
	}
	
	public Result getResult() {
		return result;
	}

	public String getOwn() {
		return own;
	}

	public void setOwn(String own) {
		this.own = own;
	}

	public InfoEnum getState() {
		return state;
	}

	public void setState(InfoEnum state) {
		this.state = state;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

}
