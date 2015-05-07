package com.wiiy.park.service;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.park.entity.BillRemind;

/**
 * @author my
 */
public interface BillRemindService extends IService<BillRemind> {

	Result getRowCountByFeeType(String department);

	Result remind(Long id);
}
