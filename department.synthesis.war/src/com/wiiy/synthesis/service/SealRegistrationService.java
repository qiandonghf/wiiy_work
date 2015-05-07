package com.wiiy.synthesis.service;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.SealRegistration;

/**
 * @author my
 */
public interface SealRegistrationService extends IService<SealRegistration> {

	Result save(SealRegistration sealRegistration, String attAddress);

	Result update(SealRegistration dbBean, String attAddress);

	Result agree(Long id);

	Result disagree(Long id);
}
