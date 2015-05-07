package com.wiiy.business.service;


import java.io.OutputStream;

import com.wiiy.commons.service.IService;
import com.wiiy.business.entity.InvestmentProcess;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface InvestmentProcessService extends IService<InvestmentProcess> {

	Result approval(Long id, Long userId, String string);

	Result<InvestmentProcess> print(Long id,OutputStream out);
}
