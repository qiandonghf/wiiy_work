package com.wiiy.estate.service;

import com.wiiy.common.preferences.enums.BillPlanStatusEnum;
import com.wiiy.commons.service.IService;
import com.wiiy.estate.entity.BillPlanFacility;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

public interface BillPlanFacilityService extends IService<BillPlanFacility>{

	Result checkoutById(Long id, BillPlanStatusEnum inchecked);

	Result batchCheckout(String ids, String[] split, boolean autoRemind);

	Integer getRowCount(Filter filter);

}
