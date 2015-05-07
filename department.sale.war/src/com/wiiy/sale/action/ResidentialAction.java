package com.wiiy.sale.action;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.SimpleFormatter;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.park.entity.Building;
import com.wiiy.park.entity.Park;
import com.wiiy.park.entity.Room;
import com.wiiy.common.preferences.enums.DepartmentEnum;
import com.wiiy.park.service.BuildingService;
import com.wiiy.park.service.ParkService;
import com.wiiy.park.service.RoomService;
import com.wiiy.sale.entity.Residential;
import com.wiiy.sale.service.ResidentialService;

/**
 * @author my
 */
public class ResidentialAction extends JqGridBaseAction<Residential>{
	
	private ResidentialService residentialService;
	private Result result;
	private Residential residential;
	private Room room;
	private Long id;
	private String ids;
	private RoomService roomService;
	private String type;
	private String roomEnum;
	private DepartmentEnum enums;
	private String roomIds;
	private Long customerId;
	
	//预订提醒数量 7天内
	public String registCount(){
		Filter filter = new Filter(Residential.class);
		Calendar calendar = Calendar.getInstance();
		Date time = CalendarUtil.getEarliest(calendar.getTime(), Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DAY_OF_YEAR, 7);
		filter.match("endTime", time, "ge");
		filter.match("endTime", calendar.getTime(), "le");
		result = residentialService.getRowCount(filter);
		return JSON;
	}
	
	public String listAll(){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 7);
		Format format1=new SimpleDateFormat();
		String times=format1.format(cal.getTime());
		String sql="SELECT count(id) FROM sale_residential WHERE end_time<'"+times+"'";
		List<Object> list=roomService.getListBySql(sql);
		int num=Integer.parseInt(list.get(0).toString());
		result=Result.value(num);
		return JSON;
	}
	
	public String save(){
		result = residentialService.save(residential);
		return JSON;
	}
	
	public String view(){
		result = residentialService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = residentialService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		Residential dbBean = residentialService.getBeanById(residential.getId()).getValue();
		BeanUtil.copyProperties(residential, dbBean);
		result = residentialService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = residentialService.deleteById(id);
		} else if(ids!=null){
			result = residentialService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(Residential.class);
		
		filter.createAlias("user", "user");
		if (id != null) {
			filter.eq("roomId", id);
		}
		//预订到期提醒 7天内
		if("remind".equals(type)){
			Calendar calendar = Calendar.getInstance();
			Date time = CalendarUtil.getEarliest(calendar.getTime(), Calendar.DAY_OF_MONTH);
			calendar.add(Calendar.DAY_OF_YEAR, 7);
			filter.match("endTime", time, "ge");
			filter.match("endTime", calendar.getTime(), "le");
		}
		
		return refresh(filter);
	}
	
	public String changeState() {
		result = residentialService.changeState(id);
		return JSON;
	}
	
	@Override
	protected List<Residential> getListByFilter(Filter fitler) {
		return residentialService.getListByFilter(fitler).getValue();
	}
	
	
	public Residential getResidential() {
		return residential;
	}

	public void setResidential(Residential residential) {
		this.residential = residential;
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

	public void setResidentialService(ResidentialService residentialService) {
		this.residentialService = residentialService;
	}
	
	public Result getResult() {
		return result;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRoomEnum() {
		return roomEnum;
	}

	public void setRoomEnum(String roomEnum) {
		this.roomEnum = roomEnum;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public DepartmentEnum getEnums() {
		return enums;
	}

	public void setEnums(DepartmentEnum enums) {
		this.enums = enums;
	}

	public String getRoomIds() {
		return roomIds;
	}
	public void setRoomIds(String roomIds) {
		this.roomIds = roomIds;
	}
	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
}
