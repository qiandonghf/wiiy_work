package com.wiiy.sale.service;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.sale.entity.Residential;

/**
 * @author my
 */
public interface ResidentialService extends IService<Residential> {

	Result changeState(Long id);

	Result getRowCount(Filter filter);
}
