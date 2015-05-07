package com.wiiy.park.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.wiiy.common.dto.BillInvoiceDto;
import com.wiiy.common.preferences.enums.BillInOutEnum;
import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.park.entity.CommonBill;
import com.wiiy.park.entity.BillRemind;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public interface BillService extends IService<CommonBill> {
	
	List getObjectListBySql(String sql);
	
	Integer getRowCount(Filter filter);

	Result apart(Long id, double amount);

	Result chargeoff(Long id);

	Result back(Long id);
	
	String generateNumber(Session session);
	
	String generateNumber();

	Result inInform(String urged, String email, String sms);

	Result outInform(String email, String sms);

	Result arrearAlertStatistics();

	Result measureStatistics();
	
	Result getProjectionResult(Filter filter);
	Long getCustomerCountInBill(BillInOutEnum inOrOut);

	Result<List<CommonBill>> getListByHql(String string);

	Result<BillInvoiceDto> getInvoiceDto(Long id);
	
	Result<BillInvoiceDto> getInvoiceDto(String ids);

	Result invoice(CommonBill dbBean);
	
	Result invoice(CommonBill dbBean,String ids);

	Result remind(BillRemind t);

	Result remindById(Long id);
	
	Result remindByIds(String ids);

	Result pay(String ids, Date parse);
}
