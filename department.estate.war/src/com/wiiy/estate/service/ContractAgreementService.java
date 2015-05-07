package com.wiiy.estate.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.estate.entity.EstateContractAgreement;
import com.wiiy.estate.entity.EstateContractAgreementAtt;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface ContractAgreementService extends IService<EstateContractAgreement> {
	Result<EstateContractAgreement> save(EstateContractAgreement t,List<EstateContractAgreementAtt> sessionContractAgreementAttList);
}
