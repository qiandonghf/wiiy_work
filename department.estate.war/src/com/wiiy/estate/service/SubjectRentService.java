package com.wiiy.estate.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.estate.entity.EstateSubjectRent;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface SubjectRentService extends IService<EstateSubjectRent> {

	@SuppressWarnings("rawtypes")
	Result<List> getListBySql(String string);
}
