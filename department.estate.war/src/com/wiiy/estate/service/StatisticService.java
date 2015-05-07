package com.wiiy.estate.service;

import java.util.Date;
import java.util.List;

import com.wiiy.hibernate.Pager;
import com.wiiy.hibernate.Result;

public interface StatisticService {
	Result<?> buildingGraph();
	
	Result<?> buildingRentGraph();
	
	Result<?> billCost(Date startTime, Date endTime , Date time , String timeType);

	Result<?> billCostByTime(Date time, String type);
	
	Result<?> billCostYearCount();

	Result<?> billCostByCustomer(Long customerId, Date startTime, Date endTime);

	Result<?> billCostByProperty(Date startTime,Date endTime);

	List<Object[]> exportBillByTime(Date startTime,String type);

	List<Object[]> exportBillByCustomer(Long customerId, Date startTime,Date endTime);

	List<Object[]> exportBillByProperty(Date startTime, Date endTime);

	Result<?> billWzubCostByProperty(Date startTime, Date endTime);

	List<Object[]> exportBillWzubByProperty(Date startTime, Date endTime);

	Result<?> billCostByMonth(Date startTime, Date endTime, String string);
}
