package com.wiiy.synthesis.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.synthesis.entity.CarFix;
import com.wiiy.synthesis.service.CarFixService;

/**
 * @author my
 */
public class CarFixAction extends JqGridBaseAction<CarFix>{
	
	private CarFixService carFixService;
	private Result result;
	private CarFix carFix;
	private Long id;
	private String ids;
	
	
	public String save(){
		result = carFixService.save(carFix);
		return JSON;
	}
	
	public String view(){
		result = carFixService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = carFixService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		CarFix dbBean = carFixService.getBeanById(carFix.getId()).getValue();
		BeanUtil.copyProperties(carFix, dbBean);
		result = carFixService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = carFixService.deleteById(id);
		} else if(ids!=null){
			result = carFixService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(CarFix.class));
	}
	
	@Override
	protected List<CarFix> getListByFilter(Filter fitler) {
		return carFixService.getListByFilter(fitler).getValue();
	}
	
	
	public CarFix getCarFix() {
		return carFix;
	}

	public void setCarFix(CarFix carFix) {
		this.carFix = carFix;
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

	public void setCarFixService(CarFixService carFixService) {
		this.carFixService = carFixService;
	}
	
	public Result getResult() {
		return result;
	}
	
}
