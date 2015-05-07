package com.wiiy.estate.service;

import com.wiiy.common.preferences.enums.BillPlanStatusEnum;
import com.wiiy.commons.service.IService;
import com.wiiy.estate.entity.EstateDeposit;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface DepositService extends IService<EstateDeposit> {

	@SuppressWarnings("rawtypes")
	Result checkoutById(Long id, BillPlanStatusEnum billPlanStatus);

	Integer getRowCount(Filter le);

	@SuppressWarnings("rawtypes")
	Result batchCheckout(String ids, String[] split, boolean autoRemind);
}
