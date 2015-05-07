package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.StafferDao;
import com.wiiy.business.entity.BusinessCustomer;
import com.wiiy.business.entity.CustomerModifyLog;
import com.wiiy.business.entity.ParkLog;
import com.wiiy.business.entity.Staffer;
import com.wiiy.business.preferences.R;
import com.wiiy.business.preferences.enums.ParkLogTypeEnums;
import com.wiiy.business.service.StafferService;
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
public class StafferServiceImpl implements StafferService{
	
	private StafferDao stafferDao;
	
	public void setStafferDao(StafferDao stafferDao) {
		this.stafferDao = stafferDao;
	}

	@Override
	public Result<Staffer> save(Staffer t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = stafferDao.openSession();
			tr = session.beginTransaction();
			if (("").equals(t.getDegreeId())) {
				t.setDegreeId(null);
			}
			if (("").equals(t.getPositionId())) {
				t.setPositionId(null);
			}
			if(("").equals(t.getPoliticalId())){
				t.setPoliticalId(null);
			}
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			
			if(t.getManager() == BooleanEnum.YES || t.getLegal() == BooleanEnum.YES){
				List<Staffer> staffers = getListByFilter(new Filter(Staffer.class).eq("customerId",t.getCustomerId()).or(Filter.Eq("manager", BooleanEnum.YES),Filter.Eq("legal", BooleanEnum.YES))).getValue();
				for (Staffer staffer : staffers) {
					if(staffer.getManager() == BooleanEnum.YES && t.getManager() == BooleanEnum.YES){
						staffer.setManager(BooleanEnum.NO);
					}else if(staffer.getLegal() == BooleanEnum.YES && t.getLegal() == BooleanEnum.YES){
						staffer.setLegal(BooleanEnum.NO);
					}
					session.update(staffer);
				}
			}
			session.save(t);
			BusinessCustomer customer = (BusinessCustomer)session.get(BusinessCustomer.class,t.getCustomerId());
			CustomerModifyLog customerModifyLog = new CustomerModifyLog();
			customerModifyLog.setContent("添加主要人员【"+t.getName()+"】");
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
			parkLog.setContent("添加主要人员【"+t.getName()+"】    "+customer.getName());
			parkLog.setParkLogType(ParkLogTypeEnums.CUSTOMERMODIFY);
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
			parkLog.setCreateTime(new Date());
		    session.save(parkLog);
			tr.commit();
			return Result.success(R.Staffer.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Staffer.class)));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.Staffer.SAVE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<Staffer> delete(Staffer t) {
		try {
			stafferDao.delete(t);
			return Result.success(R.Staffer.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.Staffer.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Staffer> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = stafferDao.openSession();
			tr = session.beginTransaction();
			Staffer t = (Staffer)session.get(Staffer.class, id);
			BusinessCustomer customer = (BusinessCustomer)session.get(BusinessCustomer.class,t.getCustomerId());
			CustomerModifyLog customerModifyLog = new CustomerModifyLog();
			customerModifyLog.setContent("删除主要人员【"+t.getName()+"】");
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
			session.delete(t);
			ParkLog parkLog = new ParkLog() ;
			parkLog.setContent("删除主要人员【"+t.getName()+"】    "+customer.getName());
			parkLog.setParkLogType(ParkLogTypeEnums.CUSTOMERMODIFY);
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
			parkLog.setCreateTime(new Date());
		    session.save(parkLog);
			tr.commit();
			return Result.success(R.Staffer.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.Staffer.DELETE_FAILURE);
		} finally{
			session.close();
		}
	}

	@Override
	public Result<Staffer> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = stafferDao.openSession();
			tr = session.beginTransaction();
			List<Staffer> list = session.createQuery("from Staffer where id in ("+ids+")").list();
			String str = "" ;
			for (Staffer t : list) {
				BusinessCustomer customer = (BusinessCustomer)session.get(BusinessCustomer.class,t.getCustomerId());
				CustomerModifyLog customerModifyLog = new CustomerModifyLog();
				customerModifyLog.setContent("删除主要人员【"+t.getName()+"】");
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
				session.delete(t);
				str = str  +"删除主要人员【"+t.getName()+"】    "+customer.getName()+";";
			}
			ParkLog parkLog = new ParkLog() ;
			parkLog.setContent(str);
			parkLog.setParkLogType(ParkLogTypeEnums.CUSTOMERMODIFY);
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
			parkLog.setCreateTime(new Date());
		    session.save(parkLog);
			tr.commit();
			return Result.success(R.Staffer.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.Staffer.DELETE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<Staffer> update(Staffer t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = stafferDao.openSession();
			tr = session.beginTransaction();
			if (("").equals(t.getDegreeId())) {
				t.setDegreeId(null);
			}
			if (("").equals(t.getPositionId())) {
				t.setPositionId(null);
			}
			if(("").equals(t.getPoliticalId())){
				t.setPoliticalId(null);
			}
			
			if(t.getManager() == BooleanEnum.YES || t.getLegal() == BooleanEnum.YES){
				List<Staffer> staffers = getListByFilter(new Filter(Staffer.class).ne("id", t.getId()).eq("customerId",t.getCustomerId()).or(Filter.Eq("manager", BooleanEnum.YES),Filter.Eq("legal", BooleanEnum.YES))).getValue();
				for (Staffer staffer : staffers) {
					if(staffer.getManager() == BooleanEnum.YES && t.getManager() == BooleanEnum.YES){
						staffer.setManager(BooleanEnum.NO);
					}else if(staffer.getLegal() == BooleanEnum.YES && t.getLegal() == BooleanEnum.YES){
						staffer.setLegal(BooleanEnum.NO);
					}
					session.update(staffer);
				}
			}
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			session.update(t);
			BusinessCustomer customer = (BusinessCustomer)session.get(BusinessCustomer.class,t.getCustomerId());
			CustomerModifyLog customerModifyLog = new CustomerModifyLog();
			customerModifyLog.setContent("更新主要人员【"+t.getName()+"】");
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
			parkLog.setContent("更新主要人员【"+t.getName()+"】    "+customer.getName());
			parkLog.setParkLogType(ParkLogTypeEnums.CUSTOMERMODIFY);
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
			parkLog.setCreateTime(new Date());
		    session.save(parkLog);
			tr.commit();
			return Result.success(R.Staffer.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Staffer.class)));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.Staffer.UPDATE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<Staffer> getBeanById(Serializable id) {
		try {
			return Result.value(stafferDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.Staffer.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Staffer> getBeanByFilter(Filter filter) {
		try {
			return Result.value(stafferDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Staffer.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Staffer>> getList() {
		try {
			return Result.value(stafferDao.getList());
		} catch (Exception e) {
			return Result.failure(R.Staffer.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Staffer>> getListByFilter(Filter filter) {
		try {
			return Result.value(stafferDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Staffer.LOAD_FAILURE);
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
			Filter filter = new Filter(Staffer.class);
			if(!("").equals(ids)){
				String [] idss = ids.split(",");
				Long[] idsl = new Long[idss.length];
				int index=0;
				for(String s : idss){
					idsl[index++] = Long.parseLong(idss[index-1]);
				}
				filter.in("id", idsl);
			}
			List<Staffer> stafferList = stafferDao.getListByFilter(filter);
			for(Staffer staffer : stafferList){
				cardDtoList.add(populate(staffer, groupName));
			}
			cardPubService.ImportCards(cardDtoList);
			BusinessActivator.getOperationLogService().logOP("导入名片组【"+groupName+"】");
			return Result.success("导入成功");
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure("导入失败");
		}
	}

	private CardDto populate(Staffer staffer, String groupName) {
		CardDto card = new CardDto();
		card.setGroupName(groupName);
		card.setCateName("主要人员");
		if(staffer.getPhone()==null){
			staffer.setPhone("");
		}
		card.setName(staffer.getName());
		card.setEmail(staffer.getEmail());
		card.setMobile(staffer.getPhone());
		card.setOfficeTel("");
		if(staffer.getPosition()!=null){
			card.setPosition(staffer.getPosition().getDataValue());
		}
		return card;
	}
	
}
