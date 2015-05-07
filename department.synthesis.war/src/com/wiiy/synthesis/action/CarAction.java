package com.wiiy.synthesis.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.synthesis.entity.Car;
import com.wiiy.synthesis.entity.Seal;
import com.wiiy.synthesis.service.CarService;

/**
 * @author my
 */
public class CarAction extends JqGridBaseAction<Car>{
	
	private CarService carService;
	private Result result;
	private Car car;
	private Long id;
	private String ids;
	private List<Car> carList;
	
	private int selType=0;//0单选 1多选
	
	private List<Car> selectedCars;

	public String select(){
		selType=1;
		if (ids != null && ids.trim().length() > 0) {
				Set<Long> idSet = splitIds(ids);
				Filter filter = new Filter(Seal.class);
				filter.in("id", idSet.toArray(new Long[idSet.size()]));
				selectedCars = carService.getListByFilter(filter).getValue();
		}
		return "select";
	}
	
	private Set<Long> splitIds(String ids2) {
    	
    	if (ids2 == null) return new HashSet<Long>();
    	
		Set<Long> ret = new HashSet<Long>();
		
		String[] idArray = ids2.split("\\,");
		
		for (String id : idArray) {
			try {
				ret.add(Long.valueOf(id.trim()));
			} catch (NumberFormatException e) {
			}
		}
		return ret;
	}
	
	public String carSelect(){
		carList = carService.getListByFilter(new Filter(Car.class)).getValue();
		return "carSelect";
	}
	
	
	public String save(){
		result = carService.save(car);
		return JSON;
	}
	
	public String view(){
		result = carService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = carService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		Car dbBean = carService.getBeanById(car.getId()).getValue();
		BeanUtil.copyProperties(car, dbBean);
		result = carService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = carService.deleteById(id);
		} else if(ids!=null){
			result = carService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(Car.class));
	}
	
	@Override
	protected List<Car> getListByFilter(Filter fitler) {
		return carService.getListByFilter(fitler).getValue();
	}
	
	
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
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

	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	
	public Result getResult() {
		return result;
	}


	public List<Car> getCarList() {
		return carList;
	}


	public void setCarList(List<Car> carList) {
		this.carList = carList;
	}

	public int getSelType() {
		return selType;
	}

	public void setSelType(int selType) {
		this.selType = selType;
	}

	public List<Car> getSelectedCars() {
		return selectedCars;
	}

	public void setSelectedCars(List<Car> selectedCars) {
		this.selectedCars = selectedCars;
	}
}
