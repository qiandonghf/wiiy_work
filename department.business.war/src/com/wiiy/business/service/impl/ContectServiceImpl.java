package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.ContectDao;
import com.wiiy.business.entity.BusinessCustomer;
import com.wiiy.business.entity.Contect;
import com.wiiy.business.entity.BusinessContectInfo;
import com.wiiy.business.entity.CustomerModifyLog;
import com.wiiy.business.entity.ParkLog;
import com.wiiy.business.preferences.R;
import com.wiiy.business.preferences.enums.ParkLogTypeEnums;
import com.wiiy.business.service.ContectService;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.external.service.CardPubService;
import com.wiiy.external.service.dto.CardDto;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class ContectServiceImpl implements ContectService{
	
	private ContectDao contectDao;
	
	public void setContectDao(ContectDao contectDao) {
		this.contectDao = contectDao;
	}

	@Override
	public Result<Contect> save(Contect t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = contectDao.openSession();
			tr = session.beginTransaction();
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			if(t.getMain().equals(BooleanEnum.YES)){
				session.createQuery("update Contect set main = 'NO' where customerId = "+t.getCustomerId()).executeUpdate();
			}
			session.save(t);
			BusinessCustomer customer = (BusinessCustomer)session.get(BusinessCustomer.class,t.getCustomerId());
			CustomerModifyLog customerModifyLog = new CustomerModifyLog();
			customerModifyLog.setContent("添加联系人【"+t.getName()+"】    "+customer.getName());
			customerModifyLog.setCustomer(customer);
			customerModifyLog.setCustomerId(t.getCustomerId());
			customerModifyLog.setCreateTime(new Date());
			customerModifyLog.setCreator(BusinessActivator.getSessionUser().getRealName());
			customerModifyLog.setCreatorId(BusinessActivator.getSessionUser().getId());
			customerModifyLog.setModifyTime(new Date());
			customerModifyLog.setModifier(t.getCreator());
			customerModifyLog.setModifierId(t.getCreatorId());
			customerModifyLog.setModifyLogTime(t.getCreateTime());
			customerModifyLog.setEntityStatus(EntityStatus.NORMAL);
			session.save(customerModifyLog);
			ParkLog parkLog = new ParkLog() ;
			parkLog.setContent("添加联系人【"+t.getName()+"】    "+customer.getName());
			parkLog.setParkLogType(ParkLogTypeEnums.CUSTOMERMODIFY);
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
			parkLog.setCreateTime(new Date());
			session.save(parkLog);
			tr.commit();
			return Result.success(R.Contect.SAVE_SUCCESS,t);
		} catch (ConstraintViolationException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(new UKConstraintException(e).getUK(),Contect.class)));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.Contect.UPDATE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<Contect> delete(Contect t) {
		try {
			contectDao.delete(t);
			return Result.success(R.Contect.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.Contect.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Contect> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = contectDao.openSession();
			tr = session.beginTransaction();
			Contect t = (Contect)session.get(Contect.class, id);
			List<BusinessContectInfo> contectInfos = session.createQuery("from BusinessContectInfo where contectId = "+id).list();
			if(contectInfos!=null && contectInfos.size()>0){
				return Result.failure("交往信息中有相关信息，请先删除关联信息"); 
			}
			BusinessCustomer customer = (BusinessCustomer)session.get(BusinessCustomer.class,t.getCustomerId());
			CustomerModifyLog customerModifyLog = new CustomerModifyLog();
			customerModifyLog.setContent("删除联系人【"+t.getName()+"】    "+customer.getName());
			customerModifyLog.setCustomer(customer);
			customerModifyLog.setCustomerId(t.getCustomerId());
			customerModifyLog.setCreateTime(new Date());
			customerModifyLog.setCreator(BusinessActivator.getSessionUser().getRealName());
			customerModifyLog.setCreatorId(BusinessActivator.getSessionUser().getId());
			customerModifyLog.setModifyTime(new Date());
			customerModifyLog.setModifier(t.getCreator());
			customerModifyLog.setModifierId(t.getCreatorId());
			customerModifyLog.setModifyLogTime(t.getCreateTime());
			customerModifyLog.setEntityStatus(EntityStatus.NORMAL);
			System.out.println(customerModifyLog.getModifyLogTime());
			session.save(customerModifyLog);
			session.delete(t);
			ParkLog parkLog = new ParkLog() ;
			parkLog.setContent("删除联系人【"+t.getName()+"】    "+customer.getName());
			parkLog.setParkLogType(ParkLogTypeEnums.CUSTOMERMODIFY);
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
			parkLog.setCreateTime(new Date());
			session.save(parkLog);
			tr.commit();
			return Result.success(R.Contect.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.Contect.DELETE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<Contect> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = contectDao.openSession();
			tr = session.beginTransaction();
			List<Contect> list = session.createQuery("from Contect where id in ("+ids+")").list();
			List<BusinessContectInfo> contectInfos = session.createQuery("from BusinessContectInfo where contectId in ("+ids+")").list();
			Map<Long,BusinessContectInfo> contectInfoMap = new HashMap<Long, BusinessContectInfo>();
			if(contectInfos!=null && contectInfos.size()>0){
				for (BusinessContectInfo contectInfo : contectInfos) {
					contectInfoMap.put(contectInfo.getContectId(), contectInfo);
				}
			}
			String str = "" ;
			for (Contect contect : list) {
				if(contectInfoMap.get(contect.getId())!=null){
					return Result.failure("交往信息中有相关信息，请先删除关联信息");
				}
				BusinessCustomer customer = (BusinessCustomer)session.get(BusinessCustomer.class,contect.getCustomerId());
				CustomerModifyLog customerModifyLog = new CustomerModifyLog();
				customerModifyLog.setContent("删除联系人【"+contect.getName()+"】");
				customerModifyLog.setCustomer(customer);
				customerModifyLog.setCustomerId(contect.getCustomerId());
				customerModifyLog.setCreateTime(new Date());
				customerModifyLog.setCreator(BusinessActivator.getSessionUser().getRealName());
				customerModifyLog.setCreatorId(BusinessActivator.getSessionUser().getId());
				customerModifyLog.setModifyTime(new Date());
				customerModifyLog.setModifier(contect.getCreator());
				customerModifyLog.setModifierId(contect.getCreatorId());
				customerModifyLog.setModifyLogTime(contect.getCreateTime());
				customerModifyLog.setEntityStatus(EntityStatus.NORMAL);
				session.save(customerModifyLog);
				session.delete(contect);
				str = str +"删除联系人【"+contect.getName()+"】    "+customer.getName()+";";
			}
			ParkLog parkLog = new ParkLog() ;
			parkLog.setContent(str);
			parkLog.setParkLogType(ParkLogTypeEnums.CUSTOMERMODIFY);
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
			parkLog.setCreateTime(new Date());
			session.save(parkLog);
			tr.commit();
			return Result.success(R.Contect.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.Contect.DELETE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<Contect> update(Contect t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = contectDao.openSession();
			tr = session.beginTransaction();
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			if(t.getMain().equals(BooleanEnum.YES)){
				session.createQuery("update Contect set main = 'NO' where customerId = "+t.getCustomerId()).executeUpdate();
			}
			session.update(t);
			BusinessCustomer customer = (BusinessCustomer)session.get(BusinessCustomer.class,t.getCustomerId());
			CustomerModifyLog customerModifyLog = new CustomerModifyLog();
			customerModifyLog.setContent("更新联系人【"+t.getName()+"】    "+customer.getName());
			customerModifyLog.setCustomer(customer);
			customerModifyLog.setCustomerId(t.getCustomerId());
			customerModifyLog.setCreateTime(new Date());
			customerModifyLog.setCreator(BusinessActivator.getSessionUser().getRealName());
			customerModifyLog.setCreatorId(BusinessActivator.getSessionUser().getId());
			customerModifyLog.setModifyTime(new Date());
			customerModifyLog.setModifier(customerModifyLog.getCreator());
			customerModifyLog.setModifierId(customerModifyLog.getCreatorId());
			customerModifyLog.setModifyLogTime(customerModifyLog.getCreateTime());
			customerModifyLog.setEntityStatus(EntityStatus.NORMAL);
			session.save(customerModifyLog);
			ParkLog parkLog = new ParkLog() ;
			parkLog.setContent("更新联系人【"+t.getName()+"】    "+customer.getName());
			parkLog.setParkLogType(ParkLogTypeEnums.CUSTOMERMODIFY);
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
			parkLog.setCreateTime(new Date());
			session.save(parkLog);
			tr.commit();
			return Result.success(R.Contect.UPDATE_SUCCESS,t);
		} catch (ConstraintViolationException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(new UKConstraintException(e).getUK(),Contect.class)));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.Contect.UPDATE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<Contect> getBeanById(Serializable id) {
		try {
			return Result.value(contectDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.Contect.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Contect> getBeanByFilter(Filter filter) {
		try {
			return Result.value(contectDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Contect.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Contect>> getList() {
		try {
			return Result.value(contectDao.getList());
		} catch (Exception e) {
			return Result.failure(R.Contect.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Contect>> getListByFilter(Filter filter) {
		try {
			return Result.value(contectDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Contect.LOAD_FAILURE);
		}
	}

	@Override
	public Result<?> importCard(String ids) {
		try{
			/*Config config = BusinessActivator.getAppConfig().getConfig("cardGroupName");
			String groupName=config.getParameter("name");*/
			String groupName = BusinessActivator.getAppParamService().loadCardGroupName();
			CardPubService cardPubService = BusinessActivator.getService(CardPubService.class);
			List<CardDto> cardDtoList = new ArrayList<CardDto>();
			Filter filter = new Filter(Contect.class);
			if(!("").equals(ids)){
				String [] idss = ids.split(",");
				Long[] idsl = new Long[idss.length];
				int index=0;
				for(String s : idss){
					idsl[index++] = Long.parseLong(idss[index-1]);
				}
				filter.in("id", idsl);
			}
			List<Contect> contectList = contectDao.getListByFilter(filter);
			for(Contect contect : contectList){
				cardDtoList.add(populate(contect, groupName));
			}
			cardPubService.ImportCards(cardDtoList);
			BusinessActivator.getOperationLogService().logOP("导入名片组【"+groupName+"】");
			return Result.success("导入成功");
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure("导入失败");
		}
	}

	private CardDto populate(Contect contect, String groupName) {
		CardDto card = new CardDto();
		card.setGroupName(groupName);
		card.setCateName("联系人");
		card.setName(contect.getName());
		card.setMobile(contect.getMobile());
		if(card.getMobile()==null){
			card.setMobile("");
		}
		card.setFax(contect.getFax());
		card.setMsn(contect.getMsn());
		card.setQq(contect.getQq());
		card.setGender(contect.getGender());
		card.setBirthDay(contect.getBirthDay());
		card.setEmail(contect.getEmail());
		card.setPosition(contect.getPosition());
		card.setHomeAddr(contect.getHomeAddr());
		card.setHomeTel(contect.getHomePhone());
		card.setMemo(contect.getMemo());
		
		return card;
	}

}
