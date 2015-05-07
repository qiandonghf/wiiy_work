package com.wiiy.estate.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.estate.entity.DeviceYearly;
import com.wiiy.estate.service.DeviceYearlyService;

/**
 * @author my
 */
public class DeviceYearlyAction extends JqGridBaseAction<DeviceYearly>{
	
	private DeviceYearlyService deviceYearlyService;
	private Result result;
	private DeviceYearly deviceYearly;
	private Long id;
	private String ids;
	
	
	public String listAll(){
		String sql="SELECT count(id) FROM estate_device_management";
		List<Object> list=deviceYearlyService.getListBySql(sql);
		if(list.size()>0){
			result=Result.value(Integer.valueOf(list.get(0).toString()));
		}
		return JSON;
	}
	
	public String save(){
		result = deviceYearlyService.save(deviceYearly);
		return JSON;
	}
	
	public String view(){
		result = deviceYearlyService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = deviceYearlyService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		DeviceYearly dbBean = deviceYearlyService.getBeanById(deviceYearly.getId()).getValue();
		BeanUtil.copyProperties(deviceYearly, dbBean);
		result = deviceYearlyService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = deviceYearlyService.deleteById(id);
		} else if(ids!=null){
			result = deviceYearlyService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(DeviceYearly.class);
		if (id != null) {
			filter.eq("deviceId", id);
		}
		return refresh(filter);
	}
	
	@Override
	protected List<DeviceYearly> getListByFilter(Filter fitler) {
		return deviceYearlyService.getListByFilter(fitler).getValue();
	}
	
	
	public DeviceYearly getDeviceYearly() {
		return deviceYearly;
	}

	public void setDeviceYearly(DeviceYearly deviceYearly) {
		this.deviceYearly = deviceYearly;
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

	public void setDeviceYearlyService(DeviceYearlyService deviceYearlyService) {
		this.deviceYearlyService = deviceYearlyService;
	}
	
	public Result getResult() {
		return result;
	}
	
}
