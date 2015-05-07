package com.wiiy.business.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.business.entity.BusinessCustomer;
import com.wiiy.business.entity.DataReportToCustomer;
import com.wiiy.business.preferences.enums.CustomerReportStatusEnum;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface DataReportToCustomerService extends IService<DataReportToCustomer> {

	Result<List<BusinessCustomer>> getCustomerListByReportId(Long reportId);

	Result report(Long id, String propertyIds, String propertyValues);

	Result<Integer> countByFilter(Filter filter);

	Result report(Long id, CustomerReportStatusEnum status, String propertyIds,
			String propertyValues);

	Result reportPub(Long id);

	Result back(Long id, String suggestion);
}
