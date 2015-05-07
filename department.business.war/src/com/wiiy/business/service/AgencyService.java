package com.wiiy.business.service;

import com.wiiy.commons.service.IService;
import com.wiiy.business.entity.Agency;
import com.wiiy.hibernate.Result;

public interface AgencyService extends IService<Agency>{
	Result<?> importCard();

	Result saveAccount(Long id, String username, String password,Long orgId);

	Result updateAccountPassword(Long id, String password);
}
