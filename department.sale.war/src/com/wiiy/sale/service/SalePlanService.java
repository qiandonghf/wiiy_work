package com.wiiy.sale.service;

import com.wiiy.commons.service.IService;
import com.wiiy.sale.entity.SalePlan;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface SalePlanService extends IService<SalePlan> {
	Result<String> generateCode();
}
