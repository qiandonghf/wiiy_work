package com.wiiy.estate.service.impl;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.business.entity.BusinessCustomer;
import com.wiiy.business.entity.Contect;
import com.wiiy.common.preferences.enums.BillInOutEnum;
import com.wiiy.common.preferences.enums.BillPlanStatusEnum;
import com.wiiy.common.preferences.enums.BillStatusEnum;
import com.wiiy.common.preferences.enums.BillingMethodEnum;
import com.wiiy.common.preferences.enums.EstateFeeEnum;
import com.wiiy.common.preferences.enums.InvoiceTypeEnum;
import com.wiiy.common.preferences.enums.PriceUnitEnum;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dao.BillPlanRentDao;
import com.wiiy.estate.dto.StatisticDto;
import com.wiiy.estate.entity.EstateBillPlanRent;
import com.wiiy.estate.entity.EstateContract;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.preferences.enums.RentBillPlanEnum;
import com.wiiy.estate.service.BillPlanRentService;
import com.wiiy.estate.util.BillPlanGenerateUtil;
import com.wiiy.external.service.SMSPubService;
import com.wiiy.external.service.dto.SMSDto;
import com.wiiy.external.service.dto.SMSReceiverDto;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.ModulePrefixNamingStrategy;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.park.entity.BillType;
import com.wiiy.park.entity.CommonBill;
import com.wiiy.park.service.BillService;
import com.wiiy.park.service.BillTypeService;

/**
 * @author my
 */
public class BillPlanRentServiceImpl implements BillPlanRentService{
	
	private BillPlanRentDao billPlanRentDao;
	private BillTypeService billTypeService;
	private BillService billService;
	public void setBillPlanRentDao(BillPlanRentDao billPlanRentDao) {
		this.billPlanRentDao = billPlanRentDao;
	}

	public void setBillTypeService(BillTypeService billTypeService) {
		this.billTypeService = billTypeService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	@Override
	public Result<EstateBillPlanRent> save(EstateBillPlanRent t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			billPlanRentDao.save(t);
			return Result.success(R.EstateBillPlanRent.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateBillPlanRent.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillPlanRent.SAVE_FAILURE);
		}
	}

