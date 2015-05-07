package com.wiiy.estate.service;

import java.util.List;

import com.wiiy.commons.service.IService;

import com.wiiy.estate.entity.Supply;

public interface SupplyService extends IService<Supply>{

	List<Object> getListBySql(String sql);

}
