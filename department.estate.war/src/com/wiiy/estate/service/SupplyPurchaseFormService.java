package com.wiiy.estate.service;

import com.wiiy.commons.service.IService;
import com.wiiy.estate.entity.SupplyPurchaseForm;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface SupplyPurchaseFormService extends IService<SupplyPurchaseForm> {
	Result<String> generateCode();
}
