package com.wiiy.estate.service;

import com.wiiy.commons.service.IService;
import com.wiiy.estate.entity.DevicePatrol;

/**
 * @author my
 */
public interface DevicePatrolService extends IService<DevicePatrol> {
	
	
	//判断是否已经巡检(上一次巡检到目前为止是否已经巡检过)
	public boolean checkPatrol(String previous, String curTime);
	
}
