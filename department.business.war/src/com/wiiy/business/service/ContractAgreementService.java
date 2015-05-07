package com.wiiy.business.service;

import java.util.List;

import com.wiiy.business.entity.ContractAgreement;
import com.wiiy.business.entity.ContractAgreementAtt;
import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface ContractAgreementService extends IService<ContractAgreement> {
	Result<ContractAgreement> save(ContractAgreement t,List<ContractAgreementAtt> sessionContractAgreementAttList);
}
