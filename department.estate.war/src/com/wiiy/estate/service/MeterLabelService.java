package com.wiiy.estate.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.estate.entity.MeterLabel;
import com.wiiy.park.entity.Park;

public interface MeterLabelService extends IService<MeterLabel>{

	void updateRecord(Long id, String oldIds, String newIds);
	
	//获取水电表单价
	public List<Park> getWaterOrElePrice();

}
