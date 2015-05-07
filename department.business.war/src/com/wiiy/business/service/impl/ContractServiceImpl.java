package com.wiiy.business.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.exception.ConstraintViolationException;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.BusinessContractDao;
import com.wiiy.business.dao.ParkLogDao;
import com.wiiy.business.dto.ContractRentPrintDto;
import com.wiiy.business.entity.BusinessBillPlanRent;
import com.wiiy.business.entity.BusinessContract;
import com.wiiy.business.entity.BusinessContractAtt;
import com.wiiy.business.entity.BusinessCustomer;
import com.wiiy.business.entity.BusinessSubjectRent;
import com.wiiy.business.entity.ContractMemo;
import com.wiiy.business.entity.ContractModifyLog;
import com.wiiy.business.entity.CustomerInfo;
import com.wiiy.business.entity.ParkLog;
import com.wiiy.business.preferences.R;
import com.wiiy.business.preferences.enums.ContractRentStatusEnum;
import com.wiiy.business.preferences.enums.ContractStatusEnum;
import com.wiiy.business.preferences.enums.ParkLogTypeEnums;
import com.wiiy.business.service.BillPlanRentService;
import com.wiiy.business.service.ContractService;
import com.wiiy.business.service.CustomerService;
import com.wiiy.business.service.SubjectRentService;
import com.wiiy.common.preferences.enums.BillPlanStatusEnum;
import com.wiiy.common.preferences.enums.BusinessFeeEnum;
import com.wiiy.common.preferences.enums.PriceUnitEnum;
import com.wiiy.common.preferences.enums.RoomStatusEnum;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.commons.util.DateUtil;
import com.wiiy.commons.util.IOUtil;
import com.wiiy.commons.util.MicrosoftWordUtil;
import com.wiiy.commons.util.StringUtil;
import com.wiiy.core.entity.Approval;
import com.wiiy.core.entity.User;
import com.wiiy.core.preferences.enums.ApprovalStatusEnum;
import com.wiiy.core.preferences.enums.ApprovalTypeEnum;
import com.wiiy.core.service.export.OsgiUserService;
import com.wiiy.external.service.SMSPubService;
import com.wiiy.external.service.SysEmailSenderPubService;
import com.wiiy.external.service.dto.SMSDto;
import com.wiiy.external.service.dto.SMSReceiverDto;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.park.entity.Room;
import com.wiiy.park.entity.RoomHistory;
import com.wiiy.park.service.RoomService;

/**
 * @author my
 */
public class ContractServiceImpl implements ContractService{
	
	private BusinessContractDao contractDao;
	private RoomService roomService;
	private BillPlanRentService billPlanRentService;
	private ParkLogDao parkLogDao ;
	private CustomerService customerService;
	private SubjectRentService subjectRentService;

