package com.wiiy.sale.service;

import java.util.List;
import java.util.Map;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.sale.entity.SaleCustomer;

/**
 * @author my
 */
public interface SaleCustomerService extends IService<SaleCustomer> {
	Result save(SaleCustomer customer, String ids);

	Result update(SaleCustomer customer, String ids);
	
	/*public List<Integer> levels();*/

	Map<String, Integer> levels(Boolean type);
}
