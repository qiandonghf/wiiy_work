package com.wiiy.business.service;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.business.entity.InvestmentConfig;

/**
 * @author my
 */
public interface InvestmentConfigService extends IService<InvestmentConfig> {

	Result save(Long id, String ids);

	Result deleteById(Long id, Long investmentId);
	
	Result deleteByIds(String id, Long investmentId);
}
