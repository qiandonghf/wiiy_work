package com.wiiy.business.service;

import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wiiy.business.entity.BusinessContract;
import com.wiiy.business.entity.BusinessSubjectRent;
import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public interface ContractService extends IService<BusinessContract> {

	Result<String> generateCode(String contractModel);

	Result save(BusinessContract contract, List<BusinessSubjectRent> sessionSubjectRentList);

	Result submitApproval(Long id);

	Result submitSubtract(Long contractId, Long subjectRentId, Date executeTime,Double checkoutMoney,Boolean checkoutNow, String memo);

	Result submitSurrender(Long contractId, Date executeTime,List<Double> checkoutMoney,Boolean checkoutNow, String memo);

	/*Result approval(Long contractId);*/
	
	Result approval(Long contractId,Long approvalUserId);

	Result executeContract(Long contractId);
	
	Result closeContract(Long contractId);

	Result submitRelet(BusinessContract contract);

	Result print(Long id, File template, OutputStream out);

	Result saveRentContract1(BusinessContract contract,Map<String,String[]> roomRent,Map<String,String[]> billPlanRent,Map<String,String[]> deposit);

	Result saveNetContract1(BusinessContract contract, String[] netIds, String[] prices, String[] ips,
			String[] ports, String[] ipPubs, String[] roomStartDates,
			String[] roomEndDates, String[] feeTypes, String[] moneys,
			String[] playPayDates, String[] planStartDates,
			String[] planEndDates, String autocheck);

	Result print(Long id);

	Result<List<BusinessContract>> getListByHql(String string);

	Result getRowCount(Filter filter);


}
