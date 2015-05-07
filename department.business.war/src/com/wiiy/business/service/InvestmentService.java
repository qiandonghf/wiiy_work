package com.wiiy.business.service;

import java.io.OutputStream;

import com.wiiy.commons.service.IService;
import com.wiiy.core.entity.Approval;
import com.wiiy.business.entity.Investment;
import com.wiiy.business.entity.InvestmentDirector;
import com.wiiy.business.entity.InvestmentRegInfo;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface InvestmentService extends IService<Investment> {

	Result<Investment> saveOrUpdate(Investment investment, InvestmentRegInfo investmentRegInfo, String[] enterpriseTypeIds);

	Result<String> generateCode();

	Result<Investment> update(Investment investment, InvestmentRegInfo investmentRegInfo, String[] enterpriseTypeIds);
	
	Result<Investment> print(Long id, OutputStream out);

	Result submitSettled(Long id);

	Result updateApproval(Approval approval);

	Result approval(Long id,Long userId, String approvalType);

	Result getListByLimitNum(int i);
	
	Result getApprovalList();

	Result getMyApprovalList();

	Result assigneTo(Long prevId, Long id,String type);

}
