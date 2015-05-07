package com.wiiy.synthesis.service;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.SynthesisCustomer;
import com.wiiy.synthesis.entity.SynthesisCustomerInfo;

/**
 * @author my
 */
public interface SynthesisCustomerService extends IService<SynthesisCustomer> {
	@SuppressWarnings("rawtypes")
	Result save(SynthesisCustomer customer, SynthesisCustomerInfo customerInfo);
	@SuppressWarnings("rawtypes")
	Result update(SynthesisCustomer customer,SynthesisCustomerInfo customerInfo);
	Result<String> generateCode();
}
