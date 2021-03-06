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
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dao.DepositDao;
import com.wiiy.estate.dto.StatisticDto;
import com.wiiy.estate.entity.EstateContract;
import com.wiiy.estate.entity.EstateDeposit;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.preferences.enums.DepositTypeEnum;
import com.wiiy.estate.service.DepositService;
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
public class DepositServiceImpl implements DepositService{
	
	private DepositDao depositDao;
	private BillTypeService billTypeService;
	private BillService billService;
	
	public void setDepositDao(DepositDao depositDao) {
		this.depositDao = depositDao;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public void setBillTypeService(BillTypeService billTypeService) {
		this.billTypeService = billTypeService;
	}

	@Override
	public Result<EstateDeposit> save(EstateDeposit t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			depositDao.save(t);
			return Result.success(R.Deposit.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateDeposit.class)));
		} catch (Exception e) {
			return Result.failure(R.Deposit.SAVE_FAILURE);
		}
	}

	@Override
	public Result<EstateDeposit> delete(EstateDeposit t) {
		try {
			depositDao.delete(t);
			return Result.success(R.Deposit.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.Deposit.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateDeposit> deleteById(Serializable id) {
		try {
			depositDao.deleteById(id);
			return Result.success(R.Deposit.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.Deposit.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateDeposit> deleteByIds(String ids) {
		try {
			depositDao.deleteByIds(ids);
			return Result.success(R.Deposit.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.Deposit.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateDeposit> update(EstateDeposit t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			depositDao.update(t);
			return Result.success(R.Deposit.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateDeposit.class)));
		} catch (Exception e) {
			return Result.failure(R.Deposit.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<EstateDeposit> getBeanById(Serializable id) {
		try {
			return Result.value(depositDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.Deposit.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EstateDeposit> getBeanByFilter(Filter filter) {
		try {
			return Result.value(depositDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Deposit.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateDeposit>> getList() {
		try {
			return Result.value(depositDao.getList());
		} catch (Exception e) {
			return Result.failure(R.Deposit.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateDeposit>> getListByFilter(Filter filter) {
		try {
			return Result.value(depositDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Deposit.LOAD_FAILURE);
		}
	}

	@Override
	public Result batchCheckout(String ids, String[] types, boolean autoRemind) {
		Session session = null;
		Transaction tr = null;
		try {
			session = depositDao.openSession();
			tr = session.beginTransaction();
			List<EstateDeposit> list = (List<EstateDeposit>) session.createQuery("from EstateDeposit where id in ("+ids+")").list();
			Map<DepositTypeEnum,Long> typeMap = new HashMap<DepositTypeEnum,Long>();
			for (DepositTypeEnum depositTypeEnum : DepositTypeEnum.values()) {
				BillType billType = new BillType();
				billType.setTypeName(depositTypeEnum.getTitle());
				billTypeService.checkBillType(billType);
				typeMap.put(depositTypeEnum, billType.getId());
			}
			Map<Long,StatisticDto> countMap = new HashMap<Long, StatisticDto>();
			for (int i = 0; i < list.size(); i++) {
				EstateDeposit deposit = list.get(i);
				if(!countMap.containsKey(deposit.getCustomerId())){
					BusinessCustomer customer = deposit.getCustomer();
					StatisticDto dto = new StatisticDto();
					dto.setName(customer.getName());
					dto.setdValue(deposit.getAmount());
					countMap.put(customer.getId(), dto);
				} else {
					StatisticDto dto = countMap.get(deposit.getCustomerId());
					dto.setdValue(dto.getdValue()+deposit.getAmount());
				}
				BusinessCustomer customer = deposit.getCustomer();
				EstateContract contract = deposit.getContract();
				CommonBill bill = new CommonBill();
				bill.setNumber(billService.generateNumber(session));
				bill.setInvoice(BooleanEnum.NO);
				bill.setBillTypeId(typeMap.get(deposit.getType()));
				bill.setDiscountRate(deposit.getAmount());
				if(deposit.getPlanPayDate()!=null){
					bill.setPlanPayTime(deposit.getPlanPayDate());
				}else{
					bill.setPlanPayTime(new Date());
				}
				if(contract!=null){
					bill.setContractNo(contract.getCode());
				}
				if(customer!=null){
					bill.setCustomerId(customer.getId());
				}
				if(types[i].equals("in")){
					bill.setInOut(BillInOutEnum.IN);
					deposit.setStatus(BillPlanStatusEnum.INCHECKED);
				} else {
					bill.setInOut(BillInOutEnum.OUT);
					deposit.setStatus(BillPlanStatusEnum.OUTCHECKED);
				}
				bill.setTotalAmount(deposit.getAmount());
				bill.setPrice("元");
				bill.setAmount(null);
				bill.setPenalty(0d);
				bill.setFactPayment(deposit.getAmount());
				bill.setFeeEndTime(null);
				bill.setFeeStartTime(null);
				bill.setCheckoutTime(new Date());
				bill.setStatus(BillStatusEnum.UNPAID);
				bill.setBillPlanId(deposit.getId());
				bill.setBillPlanTableName(ModulePrefixNamingStrategy.classToTableName(EstateDeposit.class));
				if(autoRemind) bill.setUrged(BooleanEnum.YES);
				else bill.setUrged(BooleanEnum.NO);
				setcreatemodify(bill);
				session.save(bill);
				session.update(deposit);
			}
			if(autoRemind){
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
	public void setcreatemodify(BaseEntity t){
		t.setCreateTime(new Date());
		t.setCreator(EstateActivator.getSessionUser().getRealName());
		t.setCreatorId(EstateActivator.getSessionUser().getId());
		t.setModifyTime(t.getCreateTime());
		t.setModifier(t.getCreator());
		t.setModifierId(t.getCreatorId());
		t.setEntityStatus(EntityStatus.NORMAL);
	}

	
	
	@Override
	public Result checkoutById(Long id, BillPlanStatusEnum billPlanStatus) {
		Session session = null;
		Transaction tr = null;
		try {
			session = depositDao.openSession();
			tr = session.beginTransaction();
			EstateDeposit deposit = (EstateDeposit) session.get(EstateDeposit.class,id);
			BillType topType = billTypeService.getBillType("招商").getValue();
			Map<DepositTypeEnum,Long> typeMap = new HashMap<DepositTypeEnum,Long>();
			for (DepositTypeEnum depositTypeEnum : DepositTypeEnum.values()) {
				BillType billType = new BillType();
				billType.setTypeName(depositTypeEnum.getTitle());
				billType.setParentId(topType.getId());
				billTypeService.checkBillType(billType);
				typeMap.put(depositTypeEnum, billType.getId());
			}
			CommonBill bill = new CommonBill();
			bill.setNumber(billService.generateNumber(session));
			bill.setBillTypeId(typeMap.get(deposit.getType()));
			bill.setCustomerId(deposit.getContract().getCustomerId());
			bill.setDiscountRate(deposit.getAmount());
			switch (billPlanStatus) {
			case INCHECKED:
				bill.setInOut(BillInOutEnum.IN);
				break;
			case OUTCHECKED:
				bill.setInOut(BillInOutEnum.OUT);
				break;
			}
			bill.setPlanPayTime(new Date());
			bill.setTotalAmount(deposit.getAmount());
			bill.setPenalty(0d);
			bill.setFactPayment(deposit.getAmount());
			bill.setFeeEndTime(deposit.getContract().getEndDate());
			bill.setFeeStartTime(deposit.getContract().getStartDate());
			bill.setCheckoutTime(new Date());
			bill.setStatus(BillStatusEnum.UNPAID);
			bill.setBillPlanId(deposit.getId());
			bill.setBillPlanTableName(ModulePrefixNamingStrategy.classToTableName(EstateDeposit.class));
			setcreatemodify(bill);
			session.save(bill);
			deposit.setStatus(billPlanStatus);
			session.save(deposit);
			tr.commit();
			return Result.failure("出账成功");
		} catch (Exception e) {
			tr.rollback();
			return Result.failure("出账失败");
		} finally {
			session.close();
		}
	}

	@Override
	public Integer getRowCount(Filter filter) {
		return depositDao.getRowCount(filter);
	}

}
