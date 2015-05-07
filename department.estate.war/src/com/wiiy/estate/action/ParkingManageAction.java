package com.wiiy.estate.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.park.entity.Park;
import com.wiiy.park.service.ParkService;

import com.wiiy.estate.entity.Garage;
import com.wiiy.estate.entity.ParkingManage;
import com.wiiy.estate.service.GarageService;
import com.wiiy.estate.service.ParkingManageService;

/**
 * @author my
 */
public class ParkingManageAction extends JqGridBaseAction<ParkingManage>{
	
	private ParkingManageService parkingManageService;
	private GarageService garageService;
	private ParkService parkService;
	private Garage garage;
	private Park park;
	private Result result;
	private ParkingManage parkingManage;
	private Long id;
	private String ids;
	private List<ParkingManage> parkingManages;
	private Long garageId;
	private List<Park> parkList;
	private List<Garage> garageList;
	public String select(){
		 parkList=parkService.getListByFilter(new Filter(Park.class)).getValue();
		 garageList=garageService.getListByFilter(new Filter(Garage.class)).getValue();
		return SELECT;
	}
	public String listAll(){
		Filter filter =new Filter(ParkingManage.class);
		return refresh(filter);
	}
	public String parkDetail(){
		return refresh(new Filter(ParkingManage.class).eq("garageId", id));
	}
	
	public String listByGarage(){
		return refresh(new Filter(ParkingManage.class).eq("garageId", id));
	}
	
	public String save(){
		result = parkingManageService.save(parkingManage);
		return JSON;
	}
	
	public String view(){
		System.out.println(garageId);
		parkingManages = parkingManageService.getListByFilter(new Filter(ParkingManage.class).eq("garageId", garageId)).getValue();
		return VIEW;
	}
	
	public String edit(){
		result = parkingManageService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		ParkingManage dbBean = parkingManageService.getBeanById(parkingManage.getId()).getValue();
		BeanUtil.copyProperties(parkingManage, dbBean);
		result = parkingManageService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = parkingManageService.deleteById(id);
		} else if(ids!=null){
			result = parkingManageService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(ParkingManage.class).eq("garageId", id));
	}
	@Override
	protected List<ParkingManage> getListByFilter(Filter fitler) {
		return parkingManageService.getListByFilter(fitler).getValue();
	}
	
	
	public ParkingManage getParkingManage() {
		return parkingManage;
	}

	public void setParkingManage(ParkingManage parkingManage) {
		this.parkingManage = parkingManage;
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

	public void setParkingManageService(ParkingManageService parkingManageService) {
		this.parkingManageService = parkingManageService;
	}
	
	public Result getResult() {
		return result;
	}
	public List<ParkingManage> getParkingManages() {
		return parkingManages;
	}

	public void setParkingManages(List<ParkingManage> parkingManages) {
		this.parkingManages = parkingManages;
	}
	public Long getGarageId() {
		return garageId;
	}

	public void setGarageId(Long garageId) {
		this.garageId = garageId;
	}
	public Garage getGarage() {
		return garage;
	}
	public void setGarage(Garage garage) {
		this.garage = garage;
	}
	public Park getPark() {
		return park;
	}
	public void setPark(Park park) {
		this.park = park;
	}
	public void setGarageService(GarageService garageService) {
		this.garageService = garageService;
	}
	public void setParkService(ParkService parkService) {
		this.parkService = parkService;
	}
	public List<Park> getParkList() {
		return parkList;
	}
	public void setParkList(List<Park> parkList) {
		this.parkList = parkList;
	}
	public List<Garage> getGarageList() {
		return garageList;
	}
	public void setGarageList(List<Garage> garageList) {
		this.garageList = garageList;
	}
}
