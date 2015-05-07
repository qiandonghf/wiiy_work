package com.wiiy.engineering.service;

import com.wiiy.commons.service.IService;
import com.wiiy.engineering.entity.EngineeringContract;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface EngineeringContractService extends IService<EngineeringContract> {
	Result<String> generateCode();
}