	@Override
	public Result<EstateBillPlanRent> delete(EstateBillPlanRent t) {
		try {
			billPlanRentDao.delete(t);
			return Result.success(R.EstateBillPlanRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateBillPlanRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateBillPlanRent> deleteById(Serializable id) {
		try {
			billPlanRentDao.deleteById(id);
			return Result.success(R.EstateBillPlanRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateBillPlanRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateBillPlanRent> deleteByIds(String ids) {
		try {
			billPlanRentDao.deleteByIds(ids);
			return Result.success(R.EstateBillPlanRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateBillPlanRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateBillPlanRent> update(EstateBillPlanRent t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			billPlanRentDao.update(t);
			return Result.success(R.EstateBillPlanRent.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateBillPlanRent.class)));
		} catch (Exception e) {
			return Result.failure(R.EstateBillPlanRent.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<EstateBillPlanRent> getBeanById(Serializable id) {
		try {
			return Result.value(billPlanRentDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.EstateBillPlanRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EstateBillPlanRent> getBeanByFilter(Filter filter) {
		try {
			return Result.value(billPlanRentDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.EstateBillPlanRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateBillPlanRent>> getList() {
		try {
			return Result.value(billPlanRentDao.getList());
		} catch (Exception e) {
			return Result.failure(R.EstateBillPlanRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateBillPlanRent>> getListByFilter(Filter filter) {
		try {
			return Result.value(billPlanRentDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.EstateBillPlanRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateBillPlanRent>> autoGenerate(EstateFeeEnum feeType,
			RentBillPlanEnum rentBillPlan, BillingMethodEnum billingMethod,
			Date startDate, Date endDate, Double roomArea, Double price,
			PriceUnitEnum priceUnit) {
		Result<List<EstateBillPlanRent>> result = BillPlanGenerateUtil.generate(rentBillPlan, billingMethod, startDate, endDate, roomArea, price, priceUnit);
		if(result.isSuccess())
		for (EstateBillPlanRent billPlanRent : result.getValue()) {
			billPlanRent.setFeeType(feeType);
			billPlanRent.setAmount(roomArea);
			billPlanRent.setPrice(price+priceUnit.getTitle()+"("+rentBillPlan.getTitle()+")");
			billPlanRent.setPlanPayDate(billPlanRent.getEndDate());
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result save(List<EstateBillPlanRent> billPlanRentList) {
		Session session = null;
		Transaction tr = null;
		try {
			session = billPlanRentDao.openSession();
			tr = session.beginTransaction();
			for (EstateBillPlanRent billPlanRent : billPlanRentList) {
				billPlanRent.setCreateTime(new Date());
				billPlanRent.setCreator(EstateActivator.getSessionUser().getRealName());
				billPlanRent.setCreatorId(EstateActivator.getSessionUser().getId());
				billPlanRent.setModifyTime(billPlanRent.getCreateTime());
				billPlanRent.setModifier(billPlanRent.getCreator());
				billPlanRent.setModifierId(billPlanRent.getCreatorId());
				billPlanRent.setEntityStatus(EntityStatus.NORMAL);
				session.save(billPlanRent);
			}
			tr.commit();
			return Result.failure(R.EstateBillPlanRent.SAVE_SUCCESS);
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.EstateBillPlanRent.SAVE_FAILURE);
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result checkoutById(Long id,BillPlanStatusEnum billPlanStatus) {
		Session session = null;
		Transaction tr = null;
		try {
			session = billPlanRentDao.openSession();
			tr = session.beginTransaction();
			checkoutById(id, billPlanStatus, session);
			tr.commit();
			return Result.failure("出账成功");
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure("出账失败");
		} finally {
			session.close();
		}
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Result checkoutById(Long id,BillPlanStatusEnum billPlanStatus,Session session) {
		EstateBillPlanRent billPlanRent = (EstateBillPlanRent) session.get(EstateBillPlanRent.class,id);
		EstateContract contract = (EstateContract) session.get(EstateContract.class, billPlanRent.getContractId());
		BillType topType = billTypeService.getBillType("物业").getValue();
		Map<EstateFeeEnum,Long> typeMap = new HashMap<EstateFeeEnum,Long>();
		for (EstateFeeEnum rentBillPlanFeeEnum : EstateFeeEnum.values()) {
			BillType billType = new BillType();
			billType.setTypeName(rentBillPlanFeeEnum.getTitle());
			billType.setParentId(topType.getId());
			billType.setTypeId(rentBillPlanFeeEnum.name());
			billTypeService.checkBillType(billType);
			typeMap.put(rentBillPlanFeeEnum, billType.getId());
		}
		CommonBill bill = new CommonBill();
		bill.setUrged(BooleanEnum.NO);
		if(billPlanRent.getFeeType()!=null){
			bill.setTypeId(billPlanRent.getFeeType().name());
		}
		bill.setNumber(billService.generateNumber(session));
		bill.setBillTypeId(typeMap.get(billPlanRent.getFeeType()));
		
		bill.setCustomerId(contract.getCustomerId());
		bill.setCustomerName(contract.getCustomerName());
		
		//common-customer 记录账单对应的企业
		/*Customer customer = new Customer();
		customer.setCustomerId(billPlanRent.getContract().getCustomerId());
		customer.setContractId(billPlanRent.getContractId());
		customer.setName(billPlanRent.getContract().getCustomerName());
		customer.setDepartment(DepartmentEnum.ESTATE);
		session.save(customer);
		bill.setCustomerId(customer.getId());*/
				
		bill.setDiscountRate(billPlanRent.getRealFee());
		bill.setPlanPayTime(billPlanRent.getPlanPayDate());
		switch (billPlanStatus) {
		case INCHECKED:
			bill.setInOut(BillInOutEnum.IN);
			break;
		case OUTCHECKED:
			bill.setInOut(BillInOutEnum.OUT);
			break;
		}
		bill.setTotalAmount(billPlanRent.getRealFee());
		bill.setInvoice(BooleanEnum.YES);
		bill.setInvoiceType(InvoiceTypeEnum.NOBILLING);
		bill.setPenalty(0d);
		bill.setFactPayment(billPlanRent.getRealFee());
		bill.setFeeEndTime(billPlanRent.getEndDate());
		bill.setFeeStartTime(billPlanRent.getStartDate());
		bill.setCheckoutTime(new Date());
		bill.setRoomId(billPlanRent.getRoomId());
		bill.setContractNo(contract.getCode());
		bill.setStatus(BillStatusEnum.UNPAID);
		bill.setPrice(billPlanRent.getPrice());
		bill.setAmount(billPlanRent.getAmount());
		bill.setBillPlanId(billPlanRent.getId());
		bill.setBillPlanTableName(ModulePrefixNamingStrategy.classToTableName(EstateBillPlanRent.class));
		setcreatemodify(bill);
		session.save(bill);
		billPlanRent.setModifyTime(new Date());
		billPlanRent.setModifier(EstateActivator.getSessionUser().getRealName());
		billPlanRent.setModifierId(EstateActivator.getSessionUser().getId());
		billPlanRent.setStatus(billPlanStatus);
		session.update(billPlanRent);
		return Result.failure("出账成功");
	}
	
	public void setcreatemodify(BaseEntity t){
		t.setCreateTime(new Date());
		t.setCreator(EstateActivator.getSessionUser().getRealName());
		t.setCreatorId(EstateActivator.getSessionUser().getId());
		t.setModifyTime(t.getCreateTime());
		t.setModifier(t.getCreator());
		t.setModifierId(t.getCreatorId());
		t.setEntityStatus(EntityStatus.NORMAL);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result checkout(List<EstateBillPlanRent> list) {
		Session session = null;
		Transaction tr = null;
		try {
			session = billPlanRentDao.openSession();
			tr = session.beginTransaction();
			BillType topType = billTypeService.getBillType("物业").getValue();
			Map<EstateFeeEnum,Long> typeMap = new HashMap<EstateFeeEnum,Long>();
			for (EstateFeeEnum rentBillPlanFeeEnum : EstateFeeEnum.values()) {
				BillType billType = new BillType();
				billType.setTypeName(rentBillPlanFeeEnum.getTitle());
				billType.setTypeId(rentBillPlanFeeEnum.name());
				billType.setParentId(topType.getId());
				billTypeService.checkBillType(billType);
				typeMap.put(rentBillPlanFeeEnum, billType.getId());
			}
			for (EstateBillPlanRent billPlanRent : list) {
				CommonBill bill = new CommonBill();
				bill.setUrged(BooleanEnum.NO);
				bill.setTypeId(billPlanRent.getFeeType().name());
				bill.setNumber(billService.generateNumber(session));
				bill.setInvoice(BooleanEnum.YES);
				bill.setInvoiceType(InvoiceTypeEnum.NOBILLING);
				bill.setBillTypeId(typeMap.get(billPlanRent.getFeeType()));
				bill.setCustomerId(billPlanRent.getContract().getCustomerId());
				bill.setCustomerName(billPlanRent.getContract().getCustomerName());
				//common-customer 记录账单对应的企业
				/*Customer customer = new Customer();
				customer.setCustomerId(billPlanRent.getContract().getCustomerId());
				customer.setContractId(billPlanRent.getContractId());
				customer.setName(billPlanRent.getContract().getCustomerName());
				customer.setDepartment(DepartmentEnum.ESTATE);
				session.save(customer);
				bill.setCustomerId(customer.getId());*/
				
				bill.setDiscountRate(billPlanRent.getRealFee());
				bill.setPlanPayTime(billPlanRent.getPlanPayDate());
				bill.setInOut(BillInOutEnum.IN);
				bill.setTotalAmount(billPlanRent.getRealFee());
				bill.setPenalty(0d);
				bill.setPrice(billPlanRent.getPrice());
				bill.setAmount(billPlanRent.getAmount());
				bill.setFactPayment(billPlanRent.getRealFee());
				bill.setFeeEndTime(billPlanRent.getEndDate());
				bill.setFeeStartTime(billPlanRent.getStartDate());
				bill.setCheckoutTime(new Date());
				bill.setRoomId(billPlanRent.getRoomId());
				bill.setContractNo(billPlanRent.getContract().getCode());
				bill.setStatus(BillStatusEnum.UNPAID);
				bill.setBillPlanId(billPlanRent.getId());
				bill.setBillPlanTableName(ModulePrefixNamingStrategy.classToTableName(EstateBillPlanRent.class));
				setcreatemodify(bill);
				session.save(bill);
				billPlanRent.setStatus(BillPlanStatusEnum.INCHECKED);
				session.save(billPlanRent);
			}
			tr.commit();
			return Result.success("出账成功");
		} catch (Exception e) {
			tr.rollback();
			return Result.failure("出账失败");
		} finally {
			session.close();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result batchCheckout(String ids, String[] types, boolean autoRemind) {
		Session session = null;
		Transaction tr = null;
		try {
			session = billPlanRentDao.openSession();
			tr = session.beginTransaction();
			List<EstateBillPlanRent> list = (List<EstateBillPlanRent>) session.createQuery("from EstateBillPlanRent where id in ("+ids+")").list();
			BillType topType = billTypeService.getBillType("物业").getValue();
			Map<EstateFeeEnum,Long> typeMap = new HashMap<EstateFeeEnum,Long>();
			for (EstateFeeEnum rentBillPlanFeeEnum : EstateFeeEnum.values()) {
				BillType billType = new BillType();
				billType.setTypeName(rentBillPlanFeeEnum.getTitle());
				billType.setTypeId(rentBillPlanFeeEnum.name());
				billType.setParentId(topType.getId());
				billTypeService.checkBillType(billType);
				typeMap.put(rentBillPlanFeeEnum, billType.getId());
			}
			Map<Long,StatisticDto> countMap = new HashMap<Long, StatisticDto>();
			for (int i = 0; i < list.size(); i++) {
				EstateBillPlanRent billPlanRent = list.get(i);
				if(!countMap.containsKey(billPlanRent.getContract().getCustomerId())){
					BusinessCustomer customer = billPlanRent.getContract().getCustomer();
					StatisticDto dto = new StatisticDto();
					dto.setName(customer.getName());
					dto.setdValue(billPlanRent.getRealFee());
					if(dto.getdValue()==null) dto.setdValue(0d);
					countMap.put(customer.getId(), dto);
				} else {
					StatisticDto dto = countMap.get(billPlanRent.getContract().getCustomerId());
					dto.setdValue(dto.getdValue()+billPlanRent.getRealFee());
				}
				CommonBill bill = new CommonBill();
				bill.setNumber(billService.generateNumber(session));
				bill.setInvoice(BooleanEnum.YES);
				bill.setInvoiceType(InvoiceTypeEnum.NOBILLING);
				bill.setTypeId(billPlanRent.getFeeType().name());
				bill.setBillTypeId(typeMap.get(billPlanRent.getFeeType()));
				//bill.setCustomerId(billPlanRent.getContract().getCustomerId());
				bill.setCustomerId(billPlanRent.getContract().getCustomerId());
				bill.setCustomerName(billPlanRent.getContract().getCustomerName());
				//common-customer 记录账单对应的企业
				/*Customer customer = new Customer();
				customer.setCustomerId(billPlanRent.getContract().getCustomerId());
				customer.setContractId(billPlanRent.getContractId());
				customer.setName(billPlanRent.getContract().getCustomerName());
				customer.setDepartment(DepartmentEnum.ESTATE);
				session.save(customer);
				bill.setCustomerId(customer.getId());*/
				
				bill.setDiscountRate(billPlanRent.getRealFee());
				bill.setPlanPayTime(billPlanRent.getPlanPayDate());
				bill.setCheckoutTime(new Date());
				if(types[i].equals("in")){
					bill.setInOut(BillInOutEnum.IN);
					billPlanRent.setStatus(BillPlanStatusEnum.INCHECKED);
				}else{
					bill.setInOut(BillInOutEnum.OUT);
					billPlanRent.setStatus(BillPlanStatusEnum.OUTCHECKED);
				}
				bill.setTotalAmount(billPlanRent.getRealFee());
				bill.setPenalty(0d);
				bill.setPrice(billPlanRent.getPrice());
				bill.setAmount(billPlanRent.getAmount());
				bill.setTotalAmount(billPlanRent.getPlanFee());
				bill.setFactPayment(billPlanRent.getRealFee());
				bill.setFeeEndTime(billPlanRent.getEndDate());
				bill.setFeeStartTime(billPlanRent.getStartDate());
				bill.setRoomId(billPlanRent.getRoomId());
				bill.setContractNo(billPlanRent.getContract().getCode());
				bill.setStatus(BillStatusEnum.UNPAID);
				bill.setBillPlanId(billPlanRent.getId());
				bill.setBillPlanTableName(ModulePrefixNamingStrategy.classToTableName(EstateBillPlanRent.class));
				if(autoRemind) bill.setUrged(BooleanEnum.YES);
				else bill.setUrged(BooleanEnum.NO);
				setcreatemodify(bill);
				session.save(bill);
				session.update(billPlanRent);
			}
			if(autoRemind) {
				try{
					SMSPubService smsPubService = EstateActivator.getService(SMSPubService.class);
					if(smsPubService!=null){
						for (Entry<Long, StatisticDto> entry : countMap.entrySet()) {
							Contect contect = (Contect) session.createQuery("from Contect where customerId = "+entry.getKey()+" and main = '"+BooleanEnum.YES+"'").uniqueResult();
							if(contect==null) continue;
							SMSDto sms = new SMSDto();
							String mobile = contect.getMobile();
							String content = EstateActivator.getAppConfig().getConfig("billCheckoutSms").getParameter("content");
							content = content.replace("${customerName}", contect.getCustomer().getName());
							content = content.replace("${total}", new DecimalFormat("#0.00").format(entry.getValue().getdValue())+"");
							sms.setContent(content);
							List<SMSReceiverDto> receiverList = new ArrayList<SMSReceiverDto>();
							SMSReceiverDto receiver = new SMSReceiverDto();
							receiver.setPhone(mobile);
							receiver.setReceiverName(contect.getCustomer().getName());
							receiverList.add(receiver);
							sms.setReceiverList(receiverList);
							sms.setCreator(EstateActivator.getSessionUser().getRealName());
							sms.setCreatorId(EstateActivator.getSessionUser().getId());
							smsPubService.send(sms, session);
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			tr.commit();
			return Result.success("出账成功");
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure("出账失败");
		} finally {
			session.close();
		}
	}

	@Override
	public Integer getRowCount(Filter filter) {
		return billPlanRentDao.getRowCount(filter);
	}
	
	@Override
	public Result<List<EstateBillPlanRent>> getListHql(String hql) {
		try {
			return Result.value(billPlanRentDao.getListByHql(hql));
		} catch (Exception e) {
			return Result.failure(R.EstateBillPlanRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result getRowCountByFeeType() {
		try{
			Map<String,Integer> map = new HashMap<String, Integer>();
			List<Object> objects = (List<Object>)billPlanRentDao.getObjectListBySql("select fee_type,count(*) from estate_estate_bill_plan_rent where status = 'UNCHECK' group by fee_type");
			if(objects!=null && objects.size()>0){
				for (Object object : objects) {
					Object[] obj = (Object[])object;
					if(obj[0]!=null){
						map.put(obj[0].toString(), Integer.valueOf(obj[1].toString()));
					}
				}
			}
			return Result.value(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}
}
