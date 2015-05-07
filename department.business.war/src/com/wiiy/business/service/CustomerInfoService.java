package com.wiiy.business.service;

import java.util.List;

import com.wiiy.business.entity.CustomerInfo;
import com.wiiy.commons.service.IService;

/**
 * @author my
 */
public interface CustomerInfoService extends IService<CustomerInfo> {

	List<Object> getListBySql(String sql);
}
