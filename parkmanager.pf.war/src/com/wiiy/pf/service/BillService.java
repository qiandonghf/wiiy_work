package com.wiiy.pf.service;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.pf.dto.BillRentDto;
import com.wiiy.pf.entity.Bill;
import com.wiiy.pf.preferences.enums.DepartmentEnum;

/**
 * @author my
 */
public interface BillService extends IService<Bill> {
	@SuppressWarnings("rawtypes")
	Result getBeanById(Long id,BillRentDto billRentDto);
	
	@SuppressWarnings("rawtypes")
	Result update(Bill bill,DepartmentEnum department);
	
	void getDtoById(String key,DepartmentEnum department,BillRentDto billRentDto);
	
	@SuppressWarnings("rawtypes")
	Result viewById(Long id,BillRentDto billRentDto);
	
	@SuppressWarnings("rawtypes")
	Result getListBySql(String sql);
}