	public void setSubjectRentService(SubjectRentService subjectRentService) {
		this.subjectRentService = subjectRentService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public void setParkLogDao(ParkLogDao parkLogDao) {
		this.parkLogDao = parkLogDao;
	}
	
	public void setContractDao(BusinessContractDao contractDao) {
		this.contractDao = contractDao;
	}

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	public void setBillPlanRentService(BillPlanRentService billPlanRentService) {
		this.billPlanRentService = billPlanRentService;
	}

	@Override
	public Result<BusinessContract> save(BusinessContract t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			contractDao.save(t);
			return Result.success(R.BusinessContract.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),BusinessContract.class)));
		} catch (Exception e) {
			return Result.failure(R.BusinessContract.SAVE_FAILURE);
		}
	}

	@Override
	public Result<BusinessContract> delete(BusinessContract t) {
		try {
			contractDao.delete(t);
			return Result.success(R.BusinessContract.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.BusinessContract.DELETE_FAILURE);
		}
	}

	@Override
	public Result<BusinessContract> deleteById(Serializable id) {
		Session session=null;
		Transaction tr=null;
		try {
			session=contractDao.openSession();
			tr=session.beginTransaction();
			try {
				session.createSQLQuery("delete from business_business_bill_plan_rent where contract_id = "+id).executeUpdate();
				session.createSQLQuery("delete from business_business_deposit where contract_id = "+id).executeUpdate();
				session.createSQLQuery("delete from business_business_contract where id = "+id).executeUpdate();
			}  catch (ConstraintViolationException e) {
				throw new FKConstraintException(e);
			}
			tr.commit();
			return Result.success(R.BusinessContract.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.BusinessContract.DELETE_FAILURE);
		}
	}

	@Override
	public Result<BusinessContract> deleteByIds(String ids) {
		System.out.println(ids);
		Session session=null;
		Transaction tr=null;
		try {
			session=contractDao.openSession();
			tr=session.beginTransaction();
			
			try {
				session.createSQLQuery("delete from business_business_bill_plan_rent where contract_id in ("+ids+")").executeUpdate();
				session.createSQLQuery("delete from business_business_deposit where contract_id in ("+ids+")").executeUpdate();
				session.createSQLQuery("delete from business_business_contract where id in ("+ids+")").executeUpdate();
			}  catch (ConstraintViolationException e) {
				throw new FKConstraintException(e);
			}
			tr.commit();
			return Result.success(R.BusinessContract.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.BusinessContract.DELETE_FAILURE);
		}
	}

	@Override
	public Result<BusinessContract> update(BusinessContract t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			contractDao.update(t);
			return Result.success(R.BusinessContract.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),BusinessContract.class)));
		} catch (Exception e) {
			return Result.failure(R.BusinessContract.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<BusinessContract> getBeanById(Serializable id) {
		try {
			return Result.value(contractDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.BusinessContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<BusinessContract> getBeanByFilter(Filter filter) {
		try {
			return Result.value(contractDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.BusinessContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<BusinessContract>> getList() {
		try {
			return Result.value(contractDao.getList());
		} catch (Exception e) {
			return Result.failure(R.BusinessContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<BusinessContract>> getListByFilter(Filter filter) {
		try {
			return Result.value(contractDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.BusinessContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<String> generateCode(String contractModel) {
		String mouth =String.valueOf(CalendarUtil.now().get(Calendar.MONTH)+1);
		if(CalendarUtil.now().get(Calendar.MONTH)<10){
			mouth = "0"+mouth;
		}
		String code =CalendarUtil.now().get(Calendar.YEAR)+mouth;
		
		BusinessContract contract = contractDao.getBeanByFilter(new Filter(BusinessContract.class).like("code", code, MatchMode.START).orderBy("code", Filter.DESC).maxResults(1));
		int count = contractDao.getRowCount(new Filter(BusinessContract.class).ge("signDate", CalendarUtil.getEarliest(Calendar.MONTH)));
		if (contract != null) {
			try {
				String oldCode = contract.getCode();
				count = Integer.parseInt(oldCode.replace(code, ""));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		nf.setMinimumIntegerDigits(2);
		code = code + nf.format(count+1);
		return Result.value(code);
	}

	@Override
	public Result save(BusinessContract t, List<BusinessSubjectRent> sessionSubjectRentList) {
		Session session = null;
		Transaction tr = null;
		try {
			session = contractDao.openSession();
			tr = session.beginTransaction();
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			session.save(t);
			if(sessionSubjectRentList!=null)
			for (BusinessSubjectRent subjectRent : sessionSubjectRentList) {
				subjectRent.setContractId(t.getId());
				subjectRent.setCreateTime(new Date());
				subjectRent.setCreator(BusinessActivator.getSessionUser().getRealName());
				subjectRent.setCreatorId(BusinessActivator.getSessionUser().getId());
				subjectRent.setModifyTime(t.getCreateTime());
				subjectRent.setModifier(t.getCreator());
				subjectRent.setModifierId(t.getCreatorId());
				subjectRent.setEntityStatus(EntityStatus.NORMAL);
				session.save(subjectRent);
			}
			tr.commit();
			return Result.success(R.BusinessContract.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),BusinessContract.class)));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.BusinessContract.SAVE_FAILURE);
		} finally {
			session.close();
		}
	}

	public void setcreatemodify(BaseEntity t){
		t.setCreateTime(new Date());
		t.setCreator(BusinessActivator.getSessionUser().getRealName());
		t.setCreatorId(BusinessActivator.getSessionUser().getId());
		t.setModifyTime(t.getCreateTime());
		t.setModifier(t.getCreator());
		t.setModifierId(t.getCreatorId());
		t.setEntityStatus(EntityStatus.NORMAL);
	}

	@Override
	public Result submitApproval(Long id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = contractDao.openSession();
			tr = session.beginTransaction();
			BusinessContract contract = (BusinessContract) session.get(BusinessContract.class, id);
//			contract.setState(ContractStatusEnum.COMFIRM);
			SMSPubService smsPubService = BusinessActivator.getService(SMSPubService.class);
			if(smsPubService!=null){
				SMSDto dto = new SMSDto();
				Long userId = Long.parseLong(BusinessActivator.getAppConfig().getConfig("rentContractAuthor").getParameter("author"));
				User user = BusinessActivator.getService(OsgiUserService.class).getById(userId);
				String mobile = user.getMobile();
				dto.setContent("您好，有一份编号为"+contract.getCode()+"的合同需要您审批");
				List<SMSReceiverDto> receiverList = new ArrayList<SMSReceiverDto>();
				SMSReceiverDto receiver = new SMSReceiverDto();
				receiver.setPhone(mobile);
				receiver.setReceiverName(user.getRealName());
				receiverList.add(receiver);
				dto.setReceiverList(receiverList);
				dto.setCreator(BusinessActivator.getSessionUser().getRealName());
				dto.setCreatorId(BusinessActivator.getSessionUser().getId());
				smsPubService.send(dto, session);
			}
			session.update(contract);
			tr.commit();
			return Result.success(R.BusinessContract.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),BusinessContract.class)));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.BusinessContract.SAVE_FAILURE);
		} finally {
			session.close();
		}
	}
	

	/**
	 * 合同打印
	 */
	@Override
	public Result print(Long id) {
		try {
			Session session = contractDao.openSession();
			Object dto = generatePrintDto(session,id);
			return Result.value(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Investment.LOAD_FAILURE);
		}
	}
	
	public Object generatePrintDto(Session session, Long id){
		BusinessContract contract = contractDao.getBeanById(id);
		
		List<BusinessSubjectRent> list = session.createQuery("from BusinessSubjectRent where contractId = "+id).list();
		List<BusinessBillPlanRent> billPlanList = session.createQuery("from BusinessBillPlanRent where contractId = "+id).list();
		ContractRentPrintDto dto = new ContractRentPrintDto();
		if(contract.getContractParty()!=null){
			dto.setContractParty(contract.getContractParty().getDataValue());
		}
		dto.setCustomerName(contract.getCustomerName());
		dto.setStartDate(contract.getStartDate());
		dto.setEndDate(contract.getEndDate());
		
		if(null!=contract.getPropertyName()){dto.setPropertyName(contract.getPropertyName());}
		if(null!=contract.getPropertyNo()){dto.setPropertyNo(contract.getPropertyNo().getDataValue());}
		if(null!=contract.getRoomType()){dto.setRoomType(contract.getRoomType().getDataValue());}
		if(null!=contract.getPropertyUnit()){dto.setPropertyUnit(contract.getPropertyUnit().toString());}
		if(0!=list.size()){
			dto.setAreaTotal(0d);
			String roomName = "";
			for (BusinessSubjectRent subjectRent : list) {
				dto.setAreaTotal(dto.getAreaTotal()+subjectRent.getRoomArea());
				if(null!=subjectRent.getRoomName()){
					roomName+=subjectRent.getRoom().getName()+",";
				}
			}
			//租金（每日每平方米）
			dto.setRentPrice(list.get(0).getRentPrice());
			dto.setRentPriceUnit(list.get(0).getRentPriceUnit().getTitle());
			
			dto.setContractItemList(roomName.substring(0, roomName.length()-1));
		}
		if (0!=billPlanList.size()) {
			double planFee = 0;
			/*for (BusinessBillPlanRent billPlanRent : billPlanList) {
				if ((BusinessFeeEnum.MANAGE).equals(billPlanRent
						.getFeeType())) {
					planFee += billPlanRent.getPlanFee();
				}
			}
*/			
			dto.setAnnuity(planFee);
		}
		if(null!=contract.getOverallFloorage()){dto.setOverallFloorage((contract.getOverallFloorage().toString()));}
		if(null!=contract.getPurpose()){dto.setPurpose(contract.getPurpose());}
		if(null!=contract.getPayType()){dto.setPayType(contract.getPayType().getTitle());}
		if(null!=contract.getRentAmount()){
			System.out.println(contract.getRentAmount());
			dto.setRentAmount(contract.getRentAmount());
		}
		if(null!=contract.getAddress()){dto.setAddress(contract.getAddress());}
		
		/**
		 * 招商合同打印内容
		 */
		double day = (contract.getEndDate().getTime()-contract.getStartDate().getTime())/(24*60*60*1000);
		double year = day%365>5?(day/365+1):day/365;
		
		dto.setContractDate(String.valueOf(year));
		
		dto.setCode(contract.getCode());//编号
		dto.setDecorateStartDate(contract.getDecorateStartDate());//装修期
		dto.setDecorateEndDate(contract.getDecorateEndDate());
		//第一期租金
		dto.setRealFeeZJ(contract.getRealFeeZJ());
		if(contract.getRealFeeZJ()!=null){
			dto.setRealFeeZJ2(StringUtil.digitUppercase(contract.getRealFeeZJ()));
		}
		//定金
		dto.setRealFeeDJ(contract.getRealFeeDJ());
		if(contract.getRealFeeDJ()!=null){
			dto.setRealFeeDJ2(StringUtil.digitUppercase(contract.getRealFeeDJ()));
		}
		//第一年每日每平方米租金
		dto.setRentPrice(contract.getRentPrice());
		
		//乙方信息
		CustomerInfo customerInfo = (CustomerInfo)session.get(CustomerInfo.class, contract.getCustomerId());
		if(customerInfo!=null){
			dto.setCustomerAddress(customerInfo.getAddress());
			dto.setCustomerContact(customerInfo.getContact());
			dto.setPhone(customerInfo.getOfficePhone());
		}
		
		return dto;
	}
	
	@Override
	public Result print(Long id, File template,OutputStream out) {
		try {
			Session session = contractDao.openSession();
			Object dto = generatePrintDto(session, id);
			generateWord(dto, template, out);
			return Result.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Investment.LOAD_FAILURE);
		}
	}
	
	private void generateWord(Object contract,File template,OutputStream out) {
		MicrosoftWordUtil mwu = new MicrosoftWordUtil();
		try {
			String printDoc = "d:\\printDoc";
			File f = new File(printDoc);
			f.mkdir();
			File temp = new File("d:\\printDoc\\temp.doc");
			IOUtil.copyInputStreamToFile(new FileInputStream(template), temp);
			mwu.openDocument(temp.getAbsolutePath());
			if(contract instanceof ContractRentPrintDto) {
				Field[] fields = ContractRentPrintDto.class.getDeclaredFields();
				for (Field field : fields) {
					if(!Collection.class.isAssignableFrom(field.getType())){
						String fieldName = field.getName();
						try {
							Object value = ContractRentPrintDto.class.getMethod("get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1)).invoke(contract);
							String replaceText = "";
							if(value instanceof Number){
								replaceText = new DecimalFormat("#.##").format(value);
							} else if(value instanceof Date){
								replaceText = DateUtil.format((Date)value,"yyyy年MM月dd日");
							} else if(value!=null){
								replaceText = value.toString();
							}
							String toFindText = "#"+fieldName;
							mwu.moveStart();
							mwu.replaceAllText(toFindText, replaceText);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			mwu.closeDocument();
			IOUtil.copyFileToOutputStream(temp, out);
			temp.deleteOnExit();
		} catch (Exception e) {
			e.printStackTrace();
			mwu.close();
		}
	}

	/**
	 * 减租
	 */
	@Override
	public Result submitSubtract(Long contractId, Long subjectRentId, Date executeTime,Double checkoutMoney,Boolean checkoutNow, String memo) {
		Session session = null;
		Transaction tr = null;
		try {
			session = contractDao.openSession();
			tr = session.beginTransaction();
			BusinessContract contract = (BusinessContract) session.get(BusinessContract.class, contractId);
			BusinessSubjectRent subjectRent =  (BusinessSubjectRent) session.get(BusinessSubjectRent.class, subjectRentId);
			BusinessBillPlanRent billPlanRent = new BusinessBillPlanRent();
			billPlanRent.setAutoCheck(BooleanEnum.NO);
			billPlanRent.setContractId(contractId);
			billPlanRent.setEndDate(executeTime);
			billPlanRent.setFeeType(BusinessFeeEnum.BUSINESS_ZJ);
			billPlanRent.setMemo(memo);
			billPlanRent.setRoomId(subjectRent.getRoomId());
			billPlanRent.setRoomName(subjectRent.getRoomName());
			billPlanRent.setPlanFee(checkoutMoney);
			billPlanRent.setPlanPayDate(executeTime);
			billPlanRent.setRealFee(checkoutMoney);
			billPlanRent.setStartDate(contract.getStartDate());
			billPlanRent.setSubjectId(subjectRentId);
			setcreatemodify(billPlanRent);
			if(checkoutNow) billPlanRent.setStatus(BillPlanStatusEnum.OUTCHECKED);
			else billPlanRent.setStatus(BillPlanStatusEnum.UNCHECK);
			session.save(billPlanRent);
			if(checkoutNow) billPlanRentService.checkoutById(billPlanRent.getId(), BillPlanStatusEnum.OUTCHECKED, session);
			surrender(subjectRent,executeTime,session,billPlanRent.getId());
			ContractMemo contractMemo = new ContractMemo();
			contractMemo.setMemo(memo);
			contractMemo.setContractId(contractId);
			setcreatemodify(contractMemo);
			session.save(contractMemo);
			ContractModifyLog log = new ContractModifyLog();
			setcreatemodify(log);
			log.setOperation("合同减租");
			log.setMemo("合同标的"+subjectRent.getRoomName()+"减租 执行时间为"+DateUtil.format(executeTime));
			log.setContractId(contractId);
			setcreatemodify(log);
			session.save(log);
			ParkLog parkLog = new ParkLog() ;
			parkLog.setCreator("合同减租");
			parkLog.setContent("合同标的"+subjectRent.getRoomName()+"减租 执行时间为"+DateUtil.format(executeTime));
			parkLog.setParkLogType(ParkLogTypeEnums.CONTRACT);
			parkLog.setCreateTime(new Date());
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
			session.save(parkLog);
			tr.commit();
			return Result.success("减租成功");
		} catch (Exception e) {
			tr.rollback();
			return Result.failure("减租失败");
		} finally {
			session.close();
		}
	}
	
	private void surrender(BusinessSubjectRent subjectRent,Date executeTime,Session session){
		subjectRent.setEndDate(executeTime);
		session.update(subjectRent);
		session.createQuery("delete from BillPlanRent where subjectId = "+subjectRent.getId()+" and status = '"+BillPlanStatusEnum.UNCHECK+"'").executeUpdate();
	}
	
	private void surrender(BusinessSubjectRent subjectRent,Date executeTime,Session session,Long except){
		subjectRent.setEndDate(executeTime);
		session.update(subjectRent);
		session.createQuery("delete from BillPlanRent where subjectId = "+subjectRent.getId()+" and status = '"+BillPlanStatusEnum.UNCHECK+"' and id != "+except).executeUpdate();
	}
	
	
	/**
	 * 退租
	 */
	@Override
	public Result submitSurrender(Long contractId, Date executeTime,List<Double> checkoutMoneys,Boolean checkoutNow, String memo) {
		Session session = null;
		Transaction tr = null;
		try {
			session = contractDao.openSession();
			tr = session.beginTransaction();
			BusinessContract contract = (BusinessContract) session.get(BusinessContract.class, contractId);
			contract.setEndDate(executeTime);
//			contract.setState(ContractStatusEnum.CLOSED);
			contract.setRentState(ContractRentStatusEnum.RENTOFF);
			List<BusinessSubjectRent> subjectRentList = session.createQuery("from BusinessSubjectRent where contractId = "+contractId).list();
			for (int i = 0; i < subjectRentList.size(); i++) {
				BusinessSubjectRent subjectRent = subjectRentList.get(i);
				surrender(subjectRent,executeTime,session);
				BusinessBillPlanRent billPlanRent = new BusinessBillPlanRent();
				billPlanRent.setAutoCheck(BooleanEnum.NO);
				billPlanRent.setContractId(contractId);
				billPlanRent.setEndDate(executeTime);
				billPlanRent.setFeeType(BusinessFeeEnum.BUSINESS_ZJ);
				billPlanRent.setRoomId(subjectRent.getRoomId());
				billPlanRent.setRoomName(subjectRent.getRoomName());
				billPlanRent.setMemo(memo);
				billPlanRent.setPlanFee(checkoutMoneys.get(i));
				billPlanRent.setPlanPayDate(executeTime);
				billPlanRent.setRealFee(checkoutMoneys.get(i));
				billPlanRent.setStartDate(contract.getStartDate());
				billPlanRent.setSubjectId(subjectRent.getId());
				setcreatemodify(billPlanRent);
				billPlanRent.setStatus(BillPlanStatusEnum.OUTCHECKED);
				//else billPlanRent.setStatus(BillPlanStatusEnum.UNCHECK);
				session.save(billPlanRent);
				if(checkoutNow) billPlanRentService.checkoutById(billPlanRent.getId(), BillPlanStatusEnum.OUTCHECKED, session);
			}
			ContractMemo contractMemo = new ContractMemo();
			contractMemo.setMemo(memo);
			contractMemo.setContractId(contractId);
			setcreatemodify(contractMemo);
			session.save(contractMemo);
			ContractModifyLog log = new ContractModifyLog();
			setcreatemodify(log);
			log.setOperation("合同退租");
			log.setMemo("退租 执行时间为"+DateUtil.format(executeTime));
			log.setContractId(contractId);
			setcreatemodify(log);
			session.save(log);
			ParkLog parkLog = new ParkLog() ;
			parkLog.setCreator("合同退租");
			parkLog.setContent("退租 执行时间为"+DateUtil.format(executeTime));
			parkLog.setParkLogType(ParkLogTypeEnums.CONTRACT);
			parkLog.setCreateTime(new Date());
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
			session.save(parkLog);
			tr.commit();
			return Result.success("退租成功");
		} catch (Exception e) {
			tr.rollback();
			return Result.failure("退租失败");
		} finally {
			session.close();
		}
	}

	@Override
	public Result approval(Long contractId,Long approvalUserId) {
		Session session = null;
		Transaction tr = null;
		try {
			session = contractDao.openSession();
			if(session.createQuery("from Approval where type = '"+ApprovalTypeEnum.CONTRACT+"' and groupId = "+contractId+" and userId = "+approvalUserId).list().size()>0){
				return Result.failure("审批人已存在");
			} else {
				tr = session.beginTransaction();
				BusinessContract contract = (BusinessContract) session.get(BusinessContract.class, contractId);
				Approval approval = new Approval();
				approval.setUserId(approvalUserId);
				approval.setUserName(BusinessActivator.getService(OsgiUserService.class).getById(approvalUserId).getRealName());
				approval.setGroupId(contractId);
				approval.setStatus(ApprovalStatusEnum.UNDETERMINED);
				approval.setType(ApprovalTypeEnum.CONTRACT);
				approval.setTitle(contract.getName());
				approval.setWidth(765);
				approval.setHeight(467);
				approval.setUrl("parkmanager.pb/contract!view.action?id="+contractId+"&type=approval");
				setcreatemodify(approval);
				session.save(approval);
				sendMsg(session,contractId);
				tr.commit();
				return Result.success("送审成功");
			}
		} catch (Exception e) {
			tr.rollback();
			return Result.failure("送审失败");
		} finally {
			session.close();
		}
	}
	
	private void sendMsg(Session session, Long contractId){
		BusinessContract contract = (BusinessContract) session.get(BusinessContract.class, contractId);
		String author = BusinessActivator.getAppConfig().getConfig("rentContractAuthor").getParameter("author");
		OsgiUserService userService = BusinessActivator.getService(OsgiUserService.class);
		User user = userService.getById(Long.valueOf(author));
		SMSPubService smsPubService = BusinessActivator.getService(SMSPubService.class);
		if(smsPubService!=null && smsActive()){
			String receiverMobile = user.getMobile();
			String receiverName = user.getRealName();
			String content = BusinessActivator.getAppConfig().getConfig("contractRemind").getParameter("smsModule");
			content = content.replace("${companyName}", contract.getCustomerName());
			smsPubService.send(receiverMobile, content, receiverName);
		}
		
		SysEmailSenderPubService sysEmailSenderPubService = BusinessActivator.getService(SysEmailSenderPubService.class);
		if(sysEmailSenderPubService!=null && emailActive()){
			String content = "";
			StringBuffer data = parseHTML("web/msgRemindModule/msgRemindModule.html");
			content = data.toString();
			String subject = "合同审批提醒";
			content = content.replace("${subject}", contract.getCustomerName());
			content = content.replace("${msgType}", "合同审批提醒");
			content = content.replace("${url}", basePath()+"parkmanager.pb/contract!view.action?id="+contract.getId());
			content = content.replace("${receiver}", user.getRealName());
			content = content.replace("${customerName}", user.getRealName());
			content = content.replace("${sender}", BusinessActivator.getSessionUser().getRealName());
			content = content.replace("${content}", "");
			content = content.replace("${msgLink}",BusinessActivator.getRemindEmailService().getRemindEmailLink());
			sysEmailSenderPubService.send(user.getEmail(),content,subject);
		}
	}
	
	private boolean emailActive(){
		String msgSet =  BusinessActivator.getAppConfig().getConfig("contractRemind").getParameter("email");
		if(msgSet.equals("active")){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean smsActive(){
		String msgSet =  BusinessActivator.getAppConfig().getConfig("contractRemind").getParameter("sms");
		if(msgSet.equals("active")){
			return true;
		}else{
			return false;
		}
	}
	
	private StringBuffer parseHTML(String str){
		URL url = BusinessActivator.getURL(str);
		InputStreamReader Inputreader;
		StringBuffer data = new StringBuffer();
		try {
			Inputreader = new InputStreamReader(url.openStream(),"utf-8");
			BufferedReader br = new BufferedReader(Inputreader);
			String temp=br.readLine();
			while( temp!=null){
				data.append(temp+"\n");
				temp=br.readLine(); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	private String basePath(){
		return ServletActionContext.getRequest().getScheme()+"://"+ServletActionContext.getRequest().getServerName()+":"+ServletActionContext.getRequest().getServerPort()+"/";
	}
	
	@Override
	public Result closeContract(Long contractId) {
		Session session = null;
		Transaction tr = null;
		try {
			session = contractDao.openSession();
			tr = session.beginTransaction();
			BusinessContract contract = (BusinessContract) session.get(BusinessContract.class, contractId);
			contract.setState(ContractStatusEnum.CLOSED);
			session.update(contract);
			List<BusinessSubjectRent> subjectRentList = session.createQuery("from BusinessSubjectRent where contractId="+contractId).list();
			/*for (BusinessSubjectRent subjectRent : subjectRentList) {
				roomService.updateRoomCustomer(session, subjectRent.getRoomId(), contract,subjectRent);
			}*/
			ContractModifyLog log = new ContractModifyLog();
			setcreatemodify(log);
			log.setOperation("合同关闭");
			log.setMemo("合同关闭");
			log.setContractId(contractId);
			session.save(log);
			ParkLog parkLog = new ParkLog() ;
			parkLog.setCreator("合同关闭");
			parkLog.setContent("合同关闭:"+contract.getName()+"【"+contract.getCustomerName()+"】");
			parkLog.setParkLogType(ParkLogTypeEnums.CONTRACT);
			parkLog.setCreateTime(new Date());
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
			session.save(parkLog);
			tr.commit();
			return Result.success("合同关闭成功");
		} catch (Exception e) {
			tr.rollback();
			return Result.failure("合同关闭失败");
		} finally {
			session.close();
		}
	}
	@Override
	public Result executeContract(Long contractId) {
		Session session = null;
		Transaction tr = null;
		try {
			session = contractDao.openSession();
			tr = session.beginTransaction();
			BusinessContract contract = (BusinessContract) session.get(BusinessContract.class, contractId);
			contract.setState(ContractStatusEnum.EXECUTE);
			session.update(contract);
			List<BusinessSubjectRent> subjectRentList = session.createQuery("from BusinessSubjectRent where contractId="+contractId).list();
			/*for (BusinessSubjectRent subjectRent : subjectRentList) {
				roomService.updateRoomCustomer(session, subjectRent.getRoomId(), contract,subjectRent);
			}*/
			ContractModifyLog log = new ContractModifyLog();
			setcreatemodify(log);
			log.setOperation("合同执行");
			log.setMemo("合同执行:"+contract.getName() +"["+contract.getCustomerName()+"]");
			log.setContractId(contractId);
			session.save(log);
			ParkLog parkLog = new ParkLog() ;
			parkLog.setCreator("合同执行");
			parkLog.setContent("合同执行" +contract.getName() +"【"+contract.getCustomerName()+"】");
			parkLog.setParkLogType(ParkLogTypeEnums.CONTRACT);
			parkLog.setCreateTime(new Date());
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
			session.save(parkLog);
			
			List<BusinessSubjectRent> srList = subjectRentService.getListByFilter(new Filter(BusinessSubjectRent.class).eq("contractId", contractId)).getValue();
			if(srList!=null&&srList.size()>0){
				for(BusinessSubjectRent sr : srList){
					RoomHistory rh = new RoomHistory();
					rh.setContractId(contractId);
					rh.setCustomerId(contract.getCustomerId());
					BusinessCustomer c = customerService.getBeanByFilter(new Filter(BusinessCustomer.class).eq("id",contract.getCustomerId())).getValue();
					CustomerInfo cif = c.getCustomerInfo();
					if(c!=null&&cif!=null&&cif.getCellphone()!=null){
						rh.setPhone(cif.getCellphone());
					}
					rh.setRoomId(sr.getRoomId());
					rh.setManagerId(contract.getManagerId());
					rh.setRentDate(new SimpleDateFormat("yyyy-MM-dd").format(sr.getStartDate())+"-"+new SimpleDateFormat("yyyy-MM-dd").format(sr.getEndDate()));
					setcreatemodify(rh);
					session.save(rh);
					
					Room room = (Room)session.get(Room.class, sr.getRoomId());
					room.setCustomerId(contract.getCustomerId());
					room.setCustomerName(contract.getCustomerName());
					
					room.setStatus(RoomStatusEnum.USING);
					room.setEndDate(contract.getEndDate());
					long time1 = sr.getStartDate().getTime();
					long time2 = sr.getEndDate().getTime();
					int days = (int) ((time2-time1)/(1000*60*60*24));
					double propertyFee = 0;
					if(contract.getPropertyUnit()!=null){
						propertyFee = days/30.0*sr.getRoomArea()*contract.getPropertyUnit().doubleValue();
					}
					if(sr.getRentPriceUnit()==PriceUnitEnum.DAY){
						room.setRentPrice(BigDecimal.valueOf(days*sr.getRoomArea()*sr.getRentPrice()+propertyFee));
					}
					else if(sr.getRentPriceUnit()==PriceUnitEnum.MONTH){
						room.setRentPrice(BigDecimal.valueOf(days/30.0*sr.getRoomArea()*sr.getRentPrice()+propertyFee));
					}else if(sr.getRentPriceUnit()==PriceUnitEnum.ONCE){
						room.setRentPrice(BigDecimal.valueOf(sr.getRentPrice()+propertyFee));
					}
					
					session.update(room);
				}
			}
			tr.commit();
			return Result.success("合同执行成功");
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure("合同执行失败");
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result submitRelet(BusinessContract t) {
		Long oldId = t.getId();
		Session session = null;
		Transaction tr = null;
		try {
			session = contractDao.openSession();
			tr = session.beginTransaction();
			User user = BusinessActivator.getSessionUser();
			BusinessContract old = (BusinessContract) session.get(BusinessContract.class, oldId);
			Date startDate = t.getStartDate();
			Date endDate = t.getEndDate();
			Date receiveDate = t.getReceiveDate();
			BeanUtil.copyProperties(old, t);
			t.setId(null);
			t.setStartDate(startDate);
			t.setEndDate(endDate);
			t.setReceiveDate(receiveDate);
			t.setName(old.getName()+"(续租)");
			t.setState(ContractStatusEnum.NEW);
			t.setCode(generateCode(null).getValue());
			t.setCreateTime(new Date());
			t.setCreator(user.getRealName());
			t.setCreatorId(user.getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			t.setRentState(ContractRentStatusEnum.RELET);
			session.save(t);
			List<BusinessSubjectRent> subjectRentList = session.createQuery("from BusinessSubjectRent where contractId="+oldId).list();
			for (BusinessSubjectRent oldSubjectRent : subjectRentList) {
				BusinessSubjectRent subjectRent = new BusinessSubjectRent();
				BeanUtil.copyProperties(oldSubjectRent, subjectRent);
				subjectRent.setId(null);
				subjectRent.setContractId(t.getId());
				setcreatemodify(subjectRent);
				session.save(subjectRent);
			}
			List<BusinessContractAtt> attList = session.createQuery("from BusinessContractAtt where contractId="+oldId).list();
			for (BusinessContractAtt contractAtt : attList) {
				BusinessContractAtt att = new BusinessContractAtt();
				BeanUtil.copyProperties(contractAtt, att);
				setcreatemodify(att);
				att.setId(null);
				att.setContractId(t.getId());
				session.save(att);
			}
			ContractModifyLog log = new ContractModifyLog();
			setcreatemodify(log);
			log.setOperation("续租");
			log.setMemo("合同续租 续租产生的新合同为"+t.getName()+"("+t.getCode()+")");
			log.setContractId(oldId);
			session.save(log);
			ParkLog parkLog = new ParkLog() ;
			parkLog.setCreator("续租");
			parkLog.setContent("合同续租 续租产生的新合同为"+t.getName()+"【"+t.getCode()+"】");
			parkLog.setParkLogType(ParkLogTypeEnums.CONTRACT);
			parkLog.setCreateTime(new Date());
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getUsername());
			session.save(parkLog);
			tr.commit();
			return Result.success("合同续租成功");
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),BusinessContract.class)));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure("合同续租成功");
		} finally {
			session.close();
		}
	}

	@Override
	public Result saveRentContract1(BusinessContract contract,Map<String,String[]> roomRent,Map<String,String[]> billPlanRent,Map<String,String[]> deposit) {
		Session session = null;
		Transaction tr = null;
		try {
			session = contractDao.openSession();
			/*if(contract.getContractNo()!=null){
				int count = session.createCriteria(Contract.class).add(Restrictions.eq("contractNo", contract.getContractNo())).list().size();
				if(count>0){
					return Result.failure("合同编号已经存在 ，保存失败");
				}
			}*/
			tr = session.beginTransaction();
			contract.setState(ContractStatusEnum.NEW);
			contract.setName(contract.getItem().getTitle()+"["+contract.getCustomerName()+"]");
//			contract.setName(contract.getCustomerName()+DateUtil.format(new Date(),"yyyyMMddHHmm"));
			if(contract.getRoomTypeId()!=null && ("").equals(contract.getRoomTypeId())){
				contract.setRoomTypeId(null);
			}
			if(contract.getPropertyNoId()!=null && ("").equals(contract.getPropertyNoId())){
				contract.setPropertyNoId(null);
			}
			setcreatemodify(contract);
			session.save(contract);
			Map<Long,BusinessSubjectRent> subjectMap = new HashMap<Long, BusinessSubjectRent>();
			if(roomRent.get("roomIds")!=null){
				for (int i = 0; i < roomRent.get("roomIds").length; i++) {
					/* delete by jonathan 20150209
					SubjectRent rent = new SubjectRent();
					*/
					BusinessSubjectRent rent = new BusinessSubjectRent();
					rent.setContractId(contract.getId());
					rent.setStartDate(DateUtil.parse(roomRent.get("startDate")[i]));
					rent.setEndDate(DateUtil.parse(roomRent.get("endDate")[i]));
					/*rent.setManagePrice(Double.valueOf(roomRent.get("managePrice")[i]));
					rent.setManagePriceUnit(PriceUnitEnum.valueOf(roomRent.get("managePriceUnit")[i]));*/
					rent.setRentPrice(Double.valueOf(roomRent.get("rentPrices")[i]));
					PriceUnitEnum enum1 = PriceUnitEnum.valueOf(roomRent.get("rentPriceUnit")[i]);
					rent.setRentPriceUnit(enum1);
					//rent.setRentPriceUnit(PriceUnitEnum.valueOf(roomRent.get("rentPriceUnit")[i]));
					rent.setRoomArea(Double.valueOf(roomRent.get("roomAreas")[i]));
					rent.setRoomId(Long.valueOf(roomRent.get("roomIds")[i]));
					rent.setRoomName(roomRent.get("roomNames")[i]);
					if(!("").equals(roomRent.get("rebateRuleId")[i])){
						rent.setRebateRuleId(roomRent.get("rebateRuleId")[i]);
					}else{
						rent.setRebateRuleId(null);
					}
					if(roomRent.get("rebate")!=null && roomRent.get("rebate")[i]!=null){
						rent.setRebate(BooleanEnum.valueOf(roomRent.get("rebate")[i]));
					}
					if(roomRent.get("memo")!=null && roomRent.get("memo")[i]!=null){
						rent.setMemo(roomRent.get("memo")[i]);
					}
					/* delete by jonathan 20150209 执行合同时才记录历史房间信息
					RoomHistory rh = new RoomHistory();
					//rh.setContract(contract);
					rh.setContractId(contract.getId());
					BusinessCustomer c = customerService.getBeanByFilter(new Filter(BusinessCustomer.class).eq("id",contract.getCustomerId())).getValue();
					rh.setCustomerId(contract.getCustomerId());
					CustomerInfo cif = c.getCustomerInfo();
					if(c!=null&&cif!=null&&cif.getCellphone()!=null){
						rh.setPhone(cif.getCellphone());
					}
					rh.setRoomId(Long.valueOf(roomRent.get("roomIds")[i]));
					rh.setManagerId(contract.getManagerId());
					rh.setRentDate(roomRent.get("startDate")[i]+"——"+roomRent.get("endDate")[i]);
					setcreatemodify(rh);
					session.save(rh);
					*/
					setcreatemodify(rent);
					session.save(rent);
					subjectMap.put(rent.getRoomId(), rent);
				}
			}
			if(billPlanRent.get("feeTypes")!=null){
				for (int i = 0; i < billPlanRent.get("feeTypes").length; i++) {
					BusinessBillPlanRent plan = new BusinessBillPlanRent();
					plan.setContractId(contract.getId());
					plan.setFeeType(BusinessFeeEnum.valueOf(billPlanRent.get("feeTypes")[i]));
					plan.setPlanFee(Double.valueOf(billPlanRent.get("planFees")[i]));
					plan.setRealFee(Double.valueOf(billPlanRent.get("realFees")[i]));
					plan.setPlanPayDate(DateUtil.parse(billPlanRent.get("planPayDates")[i]));
					plan.setStartDate(DateUtil.parse(billPlanRent.get("planStartDates")[i]));
					plan.setEndDate(DateUtil.parse(billPlanRent.get("planEndDates")[i]));
					if(billPlanRent.get("planStatus")!=null && billPlanRent.get("planStatus")[i]!=null && billPlanRent.get("planStatus")[i]!=""){
						plan.setStatus(BillPlanStatusEnum.valueOf(billPlanRent.get("planStatus")[i]));
					}else{
						plan.setStatus(BillPlanStatusEnum.UNCHECK);
					}
					if(billPlanRent.get("planMemos")!=null && billPlanRent.get("planMemos")[i]!=null){
						plan.setMemo(billPlanRent.get("planMemos")[i]);
					}
					if(billPlanRent.get("planRoomIds") !=null && subjectMap.get(billPlanRent.get("planRoomIds")[i])!=null){
						plan.setSubjectId(subjectMap.get(billPlanRent.get("planRoomIds")[i]).getId());
					}
					if(billPlanRent.get("planRoomIds")!=null && !("").equals(billPlanRent.get("planRoomIds")[i])){
						plan.setRoomName(billPlanRent.get("planRoomNames")[i]);
						plan.setRoomId(Long.valueOf(billPlanRent.get("planRoomIds")[i]));
					}
					plan.setAutoCheck(BooleanEnum.NO);
					setcreatemodify(plan);
					session.save(plan);
				}
			}	
			
			tr.commit();
			return Result.success("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure("保存失败");
		} finally {
			session.close();
		}
		
	}

	@Override
	public Result saveNetContract1(BusinessContract contract, String[] netIds, String[] prices,
			String[] ips, String[] ports, String[] ipPubs,
			String[] netStartDates, String[] netEndDates, String[] feeTypes,
			String[] moneys, String[] playPayDates, String[] planStartDates,
			String[] planEndDates, String autocheck) {
		Session session = null;
		Transaction tr = null;
		try {
			session = contractDao.openSession();
			/*if(contract.getContractNo()!=null){
				int count = session.createCriteria(Contract.class).add(Restrictions.eq("contractNo", contract.getContractNo())).list().size();
				if(count>0){
					return Result.failure("合同编号已经存在 ，保存失败");
				}
			}*/
			tr = session.beginTransaction();
			contract.setState(ContractStatusEnum.NEW);
			contract.setName(contract.getCustomerName());
			contract.setCode(generateCode(null).getValue());
			setcreatemodify(contract);
			session.save(contract);
			tr.commit();
			return Result.success("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure("保存失败");
		} finally {
			session.close();
		}
		
	}

	@Override
	public Result<List<BusinessContract>> getListByHql(String Hql) {
		try {
			return Result.value(contractDao.getListByHql(Hql));
		} catch (Exception e) {
			return Result.failure(R.BusinessContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result getRowCount(Filter filter) {
		try {
			return Result.value(contractDao.getRowCount(filter));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}