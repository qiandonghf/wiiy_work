package com.wiiy.sale.service;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.sale.entity.RegistrationBook;

/**
 * @author my
 */
public interface RegistrationBookService extends IService<RegistrationBook> {
	public Result changeState(Long id);
}
