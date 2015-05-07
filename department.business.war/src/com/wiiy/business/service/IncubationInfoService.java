package com.wiiy.business.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.business.entity.IncubationInfo;

/**
 * @author my
 */
public interface IncubationInfoService extends IService<IncubationInfo> {

	List<Object> getListBySql(String sql);

}
