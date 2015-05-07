package com.wiiy.engineering.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.engineering.entity.EngineeringBillRent;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface EngineeringBillRentService extends IService<EngineeringBillRent> {
	@SuppressWarnings("rawtypes")
	Result<List> getResultBySql(String sql);
}
