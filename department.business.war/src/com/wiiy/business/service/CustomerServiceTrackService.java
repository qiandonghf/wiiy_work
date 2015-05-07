package com.wiiy.business.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.business.entity.CustomerService;
import com.wiiy.business.entity.CustomerServiceTrack;

/**
 * @author my
 */
public interface CustomerServiceTrackService extends IService<CustomerServiceTrack> {

	Result save(CustomerServiceTrack customerServiceTrack,
			List<CustomerService> customerServiceList);

}
