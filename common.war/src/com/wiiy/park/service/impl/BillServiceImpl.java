package com.wiiy.park.service.impl;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.common.activator.ProjectActivator;
import com.wiiy.common.dto.BillInformDto;
import com.wiiy.common.dto.BillInvoiceDto;
import com.wiiy.common.dto.BillTypeInvoiceDto;
import com.wiiy.common.dto.StatisticDto;
import com.wiiy.common.dto.StatisticGroupDto;
import com.wiiy.common.preferences.R;
import com.wiiy.common.preferences.enums.BillInOutEnum;
import com.wiiy.common.preferences.enums.BillPlanStatusEnum;
import com.wiiy.common.preferences.enums.BillRemindStatusEnum;
import com.wiiy.common.preferences.enums.BillStatusEnum;
import com.wiiy.common.preferences.enums.InvoiceTypeEnum;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.external.service.SMSPubService;
import com.wiiy.external.service.SysEmailSenderPubService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.ModulePrefixNamingStrategy;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.park.dao.BillDao;
import com.wiiy.park.entity.BillRemind;
import com.wiiy.park.entity.BillType;
import com.wiiy.park.entity.CommonBill;
import com.wiiy.park.service.BillService;

/**
 * @author my
 */
public class BillServiceImpl implements BillService{
	
	private BillDao billDao;
	
	public void setBillDao(BillDao billDao) {
		this.billDao = billDao;
	}

