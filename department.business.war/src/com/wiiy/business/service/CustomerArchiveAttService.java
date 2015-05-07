package com.wiiy.business.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.business.entity.CustomerArchiveAtt;

/**
 * @author my
 */
public interface CustomerArchiveAttService extends IService<CustomerArchiveAtt> {

	List<Object> getListBySql(String sql);
}
