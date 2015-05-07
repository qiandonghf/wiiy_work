package com.wiiy.park.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.park.entity.Building;

/**
 * @author my
 */
public interface BuildingService extends IService<Building> {
	Result<List<Building>> getListByHql(String hql);
}
