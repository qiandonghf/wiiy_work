package com.wiiy.estate.action;

import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.estate.entity.ParkingFee;
import com.wiiy.estate.entity.ParkingManage;
import com.wiiy.estate.service.ParkingFeeService;
import com.wiiy.estate.service.ParkingManageService;

/**
 * @author my
 */
public class ParkingFeeAction extends JqGridBaseAction<ParkingFee>{
	
	private ParkingFeeService parkingFeeService;
	private ParkingManageService parkingManageService;
	private ParkingManage parkingManage;
	private Result result;
	private ParkingFee parkingFee;
	private Long id;
	private String ids;
	private String times;
	
	public String select(){
		return SELECT;
	}
	
	public String save(){
		result = parkingFeeService.save(parkingFee);
		return JSON;
	}
	
	public String view(){
		result = parkingFeeService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = parkingFeeService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		ParkingFee dbBean = parkingFeeService.getBeanById(parkingFee.getId()).getValue();
		BeanUtil.copyProperties(parkingFee, dbBean);
		result = parkingFeeService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = parkingFeeService.deleteById(id);
		} else if(ids!=null){
			result = parkingFeeService.deleteByIds(ids);
		}
		return JSON;
	}
	public String listAll(){
	   	Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, +7);
        Format format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tt=format1.format(cal.getTime());
		String sql="SELECT count(end_date) FROM estate_parking_fee WHERE isRemind='NO' AND end_date <'"+tt+"'";
		List<Object> list=parkingFeeService.getListBySql(sql);
		result=Result.value(Integer.parseInt(list.get(0).toString()));
		return JSON;
	}
	public String list(){
		Filter filter = new Filter(ParkingFee.class);
		if(times!=null&&!("").equals(times)){
			filter.eq("isRemind", BooleanEnum.NO);
			Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DAY_OF_MONTH, +7);
	        Format format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String tt=format1.format(cal.getTime());
			String sql="SELECT id FROM estate_parking_fee WHERE end_date <'"+tt+"'";
			List<Object> list=parkingFeeService.getListBySql(sql);
					Long[] idss=new Long[list.size()];
					int i=0;
					for (Object li : list) {
						idss[i]=Long.parseLong(li.toString());
						i++;
					}
					filter.in("id", idss);
		}
		if(id!=null){
			filter.eq("parkingManageId", id);
		}
		String value=null;
		if(filters!=null && filters.length()>0){
			value = serachByLikeFromFilters("infoAll",filters);		
		}
		if(value!=null){
			filter.or(Filter.Like("licenseNo", value),Filter.Like("parkingManage", value),Filter.Like("name", value));
		}
		return refresh(filter);
	}
	
	@Override
	protected List<ParkingFee> getListByFilter(Filter fitler) {
		return parkingFeeService.getListByFilter(fitler).getValue();
	}
	
	
	public ParkingFee getParkingFee() {
		return parkingFee;
	}

	public void setParkingFee(ParkingFee parkingFee) {
		this.parkingFee = parkingFee;
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

	public void setParkingFeeService(ParkingFeeService parkingFeeService) {
		this.parkingFeeService = parkingFeeService;
	}
	
	public Result getResult() {
		return result;
	}

	public ParkingManage getParkingManage() {
		return parkingManage;
	}

	public void setParkingManage(ParkingManage parkingManage) {
		this.parkingManage = parkingManage;
	}


	public void setParkingManageService(ParkingManageService parkingManageService) {
		this.parkingManageService = parkingManageService;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}
	
}
