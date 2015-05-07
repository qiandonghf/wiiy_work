package com.wiiy.estate.service;

import java.util.Date;
import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.estate.entity.DeviceManagement;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface DeviceManagementService extends IService<DeviceManagement> {

	Result<DeviceManagement> saveOrUpdate(DeviceManagement deviceManagement);

	Date getById(long id, String className);

	List<Object> getListBySql(String sql);
}