	@Override
	public Result<CommonBill> save(CommonBill t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(ProjectActivator.getSessionUser().getRealName());
			t.setCreatorId(ProjectActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			billDao.save(t);
			return Result.success(R.Bill.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CommonBill.class)));
		} catch (Exception e) {
			return Result.failure(R.Bill.SAVE_FAILURE);
		}
	}

	@Override
	public Result pay(String ids,Date payTime) {
		Session session = null;
		Transaction tr = null;
		try {
			session = billDao.openSession();
			tr = session.beginTransaction();
			/*String sqlForBillTypeId="select id from crm_bill_type where parent_id = (select id from crm_bill_type where type_name = '公共设施')";//查出公共设施下面的费用类型的ids
			List<Object> typeIds = session.createSQLQuery(sqlForBillTypeId).list();*/
			List<CommonBill> billList = (List<CommonBill>) session.createQuery("from CommonBill where id in ("+ids+")").list();
			for (CommonBill bill : billList) {
				bill.setStatus(BillStatusEnum.FULLPAID);
				bill.setPayTime(payTime);
				session.update(bill);
				/*for(Object id:typeIds){
					if(bill.getBillTypeId()==((BigInteger)id).longValue()){
						long facilityId = bill.getBillPlanId();
						BillPlanFacility billPlanFacility = billPlanFacilityService.getBeanById(facilityId).getValue();
						if(billPlanFacility!=null){
							billPlanFacility.setLastPayDate(new Date());
							billPlanFacility.setPaidFee(bill.getFactPayment());
							billPlanFacilityService.update(billPlanFacility);
						}
					}
				}*/
			}
			tr.commit();
			return Result.success("结算成功");
		} catch (Exception e) {
			tr.rollback();
			return Result.failure("结算失败");
		} finally {
			session.close();
		}
	}
		
	@Override
	public Result<CommonBill> delete(CommonBill t) {
		try {
			billDao.delete(t);
			return Result.success(R.Bill.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.Bill.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CommonBill> deleteById(Serializable id) {
		try {
			billDao.deleteById(id);
			return Result.success(R.Bill.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.Bill.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CommonBill> deleteByIds(String ids) {
		try {
			billDao.deleteByIds(ids);
			return Result.success(R.Bill.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.Bill.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CommonBill> update(CommonBill t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(ProjectActivator.getSessionUser().getRealName());
			t.setModifierId(ProjectActivator.getSessionUser().getId());
			billDao.update(t);
			return Result.success(R.Bill.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CommonBill.class)));
		} catch (Exception e) {
			return Result.failure(R.Bill.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<CommonBill> getBeanById(Serializable id) {
		try {
			return Result.value(billDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.Bill.LOAD_FAILURE);
		}
	}

	@Override
	public Result<CommonBill> getBeanByFilter(Filter filter) {
		try {
			return Result.value(billDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Bill.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CommonBill>> getList() {
		try {
			return Result.value(billDao.getList());
		} catch (Exception e) {
			return Result.failure(R.Bill.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CommonBill>> getListByFilter(Filter filter) {
		try {
			return Result.value(billDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Bill.LOAD_FAILURE);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getObjectListBySql(String sql) {
		return billDao.getObjectListBySql(sql);
	}

	@Override
	public Integer getRowCount(Filter filter) {
		return billDao.getRowCount(filter);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result apart(Long id, double amount) {
		Session session = null;
		Transaction tr = null;
		try {
			session = billDao.openSession();
			tr = session.beginTransaction();
			CommonBill bill = (CommonBill) session.get(CommonBill.class,id);
			bill.setBillSplit(1);
			CommonBill newBill = new CommonBill();
			newBill.setNumber(generateNumber(session));
			BeanUtil.copyProperties(bill, newBill);
			bill.setFactPayment(bill.getFactPayment()-amount);
			bill.setTotalAmount(bill.getTotalAmount()-amount);
			newBill.setTotalAmount(amount);
			newBill.setFactPayment(amount);
			newBill.setBillSplit(1);
			newBill.setId(null);
			session.update(bill);
			session.save(newBill);
			tr.commit();
			return Result.success("拆分成功");
		} catch (Exception e) {
			tr.rollback();
			return Result.failure("拆分失败");
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result chargeoff(Long id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = billDao.openSession();
			tr = session.beginTransaction();
			CommonBill bill = (CommonBill) session.get(CommonBill.class, id);
			bill.setStatus(BillStatusEnum.CHARGEOFF);
			session.update(bill);
			tr.commit();
			return Result.success("核销成功");
		} catch (Exception e) {
			tr.rollback();
			return Result.failure("核销失败");
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result back(Long id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = billDao.openSession();
			tr = session.beginTransaction();
			CommonBill bill = (CommonBill) session.get(CommonBill.class, id);
			String table = bill.getBillPlanTableName();
			Long playId = bill.getBillPlanId();
			Result result = Result.success();
			if(playId!=null && table!=null){
				session.createSQLQuery("update "+table+" set status = '"+BillPlanStatusEnum.UNCHECK+"' where id = "+playId).executeUpdate();
				session.delete(bill);
				tr.commit();
				result.setMsg("退回成功");
			} else {
				result.setMsg("此账单无对应资金计划 无法退回");
			}
			return result;
		} catch (Exception e) {
			tr.rollback();
			return Result.failure("退回失败");
		} finally {
			session.close();
		}
	}

	@Override
	public String generateNumber(Session session) {
		String prefix = "账单";
		String suffix = "号";
		CommonBill bill = (CommonBill) session.createQuery("from CommonBill order by number desc").setMaxResults(1).uniqueResult();
		String number = null;
		if(bill==null || bill.getNumber()==null){
			number = prefix + 10000001 + suffix;
		} else {
			String preNumber = bill.getNumber();
			preNumber = preNumber.replace(prefix, "");
			preNumber = preNumber.replace(suffix, "");
			Long num = Long.parseLong(preNumber)+1;
			number = prefix+num+suffix;
		}
		return number;
	}

	@Override
	public String generateNumber() {
		Session session = null;
		try {
			session = billDao.openSession();
			return generateNumber(session);
		} catch (Exception e) {
			return null;
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result inInform(String urged, String email, String sms) {
		Filter filter = new Filter(CommonBill.class).eq("status", BillStatusEnum.UNPAID).eq("inOut", BillInOutEnum.IN);
		if(urged!=null && urged.equals("NO")) {
			filter.eq("urged", BooleanEnum.NO);
		}
		List<CommonBill> billList = billDao.getListByFilter(filter);
		Session session = null;
		Transaction tr = null;
		try {
			session = billDao.openSession();
			tr = session.beginTransaction();
			String smsTemplate = ProjectActivator.getAppConfig().getConfig("billInform").getParameter("in");
			inform(billList, session, sms, email, smsTemplate);
			tr.commit();
			return Result.success("缴费通知成功");
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure("缴费通知失败");
		} finally {
			session.close();
		}
	}
	
	private void inform(List<CommonBill> billList, Session session, String sms, String email,String msgTemplate){
		Map<Long,BillInformDto> dtoMap = new HashMap<Long,BillInformDto>();
		
		//调整...
		/*for (CommonBill bill : billList) {
			if(bill.getUrged()==null || bill.getUrged().equals(BooleanEnum.NO)) {
				bill.setUrged(BooleanEnum.YES);
				session.update(bill);
			}
			if(!dtoMap.containsKey(bill.getCustomer().getCustomerId())) {
				dtoMap.put(bill.getCustomer().getCustomerId(),new BillInformDto(bill.getCustomer().getCustomerId(),bill.getCustomer().getName()));
			}
			dtoMap.get(bill.getCustomer().getCustomerId()).add(bill);
		}*/
		SMSPubService smsPubService = ProjectActivator.getService(SMSPubService.class);
		SysEmailSenderPubService sysEmailSenderPubService = ProjectActivator.getService(SysEmailSenderPubService.class);
		boolean sendSms = smsPubService!=null && sms!=null && sms.equalsIgnoreCase("yes");
		boolean sendEmail = sysEmailSenderPubService!=null && email!=null && email.equalsIgnoreCase("yes");
		NumberFormat format = new DecimalFormat("#0.00");
		/**
		 * 短信邮件
		 */
		/*for (BillInformDto dto : dtoMap.values()) {
			Contect contect = (Contect) session.createQuery("from Contect where customerId = "+dto.getCustomerId()+" and main = '"+BooleanEnum.YES+"'").uniqueResult();
			if(contect==null) continue;
			String content = msgTemplate.replace("${customerName}", dto.getCustomerName());
			content = content.replace("${total}", format.format(dto.getTotal()));
			content = content.replace("${billTotal}", dto.getBillList().size()+"");
			String billTypes = "";
			for (BillType billType : dto.getBillTypeMap().values()) {
				billTypes += billType.getTypeName()+",";
			}
			billTypes = StringUtil.trim(billTypes, ",");
			content = content.replace("${billTypes}", billTypes);
			if(sendSms){
				SMSDto smsDto = new SMSDto();
				smsDto.setContent(content);
				smsDto.setSendTime(new Date());
				smsDto.setTimeType(SendTimeTypeEnum.NOW);
				SMSReceiverDto r = new SMSReceiverDto();
				r.setPhone(contect.getMobile());
				r.setReceiverName(dto.getCustomerName());
				List<SMSReceiverDto> receiverList = new ArrayList<SMSReceiverDto>();
				receiverList.add(r);
				smsDto.setReceiverList(receiverList);
				smsDto.setCreator(ProjectActivator.getSessionUser().getRealName());
				smsDto.setCreatorId(ProjectActivator.getSessionUser().getId());
				smsPubService.send(smsDto, session);
			}
			if(sendEmail){
				EmailDto emailDto = new EmailDto();
				emailDto.setContent(content);
				emailDto.setSendTime(new Date());
				emailDto.setTimeType(SendTimeTypeEnum.NOW);
				List<EmailReceiverDto> receiverList = new ArrayList<EmailReceiverDto>();
				EmailReceiverDto emailReceiverDto = new EmailReceiverDto();
				emailReceiverDto.setReceiverName(dto.getCustomerName());
				emailReceiverDto.setAddress(contect.getEmail());
				receiverList.add(emailReceiverDto);
				emailDto.setReceiverList(receiverList);
				sysEmailSenderPubService.send(emailDto, session);
			}
		}*/
	}

	@Override
	public Result outInform(String email, String sms) {
		Filter filter = new Filter(CommonBill.class).eq("status", BillStatusEnum.UNPAID).eq("inOut", BillInOutEnum.OUT);
		List<CommonBill> billList = billDao.getListByFilter(filter);
		Session session = null;
		Transaction tr = null;
		try {
			session = billDao.openSession();
			tr = session.beginTransaction();
			String smsTemplate = ProjectActivator.getAppConfig().getConfig("billInform").getParameter("out");
			inform(billList, session, sms, email, smsTemplate);
			tr.commit();
			return Result.success("退款通知成功");
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure("退款通知失败");
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result arrearAlertStatistics() {
		//SELECT t.type_name,SUM(b.factPayment) FROM business_business_bill b LEFT JOIN business_business_bill_type t ON b.bill_type_id = t.id WHERE b.planPayTime < NOW() GROUP BY t.type_name;
		String sql = "SELECT t.type_name,SUM(b.factPayment) " +
				"FROM "+ModulePrefixNamingStrategy.classToTableName(CommonBill.class)+" b " +
				"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+" t " +
				"ON b.bill_type_id = t.id " +
				"WHERE b.status = 'UNPAID' and b.planPayTime < '"+new Date()+"' " +
				"GROUP BY t.type_name;";
		List<Object> list = billDao.getObjectListBySql(sql);
		List<StatisticDto> dtoList = new ArrayList<StatisticDto>();
		for (Object object : list) {
			Object[] objects = (Object[])object;
			String name = String.valueOf(objects[0]);
			double sum = 0;
			if(objects[1]!=null){
				 sum = (Double)objects[1];
			}
			StatisticDto dto = new StatisticDto();
			dto.setName(name);
			dto.setdValue(sum);
			dtoList.add(dto);
		}
		return Result.value(dtoList);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result measureStatistics() {
		//SELECT t.type_name,SUM(b.factPayment) FROM business_business_bill b LEFT JOIN business_business_bill_type t ON b.bill_type_id = t.id WHERE b.planPayTime < NOW() GROUP BY t.type_name;
		String sql_in = "SELECT t.type_name,SUM(b.factPayment) " +
				"FROM "+ModulePrefixNamingStrategy.classToTableName(CommonBill.class)+" b " +
				"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+" t " +
				"ON b.bill_type_id = t.id " +
				"WHERE b.in_out = '"+BillInOutEnum.IN+"' AND b.status = '" + BillStatusEnum.UNPAID + "'" +
				"GROUP BY t.type_name;";
		List<Object> list_in = billDao.getObjectListBySql(sql_in);
		List<StatisticDto> dtoList_in = new ArrayList<StatisticDto>();
		for (Object object : list_in) {
			Object[] objects = (Object[])object;
			String name = String.valueOf(objects[0]);
			double sum = 0;
			if(objects[1]!=null){
				 sum = (Double)objects[1];
			}
			StatisticDto dto = new StatisticDto();
			dto.setName(name);
			dto.setdValue(sum);
			dtoList_in.add(dto);
		}
		String sql_out = "SELECT t.type_name,SUM(b.factPayment) " +
				"FROM "+ModulePrefixNamingStrategy.classToTableName(CommonBill.class)+" b " +
				"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+" t " +
				"ON b.bill_type_id = t.id " +
				"WHERE b.in_out = '"+BillInOutEnum.OUT+"' AND b.status = '" + BillStatusEnum.UNPAID + "'" +
				"GROUP BY t.type_name;";
		List<Object> list_out = billDao.getObjectListBySql(sql_out);
		List<StatisticDto> dtoList_out = new ArrayList<StatisticDto>();
		for (Object object : list_out) {
			Object[] objects = (Object[])object;
			String name = String.valueOf(objects[0]);
			double sum = 0;
			if(objects[1]!=null){
				 sum = (Double)objects[1];
			}
			StatisticDto dto = new StatisticDto();
			dto.setName(name);
			dto.setdValue(sum);
			dtoList_out.add(dto);
		}
		List<StatisticGroupDto> groupList = new ArrayList<StatisticGroupDto>();
		
		StatisticGroupDto group_in = new StatisticGroupDto();
		group_in.setName("IN");
		group_in.setList(dtoList_in);
		groupList.add(group_in);
		
		StatisticGroupDto group_out = new StatisticGroupDto();
		group_out.setName("OUT");
		group_out.setList(dtoList_out);
		groupList.add(group_out);
		
		return Result.value(groupList);
	}
	
	public Result getProjectionResult(Filter filter){
		return Result.value(billDao.getProjectionResult(filter));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getCustomerCountInBill(BillInOutEnum inOrOut) {
		Session session = null;
		Transaction tr = null;
		try {
			session = billDao.openSession();
			tr = session.beginTransaction();
			String sql = "SELECT COUNT(*) FROM CRM_BILL WHERE IN_OUT = '"+inOrOut+"'GROUP BY CUSTOMER_ID";
			List<CommonBill> bills = billDao.getObjectListBySql(sql);
			Long customerCount  = (long) bills.size();
			tr.commit();
			return customerCount;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Result<List<CommonBill>> getListByHql(String hql) {
		try {
			return Result.value(billDao.getListByHql(hql));
		} catch (Exception e) {
			return Result.failure(R.Bill.LOAD_FAILURE);
		}
	}

	@Override
	public Result<BillInvoiceDto> getInvoiceDto(Long id) {
		Session session = null;
		try {
			session = billDao.openSession();
			CommonBill bill = (CommonBill)session.get(CommonBill.class, id);
			String customerInfoTabel = "";
			if(bill!=null && bill.getTypeId()!=null){
				customerInfoTabel = bill.getTypeId().split("_")[0];
				if("business".equalsIgnoreCase(customerInfoTabel)){					
					customerInfoTabel = customerInfoTabel+"_customer_info";
				}else{
					customerInfoTabel = customerInfoTabel+"_"+customerInfoTabel+"_customer_info";
				}
			}
			
			String sql = "select b.id,b.factPayment,b.penalty,b.checkoutTime,b.feeStartTime,b.feeEndTime,bt.type_name,b.customerName,ci.tax_number_g "; 
			sql += "from park_common_bill b "; 
			sql += "LEFT JOIN "+customerInfoTabel+" ci "; 
			sql += "ON b.customer_id=ci.id ";
			sql += "LEFT JOIN park_bill_type bt ";
			sql += "ON bt.id = b.bill_type_id ";
			sql += "where b.id = "+id;
			Object object = session.createSQLQuery(sql).list().get(0);
			BillInvoiceDto dto = new BillInvoiceDto();
			List<BillTypeInvoiceDto> dtos = new ArrayList<BillTypeInvoiceDto>();
			if(object!=null){
				Object[] obj = (Object[])object;
				dto.setId(Long.valueOf(obj[0].toString()));
				BillTypeInvoiceDto billTypeInvoiceDto = new BillTypeInvoiceDto();
				billTypeInvoiceDto.setFactPayment(Double.valueOf(obj[1].toString()));
				billTypeInvoiceDto.setPenalty(Double.valueOf(obj[2].toString()));
				dto.setPenalty(Double.valueOf(obj[2].toString()));
				billTypeInvoiceDto.setMoney(billTypeInvoiceDto.getFactPayment()+billTypeInvoiceDto.getPenalty());
				billTypeInvoiceDto.setCheckoutTime((Date)obj[3]);
				dto.setCheckoutTime((Date)obj[3]);
				billTypeInvoiceDto.setFeeStartTime((Date)obj[4]);
				billTypeInvoiceDto.setFeeEndTime((Date)obj[5]);
				billTypeInvoiceDto.setTypeName(obj[6].toString());
				dtos.add(billTypeInvoiceDto);
				dto.setBillTypeDto(dtos);
				dto.setCustomerName(obj[7].toString());
				dto.setTaxNumberG(obj[8].toString());
			}
			return Result.value(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bill.LOAD_FAILURE);
		} finally {
			session.close();
		}
	}
	
	@Override
	public Result<BillInvoiceDto> getInvoiceDto(String ids) {
		Session session = null;
		try {
			session = billDao.openSession();
			//待检查
			
			Long id = Long.valueOf(ids.split(",")[0]);
			CommonBill bill = (CommonBill)session.get(CommonBill.class,id);
			String customerInfoTabel = "";
			if(bill!=null && bill.getTypeId()!=null){
				customerInfoTabel = bill.getTypeId().split("_")[0];
				if("business".equalsIgnoreCase(customerInfoTabel)){					
					customerInfoTabel = customerInfoTabel+"_customer_info";
				}else{
					customerInfoTabel = customerInfoTabel+"_"+customerInfoTabel+"_customer_info";
				}
			}
			String sql = "select b.id,b.factPayment,b.penalty,b.checkoutTime,b.feeStartTime,b.feeEndTime,bt.type_name,b.customerName,ci.tax_number_g "; 
			sql += "from park_common_bill b "; 
			if(customerInfoTabel!=null && !"".equals(customerInfoTabel)){
				sql += "LEFT JOIN "+customerInfoTabel+" ci "; 
			}else{
				return Result.failure(R.Bill.LOAD_FAILURE);
			}
			sql += "ON b.customer_id=ci.id ";
			sql += "LEFT JOIN park_bill_type bt ";
			sql += "ON bt.id = b.bill_type_id ";
			sql += "where b.id in ("+ids+")";
			List<Object> objects = session.createSQLQuery(sql).list();
			BillInvoiceDto dto = new BillInvoiceDto();
			List<BillTypeInvoiceDto> dtos = new ArrayList<BillTypeInvoiceDto>();
			if(objects!=null){
				for (Object object2 : objects) {
					Object[] obj = (Object[])object2;
					dto.setId(Long.valueOf(obj[0].toString()));
					BillTypeInvoiceDto billTypeInvoiceDto = new BillTypeInvoiceDto();
					billTypeInvoiceDto.setFactPayment(Double.valueOf(obj[1].toString()));
					billTypeInvoiceDto.setPenalty(Double.valueOf(obj[2].toString()));
					dto.setPenalty(Double.valueOf(obj[2].toString()));
					billTypeInvoiceDto.setMoney(billTypeInvoiceDto.getFactPayment()+billTypeInvoiceDto.getPenalty());
					billTypeInvoiceDto.setCheckoutTime((Date)obj[3]);
					dto.setCheckoutTime((Date)obj[3]);
					billTypeInvoiceDto.setFeeStartTime((Date)obj[4]);
					billTypeInvoiceDto.setFeeEndTime((Date)obj[5]);
					billTypeInvoiceDto.setTypeName(obj[6].toString());
					dtos.add(billTypeInvoiceDto);
					dto.setCustomerName(obj[7].toString());
					dto.setTaxNumberG(obj[8].toString());
					if(dto.getMoney()!=null){
						dto.setMoney(dto.getMoney()+billTypeInvoiceDto.getMoney());
					}else{
						dto.setMoney(billTypeInvoiceDto.getMoney());
					}
				}
				dto.setBillTypeDto(dtos);
			}
			return Result.value(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bill.LOAD_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result invoice(CommonBill t) {
		try {
			if(BooleanEnum.YES.equals(t.getInvoice())){
				t.setInvoiceType(InvoiceTypeEnum.HASBILLING);
			}
			t.setModifyTime(new Date());
			t.setModifier(ProjectActivator.getSessionUser().getRealName());
			t.setModifierId(ProjectActivator.getSessionUser().getId());
			billDao.update(t);
			return Result.success("开票成功");
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure("开票失败");
		}
	}
	@Override
	public Result invoice(CommonBill dbBean,String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = billDao.openSession();
			tr = session.beginTransaction();
			List<CommonBill> list = session.createQuery("from CommonBill where id in ("+ids+")").list();
			if(list!=null && list.size()>0){
				for (CommonBill commonBill : list) {
					Long id = commonBill.getId();
					BeanUtil.copyProperties(dbBean, commonBill);
					commonBill.setId(id);
					if(BooleanEnum.YES.equals(commonBill.getInvoice())){
						commonBill.setInvoiceType(InvoiceTypeEnum.HASBILLING);
						commonBill.setModifyTime(new Date());
						commonBill.setModifier(ProjectActivator.getSessionUser().getRealName());
						commonBill.setModifierId(ProjectActivator.getSessionUser().getId());
					}
					session.update(commonBill);
				}
			}
			tr.commit();
			return Result.success("开票成功");
		}catch(Exception e){
			e.printStackTrace();
			tr.rollback();
			return Result.failure("开票失败");
		} finally {
			if(session!=null){
				session.close();
			}
		}
	}

	@Override
	public Result remind(BillRemind t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = billDao.openSession();
			tr = session.beginTransaction();
			t.setRemindStatus(BillRemindStatusEnum.UNCALLEDBILL);
			t.setCreateTime(new Date());
			t.setCreator(ProjectActivator.getSessionUser().getRealName());
			t.setCreatorId(ProjectActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			session.save(t);
			tr.commit();
			return Result.success("催缴成功");
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure("催缴失败");
		} finally {
			if(session!=null){
				session.close();
			}
		}
	}

	@Override
	public Result remindById(Long id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = billDao.openSession();
			tr = session.beginTransaction();
			CommonBill dbBean = (CommonBill)session.get(BillRemind.class, id);
			BillRemind t = new BillRemind();
			BeanUtil.copyProperties(dbBean, t);
			
			t.setRemindStatus(BillRemindStatusEnum.UNCALLEDBILL);
			t.setCreateTime(new Date());
			t.setCreator(ProjectActivator.getSessionUser().getRealName());
			t.setCreatorId(ProjectActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			session.save(t);
			tr.commit();
			return Result.success("催缴成功");
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure("催缴失败");
		} finally {
			if(session!=null){
				session.close();
			}
		}
		
	}

	@Override
	public Result remindByIds(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = billDao.openSession();
			tr = session.beginTransaction();
			List<CommonBill> list = (List<CommonBill>)session.createQuery("from CommonBill where id in ("+ids+")").list();
			if(list!=null && list.size()>0){
				for (CommonBill commonBill : list) {
					BillRemind t = new BillRemind();
					BeanUtil.copyProperties(commonBill, t);
					t.setRemindStatus(BillRemindStatusEnum.UNCALLEDBILL);
					t.setCreateTime(new Date());
					t.setCreator(ProjectActivator.getSessionUser().getRealName());
					t.setCreatorId(ProjectActivator.getSessionUser().getId());
					t.setModifyTime(t.getCreateTime());
					t.setModifier(t.getCreator());
					t.setModifierId(t.getCreatorId());
					t.setEntityStatus(EntityStatus.NORMAL);
					session.save(t);
				}
			}
			tr.commit();
			return Result.success("催缴成功");
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure("催缴失败");
		} finally {
			if(session!=null){
				session.close();
			}
		}
	}
}
