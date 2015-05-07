package com.wiiy.estate.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.estate.entity.ParkingFee;

/**
 * @author my
 */
public interface ParkingFeeService extends IService<ParkingFee> {

	List<Object> getListBySql(String sql);
}
