package com.wiiy.synthesis.service;

import java.util.List;


import com.wiiy.commons.service.IService;
import com.wiiy.core.entity.User;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.LaborContract;
import com.wiiy.synthesis.entity.LaborContractAtt;

/**
 * @author my
 */
public interface LaborContractService extends IService<LaborContract> {
	Result<LaborContract> save(LaborContract t,List<LaborContractAtt> att);
}
