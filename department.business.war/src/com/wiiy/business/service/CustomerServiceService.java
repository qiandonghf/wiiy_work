package com.wiiy.business.service;

import java.io.OutputStream;
import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.business.entity.CustomerService;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface CustomerServiceService extends IService<CustomerService> {

	Result<CustomerService> print(Long id, OutputStream out);

	int countYetCustomer();

	List getListBySql(String sql);

}
