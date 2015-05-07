package com.wiiy.estate.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.estate.entity.EstateBillPlanRent;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface EstateBillPlanRentService extends IService<EstateBillPlanRent> {
	Result<String> generateCode();
	@SuppressWarnings("rawtypes")
	Result<List> getResultBySql(String sql);
}
