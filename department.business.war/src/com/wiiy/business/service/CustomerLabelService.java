package com.wiiy.business.service;

import java.util.List;

import com.wiiy.business.entity.CustomerLabel;
import com.wiiy.commons.service.IService;

/**
 * @author my
 */
public interface CustomerLabelService extends IService<CustomerLabel> {
	
	List<CustomerLabel> getCustomerLabelsByCustomerId(Long id);
}
