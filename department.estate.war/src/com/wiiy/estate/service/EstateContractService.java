package com.wiiy.estate.service;

import com.wiiy.commons.service.IService;
import com.wiiy.estate.entity.EstateContract;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface EstateContractService extends IService<EstateContract> {
	Result<String> generateCode();
}
