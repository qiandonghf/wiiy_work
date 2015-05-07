package com.wiiy.estate.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.estate.entity.DeviceYearly;

/**
 * @author my
 */
public interface DeviceYearlyService extends IService<DeviceYearly> {

	List<Object> getListBySql(String sql);
	
	//查询年检日期(上一次年检到目前为止是否已经年检过)
	public boolean checkYear(String previous,String curTime);
	
}
