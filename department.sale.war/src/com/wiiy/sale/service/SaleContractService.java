package com.wiiy.sale.service;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.sale.entity.SaleContract;

/**
 * @author my
 */
public interface SaleContractService extends IService<SaleContract> {
	Result<String> generateCode();
}
