package com.wiiy.estate.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.estate.entity.EstateBillRent;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface EstateBillRentService extends IService<EstateBillRent> {
	@SuppressWarnings("rawtypes")
	Result<List> getResultBySql(String sql);
}
