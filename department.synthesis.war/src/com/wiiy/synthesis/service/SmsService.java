package com.wiiy.synthesis.service;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.Sms;

/**
 * @author my
 */
public interface SmsService extends IService<Sms> {
	
	Result updateSmsToSended(Sms t);

	Result save(Sms sms, String receiverName);
}
