package com.wiiy.business.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.wiiy.business.entity.BusinessBillPlanRent;
import com.wiiy.business.preferences.enums.RentBillPlanEnum;
import com.wiiy.common.preferences.enums.BillPlanStatusEnum;
import com.wiiy.common.preferences.enums.BillingMethodEnum;
import com.wiiy.common.preferences.enums.BusinessFeeEnum;
import com.wiiy.common.preferences.enums.PriceUnitEnum;
import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface BillPlanRentService extends IService<BusinessBillPlanRent> {

	Result<List<BusinessBillPlanRent>> autoGenerate(BusinessFeeEnum feeType,
			RentBillPlanEnum rentBillPlan, BillingMethodEnum billingMethod,
			Date startDate, Date endDate, Double roomArea, Double price,
			PriceUnitEnum priceUnit);

	@SuppressWarnings("rawtypes")
	Result save(List<BusinessBillPlanRent> billPlanRentList);
	
	@SuppressWarnings("rawtypes")
	Result checkout(List<BusinessBillPlanRent> list);
	
	@SuppressWarnings("rawtypes")
	Result checkoutById(Long id, BillPlanStatusEnum billPlanStatus);
	
	@SuppressWarnings("rawtypes")
	Result checkoutById(Long id, BillPlanStatusEnum billPlanStatus,Session session);
	
	@SuppressWarnings("rawtypes")
	Result batchCheckout(String ids, String[] types, boolean autoRemind);

	Integer getRowCount(Filter filter);

	Result<List<BusinessBillPlanRent>> getListHql(String string);

	Result getRowCountByFeeType(String dates);
}
