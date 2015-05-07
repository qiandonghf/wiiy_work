package com.wiiy.business.service;

import java.util.List;

import com.wiiy.business.entity.BusinessSubjectRent;
import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface SubjectRentService extends IService<BusinessSubjectRent> {

	@SuppressWarnings("rawtypes")
	Result<List> getListBySql(String string);
}
