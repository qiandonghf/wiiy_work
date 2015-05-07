package com.wiiy.estate.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.wiiy.common.preferences.enums.BillPlanStatusEnum;
import com.wiiy.common.preferences.enums.BillingMethodEnum;
import com.wiiy.common.preferences.enums.EstateFeeEnum;
import com.wiiy.common.preferences.enums.PriceUnitEnum;
import com.wiiy.commons.service.IService;
import com.wiiy.estate.entity.EstateBillPlanRent;
import com.wiiy.estate.preferences.enums.RentBillPlanEnum;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface BillPlanRentService extends IService<EstateBillPlanRent> {

	Result<List<EstateBillPlanRent>> autoGenerate(EstateFeeEnum feeType,
			RentBillPlanEnum rentBillPlan, BillingMethodEnum billingMethod,
			Date startDate, Date endDate, Double roomArea, Double price,
			PriceUnitEnum priceUnit);

	@SuppressWarnings("rawtypes")
	Result save(List<EstateBillPlanRent> billPlanRentList);
	
	@SuppressWarnings("rawtypes")
	Result checkout(List<EstateBillPlanRent> list);
	
	@SuppressWarnings("rawtypes")
	Result checkoutById(Long id, BillPlanStatusEnum billPlanStatus);
	
	@SuppressWarnings("rawtypes")
	Result checkoutById(Long id, BillPlanStatusEnum billPlanStatus,Session session);
	
	@SuppressWarnings("rawtypes")
	Result batchCheckout(String ids, String[] types, boolean autoRemind);

	Integer getRowCount(Filter filter);

	Result<List<EstateBillPlanRent>> getListHql(String string);

	Result getRowCountByFeeType();
}
