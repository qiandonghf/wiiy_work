package com.wiiy.engineering.service;

import com.wiiy.commons.service.IService;
import com.wiiy.engineering.entity.EngineeringCustomer;
import com.wiiy.engineering.entity.EngineeringCustomerInfo;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public interface EngineeringCustomerService extends IService<EngineeringCustomer> {
	Result save(EngineeringCustomer customer,//
			EngineeringCustomerInfo customerInfo);
	Result update(EngineeringCustomer customer,EngineeringCustomerInfo customerInfo);
	Result<String> generateCode();
}
