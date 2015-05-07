package com.wiiy.business.service.impl;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.CustomerDao;
import com.wiiy.business.dao.CustomerInfoDao;
import com.wiiy.business.entity.BusinessCustomer;
import com.wiiy.business.entity.CustomerInfo;
import com.wiiy.business.entity.CustomerLabelRef;
import com.wiiy.business.entity.CustomerModifyLog;
import com.wiiy.business.entity.CustomerQualification;
import com.wiiy.business.entity.CustomerVentureType;
import com.wiiy.business.entity.IncubationInfo;
import com.wiiy.business.entity.IncubationRoute;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.CustomerService;
import com.wiiy.business.util.Cipher;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.preferences.enums.UserTypeEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.commons.util.DateUtil;
import com.wiiy.core.entity.Org;
import com.wiiy.core.entity.User;
import com.wiiy.core.entity.UserRoleRef;
import com.wiiy.core.service.export.DataDictInitService;
import com.wiiy.core.service.export.OsgiUserService;
import com.wiiy.external.service.CardPubService;
import com.wiiy.external.service.dto.CardDto;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.ModulePrefixNamingStrategy;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class CustomerServiceImpl implements CustomerService{
	
	private CustomerDao businessCustomerDao;
	private CustomerInfoDao customerInfoDao;
	public void setBusinessCustomerDao(CustomerDao businessCustomerDao) {
		this.businessCustomerDao = businessCustomerDao;
	}
	public void setCustomerInfoDao(CustomerInfoDao customerInfoDao) {
		this.customerInfoDao = customerInfoDao;
	}
	@Override
	public Result<BusinessCustomer> save(BusinessCustomer customer,CustomerInfo customerInfo,IncubationInfo incubationInfo,String labelIds,String[] incubationRouteIds,String[] incubationRouteTimes,String[] customerQualificationIds,String[] custimerQualificationTimes,String[] enterpriseTypeIds) {
		Session session = null;
		Transaction tr = null;
		try {
			session = businessCustomerDao.openSession();
			tr = session.beginTransaction();
			customer.setTime(new Date());
			setCreatorModifier(customer);
			customer.setImproveArchive(BooleanEnum.NO);
			session.save(customer);
			if(labelIds!=null && labelIds.length()>0){
				labelIds = labelIds.replace(" ", "");
				if(labelIds.startsWith(","))labelIds = labelIds.substring(1);
				String[] ids = labelIds.split(",");
				for (int i = 0; i < ids.length; i++) {
					CustomerLabelRef ref = new CustomerLabelRef();
					ref.setCustomerId(customer.getId());
					ref.setLabelId(Long.parseLong(ids[i].trim()));
					setCreatorModifier(ref);
					session.save(ref);
				}
			}
			customerInfo.setCustomer(customer);
			incubationInfo.setCustomer(customer);
			setCreatorModifier(customerInfo);
			setCreatorModifier(incubationInfo);
			if(customerInfo.getTaxAddressId().equals("")){
				customerInfo.setTaxAddressId(null);
			}
			session.save(customerInfo);
			if(incubationInfo.getIncubateConfigId().equals("")){
				incubationInfo.setIncubateConfigId(null);
			}
			for (int i = 0; i < incubationRouteIds.length; i++) {
				IncubationRoute incubationRoute = new IncubationRoute();
				String routeId = incubationRouteIds[i];
				incubationRoute.setTime(null);
				if(incubationRouteTimes[i]!=null){
					Date time = DateUtil.parse(incubationRouteTimes[i]);
					incubationRoute.setTime(time);
				}
				incubationRoute.setRouteId(routeId);
				incubationRoute.setCustomerId(customer.getId());
				setCreatorModifier(incubationRoute);
				session.save(incubationRoute);
				if(incubationInfo.getStatusId()!=null){
					long newRouteId = Long.valueOf(routeId.replace("business.00", ""));
					if(newRouteId == incubationInfo.getStatusId()){
						DataDictInitService dataDictInitService = BusinessActivator.getDataDictInitService();
						String statusName = dataDictInitService.getDataDictById(routeId).getDataValue();
						incubationInfo.setStatusName(statusName);
						incubationInfo.setStatusId(incubationRoute.getId());
					}
				}
			}
			session.save(incubationInfo);
			for (int i = 0; i < customerQualificationIds.length; i++) {
				CustomerQualification customerQualification = new CustomerQualification();
				String qualificationId = customerQualificationIds[i];
				customerQualification.setTime(null);
				if(custimerQualificationTimes[i]!=null){
					Date time = DateUtil.parse(custimerQualificationTimes[i]);
					customerQualification.setTime(time);
				}
				customerQualification.setCustomerId(customer.getId());
				customerQualification.setQualificationId(qualificationId);
				setCreatorModifier(customerQualification);
				session.save(customerQualification);
			}
			if(enterpriseTypeIds!=null){	
				for(String enterpriseTypeId : enterpriseTypeIds){
					CustomerVentureType customerVentureType = new CustomerVentureType();
					customerVentureType.setModifyTime(new Date());
					customerVentureType.setCustomer(customer);
					customerVentureType.setCustomerId(customer.getId());
					customerVentureType.setVentureTypeId(enterpriseTypeId);
					session.save(customerVentureType);
				}
			}
			tr.commit();
			return Result.success(R.Customer.SAVE_SUCCESS,customer);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),BusinessCustomer.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.Customer.SAVE_FAILURE);
		} finally {
			session.close();
		}
	}
	
	private void setCreatorModifier(BaseEntity entity){
		entity.setCreateTime(new Date());
		entity.setCreatorId(BusinessActivator.getSessionUser().getId());
		entity.setCreator(BusinessActivator.getSessionUser().getRealName());
		entity.setModifyTime(entity.getCreateTime());
		entity.setModifier(entity.getCreator());
		entity.setModifierId(entity.getCreatorId());
		entity.setEntityStatus(EntityStatus.NORMAL);
	}
	
	@Override
	public Result<BusinessCustomer> save(BusinessCustomer t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			businessCustomerDao.save(t);
			return Result.success(R.Customer.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),BusinessCustomer.class)));
		} catch (Exception e) {
			return Result.failure(R.Customer.SAVE_FAILURE);
		}
	}

	@Override
	public Result<BusinessCustomer> delete(BusinessCustomer t) {
		try {
			businessCustomerDao.delete(t);
			return Result.success(R.Customer.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.Customer.DELETE_FAILURE);
		}
	}

	@Override
	public Result<BusinessCustomer> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			BusinessCustomer customer = (BusinessCustomer) businessCustomerDao.getBeanById(id);
			String path = customer.getIncubationInfo().getAgreementDocu();
			BusinessActivator.getResourcesService().delete(path);
			session = businessCustomerDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("delete CustomerInfo where id = "+id).executeUpdate();
			session.createQuery("delete IncubationInfo where id = "+id).executeUpdate();
			session.createQuery("delete CustomerLabelRef where customerId = "+id).executeUpdate();
			session.createQuery("delete CustomerQualification where customerId = " + id).executeUpdate();
			session.createQuery("delete CustomerVentureType where customerId = " + id).executeUpdate();
			session.createQuery("delete IncubationRoute where customerId = " + id).executeUpdate();
			//数据未完全删除 business_customer_modify_log
			session.createQuery("delete BusinessCustomer where id = "+id).executeUpdate();
			//删除企业,同时删除企业对应的登录用户信息
			session.createQuery("delete UserRoleRef where user_id = "+customer.getUserId()).executeUpdate();
			session.createQuery("delete User where id = "+customer.getUserId()).executeUpdate();
			tr.commit();
			return Result.success(R.Customer.DELETE_SUCCESS);
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure(BusinessActivator.getFKMessage(new FKConstraintException(e).getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure(R.Customer.DELETE_FAILURE);
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result<BusinessCustomer> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			List<String> list = businessCustomerDao.getObjectListByHql("select c.incubationInfo.agreementDocu from BusinessCustomer c where c.id in ("+ids+")");
			for (String path : list) {
				BusinessActivator.getResourcesService().delete(path);
			}
			
			session = businessCustomerDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("delete CustomerInfo where id in ("+ids+")").executeUpdate();
			session.createQuery("delete IncubationInfo where id in ("+ids+")").executeUpdate();
			session.createQuery("delete CustomerQualification where customerId in ("+ids+")").executeUpdate();
			session.createQuery("delete CustomerVentureType where customerId in ("+ids+")").executeUpdate();
			session.createQuery("delete IncubationRoute where customerId in ("+ids+")").executeUpdate();
			session.createQuery("delete BusinessCustomer where id in ("+ids+")").executeUpdate();
			
			//删除企业,同时删除企业对应的登录用户信息
			List<Long> userIdList = businessCustomerDao.getObjectListByHql("select c.userId from BusinessCustomer c where c.id in ("+ids+")");
			if(userIdList!=null && userIdList.size()>0){
				String userIds = "";
				for(int i=0;i<userIdList.size();i++){
					userIds += userIdList.get(i)+",";
				}
				String s = userIds.substring(userIds.length()-1);
				if(s.equals(",")){
					userIds = userIds.substring(0,userIds.length()-1);
					session.createQuery("delete UserRoleRef where user_id in ("+userIds+")").executeUpdate();
					session.createQuery("delete User where id in ("+userIds+")").executeUpdate();
				}
			}
			tr.commit();
			return Result.success(R.Customer.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.Customer.DELETE_FAILURE);
		} finally {
			session.close();
		}
	}
	

	@Override
	public Result update(BusinessCustomer customer, CustomerInfo customerInfo,
			IncubationInfo incubationInfo, String labelIds,String[] incubationRouteIds,String[] incubationRouteTimes,String[] customerQualificationIds,String[] custimerQualificationTimes,String[] enterpriseTypeIds) {
		Session session = null;
		Transaction tr = null;
		try {
			session = businessCustomerDao.openSession();
			tr = session.beginTransaction();
			
			BusinessCustomer dbCustomer = (BusinessCustomer) session.get(BusinessCustomer.class,customer.getId());
			BeanUtil.copyProperties(customer, dbCustomer);
			CustomerInfo dbCustomerInfo = (CustomerInfo) session.get(CustomerInfo.class,customerInfo.getId());
			BeanUtil.copyProperties(customerInfo, dbCustomerInfo);
			if(dbCustomerInfo.getRegTypeId().equals(""))dbCustomerInfo.setRegTypeId(null);
			if(dbCustomerInfo.getDocumentTypeId().equals(""))dbCustomerInfo.setDocumentTypeId(null);
			if(dbCustomerInfo.getCurrencyTypeId().equals(""))dbCustomerInfo.setCurrencyTypeId(null);
			IncubationInfo dbIncubationInfo = (IncubationInfo) session.get(IncubationInfo.class,incubationInfo.getId());
			BeanUtil.copyProperties(incubationInfo, dbIncubationInfo);
			
			setModifier(dbCustomer);
			setModifier(dbCustomerInfo);
			if(("").equals(dbIncubationInfo.getIncubateConfigId())){
				dbIncubationInfo.setIncubateConfigId(null);
			}
			setModifier(dbIncubationInfo);
			dbCustomer.setImproveArchive(checkImproveArchive(dbCustomer,customerInfo,incubationInfo));
			session.update(dbCustomer);
			if(("").equals(dbCustomerInfo.getTaxAddressId())){
				dbCustomerInfo.setTaxAddressId(null);
			}
			
			session.update(dbCustomerInfo);
			session.update(dbIncubationInfo);
			if(labelIds!=null && labelIds.length()>0){
				labelIds = labelIds.replace(" ", "");
				if(labelIds.startsWith(","))labelIds = labelIds.substring(1);
				String[] ids = labelIds.split(",");
				session.createQuery("delete from CustomerLabelRef where customerId = "+customer.getId()).executeUpdate();
				for (int i = 0; i < ids.length; i++) {
					CustomerLabelRef ref = new CustomerLabelRef();
					ref.setCustomerId(customer.getId());
					ref.setLabelId(Long.parseLong(ids[i].trim()));
					setCreatorModifier(ref);
					session.save(ref);
				}
			}else{
				session.createQuery("delete from CustomerLabelRef where customerId = "+customer.getId()).executeUpdate();
			}
			
			for (int i = 0; i < incubationRouteIds.length; i++) {
				String routeId = incubationRouteIds[i];
				IncubationRoute incubationRoute = (IncubationRoute) session.get(IncubationRoute.class, Long.valueOf(routeId));
				incubationRoute.setTime(null);
				if(incubationRouteTimes[i]!=null){
					Date time = DateUtil.parse(incubationRouteTimes[i]);
					incubationRoute.setTime(time);
				}
				setCreatorModifier(incubationRoute);
				session.update(incubationRoute);
			}
			for (int i = 0; i < customerQualificationIds.length; i++) {
				String qualificationId = customerQualificationIds[i];
				CustomerQualification customerQualification = (CustomerQualification) session.get(CustomerQualification.class, Long.valueOf(qualificationId));
				customerQualification.setTime(null);
				if(custimerQualificationTimes[i]!=null){
					Date time = DateUtil.parse(custimerQualificationTimes[i]);
					customerQualification.setTime(time);
				}
				setCreatorModifier(customerQualification);
				session.update(customerQualification);
			}
			String sql = "DELETE FROM "+ModulePrefixNamingStrategy.classToTableName(CustomerVentureType.class)+
					" WHERE customer_id = "+customer.getId()+" ";
					session.createSQLQuery(sql).executeUpdate();
			if(enterpriseTypeIds!=null){	
				for(String enterpriseTypeId : enterpriseTypeIds){
					CustomerVentureType customerVentureType = new CustomerVentureType();
					customerVentureType.setModifyTime(new Date());
					customerVentureType.setCustomer(customer);
					customerVentureType.setCustomerId(customer.getId());
					customerVentureType.setVentureTypeId(enterpriseTypeId);
					session.save(customerVentureType);
				}
			}
			CustomerModifyLog customerModifyLog = new CustomerModifyLog();
			customerModifyLog.setContent("更新企业信息");
			customerModifyLog.setCustomer(customer);
			customerModifyLog.setCustomerId(customer.getId());
			customerModifyLog.setCreateTime(new Date());
			customerModifyLog.setCreator(BusinessActivator.getSessionUser().getRealName());
			customerModifyLog.setCreatorId(BusinessActivator.getSessionUser().getId());
			customerModifyLog.setModifyTime(new Date());
			customerModifyLog.setModifier(BusinessActivator.getSessionUser().getRealName());
			customerModifyLog.setModifierId(BusinessActivator.getSessionUser().getId());
			customerModifyLog.setModifyLogTime(customerModifyLog.getModifyTime());
			customerModifyLog.setEntityStatus(EntityStatus.NORMAL);
			session.save(customerModifyLog);
			/*ParkLog parkLog = new ParkLog() ;
			parkLog.setContent(customer.getName()+"更新了企业信息");
			parkLog.setParkLogType(ParkLogTypeEnums.CUSTOMERMODIFY);
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
			parkLog.setCreateTime(new Date());
			session.save(parkLog);*/
			
			tr.commit();
			return Result.success(R.Customer.UPDATE_SUCCESS,customer);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),BusinessCustomer.class)));
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure(R.Customer.UPDATE_FAILURE);
		} finally {
			session.close();
		}
	}
	
	//完善档案判断
	private BooleanEnum checkImproveArchive(BusinessCustomer customer,CustomerInfo customerInfo,IncubationInfo incubationInfo){
		//判断条件
		
		return BooleanEnum.NO;
	}

	@Override
	public Result<BusinessCustomer> update(BusinessCustomer t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			businessCustomerDao.update(t);
			return Result.success(R.Customer.UPDATE_SUCCESS,t);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),BusinessCustomer.class)));
		} catch (Exception e) {
			return Result.failure(R.Customer.UPDATE_FAILURE);
		}
	}
	
	private void setModifier(BaseEntity entity){
		entity.setModifyTime(new Date());
		entity.setModifierId(BusinessActivator.getSessionUser().getId());
		entity.setModifier(BusinessActivator.getSessionUser().getRealName());
	}

	@Override
	public Result<BusinessCustomer> getBeanById(Serializable id) {
		try {
			return Result.value(businessCustomerDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.Customer.LOAD_FAILURE);
		}
	}

	@Override
	public Result<BusinessCustomer> getBeanByFilter(Filter filter) {
		try {
			return Result.value(businessCustomerDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Customer.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<BusinessCustomer>> getList() {
		try {
			return Result.value(businessCustomerDao.getList());
		} catch (Exception e) {
			return Result.failure(R.Customer.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<BusinessCustomer>> getListByFilter(Filter filter) {
		try {
			return Result.value(businessCustomerDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Customer.LOAD_FAILURE);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getCustomerLabelIdsByCustomerId(Long id) {
		StringBuilder sb = new StringBuilder();
		List<String> list = businessCustomerDao.getObjectListByHql("select labelId from CustomerLabelRef where customerId = "+id);
		for (String string : list) {
			sb.append(string).append(",");
		}
		if(sb.charAt(sb.length()-1)==',')sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}

	@Override
	public Result<String> generateCode() {
		String mouth =String.valueOf(CalendarUtil.now().get(Calendar.MONTH)+1);
		if(CalendarUtil.now().get(Calendar.MONTH)<10){
			mouth = "0"+mouth;
		}
		String code = "HY"+CalendarUtil.now().get(Calendar.YEAR)+mouth;
		BusinessCustomer customer = businessCustomerDao.getBeanByFilter(new Filter(BusinessCustomer.class).ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)).orderBy("id", Filter.DESC).maxResults(1));
		int count = businessCustomerDao.getRowCount(new Filter(BusinessCustomer.class).ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)));
		try {
			String oldCode = customer.getCode();
			count = Integer.parseInt(oldCode.replace("HY"+CalendarUtil.now().get(Calendar.YEAR)+mouth, ""));
		} catch (Exception e) {
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		nf.setMinimumIntegerDigits(2);
		code = code + nf.format(count+1);
		return Result.value(code);
	}
	@Override
	public Result saveAccount(Long id, String username, String password) {
		BusinessCustomer customer = businessCustomerDao.getBeanById(id);
		User user = new User();
		user.setEmail(customer.getCustomerInfo().getEmail());
		user.setPassword(Cipher.md5(password));
		user.setUsername(username);
		user.setRealName(customer.getName());
		Org org = new Org();
		org.setId(2l);
		user.setOrg(org);
		user.setUserType(UserTypeEnum.Customer);
		user.setRealName(customer.getName());
		OsgiUserService userService = BusinessActivator.getService(OsgiUserService.class);
		Result<User> result = userService.createUser(user);
		if(result.isSuccess()){
			customer.setUserId(user.getId());
			businessCustomerDao.update(customer);
			businessCustomerDao.executeSQLUpdate("insert into "+ModulePrefixNamingStrategy.classToTableName(UserRoleRef.class)+"(user_id,role_id) values("+user.getId()+",2)");
			return Result.success("账号新建成功");
		} else {
			return result;
		}
	}

	@Override
	public Result updateAccountPassword(Long id, String password) {
		OsgiUserService userService = BusinessActivator.getService(OsgiUserService.class);
		return userService.updateUser(id, password);
	}

	@Override
	public Result<BusinessCustomer> getSessionUserCustomer() {
		Long id = BusinessActivator.getSessionUser().getId();
		return Result.value(businessCustomerDao.getBeanByFilter(new Filter(BusinessCustomer.class).eq("userId", id)));
	}
	
	@Override
	public Result<?> importCard(String ids) {
		try{
			/*Config config = BusinessActivator.getAppConfig().getConfig("cardGroupName");
			String groupName=config.getParameter("name");*/
			String groupName = BusinessActivator.getAppParamService().loadCardGroupName();
			CardPubService cardPubService = BusinessActivator.getService(CardPubService.class);
			List<CardDto> cardDtoList = new ArrayList<CardDto>();
			Filter filter = new Filter(BusinessCustomer.class);
			if(!("").equals(ids)){
				String [] idss = ids.split(",");
				Long[] idsl = new Long[idss.length];
				int index=0;
				for(String s : idss){
					idsl[index++] = Long.parseLong(idss[index-1]);
				}
				filter.in("id", idsl);
			}
			List<BusinessCustomer> customerList = businessCustomerDao.getListByFilter(filter);
			for(BusinessCustomer customer : customerList){
				cardDtoList.add(populate(customer, groupName));
			}
			cardPubService.ImportCards(cardDtoList);
			BusinessActivator.getOperationLogService().logOP("导入名片组【"+groupName+"】");
			return Result.success("导入成功");
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure("导入失败");
		}
	}

	private CardDto populate(BusinessCustomer customer, String groupName) {
		CardDto card = new CardDto();
		card.setGroupName(groupName);
		card.setCateName("企业档案");
		card.setName(customer.getName());
		card.setMobile(customer.getCustomerInfo().getCellphone());
		card.setHomeAddr(customer.getCustomerInfo().getAddress());
		card.setOfficeTel(customer.getCustomerInfo().getOfficePhone());
		card.setHomePage(customer.getCustomerInfo().getWebSite());
		card.setFax(customer.getCustomerInfo().getFax());
		card.setMemo(customer.getCustomerInfo().getMemo());
		if(card.getMobile()==null){
			card.setMobile("");
		}
		return card;
	}

	@Override
	public List getObjectListBySql(String sql) {
		return businessCustomerDao.getObjectListBySql(sql);
	}

	@Override
	public void createCustomerAccount() {//给迁移到数据库中的企业创建账号 专用方法
		Session session = businessCustomerDao.openSession();
		Transaction tr = session.beginTransaction();
		List<BusinessCustomer> customerList = session.createQuery("from BusinessCustomer").list();
		flag:for (BusinessCustomer customer : customerList) {
			CustomerInfo customerInfo =customerInfoDao.getBeanByFilter(new Filter(CustomerInfo.class).createAlias("customer", "customer").eq("customer.id", customer.getId()));
			if(!customerInfo.getOrganizationNumber().equals("") && customerInfo.getOrganizationNumber()!=null){
				
				if(customerInfo.getOrganizationNumber().equals("74738658-9")){
					continue flag;
				}
				
				User user = new User();
				if(customer.getCustomerInfo().getEmail()==null||customer.getCustomerInfo().getEmail()==""){
					user.setEmail(null);
				}else{
					user.setEmail(customer.getCustomerInfo().getEmail());
				}
				user.setPassword(Cipher.md5("123456"));
				user.setUsername(customerInfo.getOrganizationNumber());
				user.setRealName(customer.getName());
				Org org = new Org();
				org.setId(2l);
				user.setOrg(org);
				user.setUserType(UserTypeEnum.Customer);
				user.setRealName(customer.getName());
				OsgiUserService userService = BusinessActivator.getService(OsgiUserService.class);
				Result<User> result = userService.createUser(user);
				customer.setUserId(user.getId());
				session.merge(customer);
				session.createSQLQuery("insert into "+ModulePrefixNamingStrategy.classToTableName(UserRoleRef.class)+"(user_id,role_id) values("+user.getId()+",2)").executeUpdate();
			}
		}
		tr.commit();
		session.close();
	}
	public Result<List<Object>> getListByLimitNum(int i) {
		try{
			Session session = businessCustomerDao.openSession();
			Transaction tr = session.beginTransaction();
			String sql = "select id,name,create_time from business_customer where park_status = 'INPARK' order by create_time desc limit 0,"+i;
		
			@SuppressWarnings("unchecked")
			List<Object> list = session.createSQLQuery(sql).list();
			Result<List<Object>> result = Result.success("加载成功",list);
			tr.commit();
			session.close();
			return result;
			}catch(Exception e){
				e.printStackTrace();
				
			}
			return Result.failure("加载失败");
	}
	@Override
	public Result<List<BusinessCustomer>> getListByHql(String hql) {
		try {
			return Result.value(businessCustomerDao.getListByHql(hql));
		} catch (Exception e) {
			return Result.failure(R.Customer.LOAD_FAILURE);
		}
	}
	@Override
	public Result<BusinessCustomer> getBeanByHql(String hql) {
		try {
			return Result.value(businessCustomerDao.getBeanByHql(hql));
		} catch (Exception e) {
			return Result.failure(R.Customer.LOAD_FAILURE);
		}
	}
	@Override
	public List getListBySql(String sql) {
		try {
			return businessCustomerDao.getObjectListBySql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Result getRowCount(Filter filter) {
		try {
			return Result.value(businessCustomerDao.getRowCount(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
