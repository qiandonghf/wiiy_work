package com.wiiy.business.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.business.entity.DataProperty;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface DataPropertyService extends IService<DataProperty> {

	Result up(Long id);

	Result down(Long id);

	Result<List<DataProperty>>  getListByConfigIds(String ids);
}
