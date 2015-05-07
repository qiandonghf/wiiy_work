package com.wiiy.estate.action;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.entity.Garage;
import com.wiiy.estate.entity.VehicleManagement;
import com.wiiy.estate.service.GarageService;
import com.wiiy.estate.service.VehicleManagementService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.park.entity.Park;
import com.wiiy.park.service.ParkService;

/**
 * @author my
 */
public class VehicleManagementAction extends JqGridBaseAction<VehicleManagement>{
	private VehicleManagementService vehicleManagementService;
	private Result result;
	private VehicleManagement vehicleManagement;
	private Long id;
	private String ids;
	public String select(){
		return SELECT;
	}
	
	public String save(){
		result = vehicleManagementService.save(vehicleManagement);
		return JSON;
	}
	
	public String view(){
		result = vehicleManagementService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = vehicleManagementService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		VehicleManagement dbBean = vehicleManagementService.getBeanById(vehicleManagement.getId()).getValue();
		BeanUtil.copyProperties(vehicleManagement, dbBean);
		result = vehicleManagementService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = vehicleManagementService.deleteById(id);
		} else if(ids!=null){
			result = vehicleManagementService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String listAll(){
		Filter filter = new Filter (VehicleManagement.class);
		String value=null;
		if(filters!=null && filters.length()>0){
			value = serachByLikeFromFilters("infoAll",filters);		
		}
		if(value!=null){
			filter.or(Filter.Like("licenseTag", value),Filter.Like("unit", value),Filter.Like("name", value),Filter.Like("phone", value),Filter.Like("payment", value));
		}
		return refresh(filter);
	}
/*	public String listAll(){
	   	Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -30);
        Format format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tt=format1.format(cal.getTime());
		String sql="SELECT count(end_date) FROM estate_parking_fee WHERE end_date <'"+tt+"'";
		List<Object> list=parkingFeeService.getListBySql(sql);
		result=Result.value(Integer.parseInt(list.get(0).toString()));
		return JSON;
	}*/
	@Override
	protected List<VehicleManagement> getListByFilter(Filter fitler) {
		return vehicleManagementService.getListByFilter(fitler).getValue();
	}
	
	
	public VehicleManagement getVehicleManagement() {
		return vehicleManagement;
	}

	public void setVehicleManagement(VehicleManagement vehicleManagement) {
		this.vehicleManagement = vehicleManagement;
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

	public void setVehicleManagementService(VehicleManagementService vehicleManagementService) {
		this.vehicleManagementService = vehicleManagementService;
	}
	
	public Result getResult() {
		return result;
	}

	
}
