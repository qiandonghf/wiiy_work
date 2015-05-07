package com.wiiy.engineering.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.engineering.entity.EngineeringBillPlanRent;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface EngineeringBillPlanRentService extends IService<EngineeringBillPlanRent> {
	Result<String> generateCode();
	@SuppressWarnings("rawtypes")
	Result<List> getResultBySql(String sql);
}
