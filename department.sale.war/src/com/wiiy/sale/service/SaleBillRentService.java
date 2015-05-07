package com.wiiy.sale.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.sale.entity.SaleBillRent;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface SaleBillRentService extends IService<SaleBillRent> {
	@SuppressWarnings("rawtypes")
	Result<List> getResultBySql(String sql);
}
