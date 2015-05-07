package com.wiiy.sale.service;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.sale.entity.SaleProject;

/**
 * @author my
 */
public interface SaleProjectService extends IService<SaleProject> {
	Result<String> generateCode();
}
