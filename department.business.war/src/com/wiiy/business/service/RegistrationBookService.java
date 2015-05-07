package com.wiiy.business.service;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.business.entity.InvestmentBook;

/**
 * @author my
 */
public interface RegistrationBookService extends IService<InvestmentBook> {
	public Result changeState(Long id);

	public Result getRowCount(Filter filter);
}
