package com.wiiy.sale.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.sale.entity.SaleBillPlanRent;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface SaleBillPlanRentService extends IService<SaleBillPlanRent> {
	Result<String> generateCode();
	@SuppressWarnings("rawtypes")
	Result<List> getResultBySql(String sql);
}
