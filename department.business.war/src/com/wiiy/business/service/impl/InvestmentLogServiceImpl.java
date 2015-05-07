package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.InvestmentLogDao;
import com.wiiy.business.dao.ParkLogDao;
import com.wiiy.business.entity.InvestmentLog;
import com.wiiy.business.entity.ParkLog;
import com.wiiy.business.preferences.R;
import com.wiiy.business.preferences.enums.ParkLogTypeEnums;
import com.wiiy.business.service.InvestmentLogService;
import com.wiiy.business.service.InvestmentService;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class InvestmentLogServiceImpl implements InvestmentLogService{
	
	private InvestmentLogDao investmentLogDao;
	private InvestmentService investmentService;
	public void setInvestmentService(InvestmentService investmentService) {
		this.investmentService = investmentService;
	}
	private ParkLogDao parkLogDao ;
	public void setParkLogDao(ParkLogDao parkLogDao) {
		this.parkLogDao = parkLogDao;
	}
	public void setInvestmentLogDao(InvestmentLogDao investmentLogDao) {
		this.investmentLogDao = investmentLogDao;
	}

	@Override
	public Result<InvestmentLog> save(InvestmentLog t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			investmentLogDao.save(t);
			ParkLog parkLog = new ParkLog() ;
			parkLog.setContent(BusinessActivator.getSessionUser().getRealName()+"新建了"+investmentService.getBeanById(t.getInvestmentId()).getValue().getName()+"招商信息");
			parkLog.setCreateTime(new Date());
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
			parkLog.setParkLogType(ParkLogTypeEnums.INVESTMENT);
			parkLogDao.save(parkLog);
			
			return Result.success(R.InvestmentLog.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentLog.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentLog.SAVE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentLog> delete(InvestmentLog t) {
		try {
			investmentLogDao.delete(t);
			ParkLog parkLog = new ParkLog() ;
			parkLog.setContent(BusinessActivator.getSessionUser().getRealName()+"删除了"+t.getInvestment().getName()+"招商信息");
			parkLog.setCreateTime(new Date());
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
			parkLog.setParkLogType(ParkLogTypeEnums.INVESTMENT);
			parkLogDao.save(parkLog);
			return Result.success(R.InvestmentLog.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.InvestmentLog.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentLog> deleteById(Serializable id) {
		try {
			investmentLogDao.deleteById(id);
			ParkLog parkLog = new ParkLog() ;
			parkLog.setContent(BusinessActivator.getSessionUser().getRealName()+"删除了"+id+"号招商信息");
			parkLog.setCreateTime(new Date());
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
			parkLog.setParkLogType(ParkLogTypeEnums.INVESTMENT);
			parkLogDao.save(parkLog);
			return Result.success(R.InvestmentLog.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.InvestmentLog.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentLog> deleteByIds(String ids) {
		try {
			investmentLogDao.deleteByIds(ids);
			ParkLog parkLog = new ParkLog() ;
			parkLog.setContent(BusinessActivator.getSessionUser().getRealName()+"删除了["+ids+"]号招商信息");
			parkLog.setCreateTime(new Date());
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
			parkLog.setParkLogType(ParkLogTypeEnums.INVESTMENT);
			parkLogDao.save(parkLog);
			return Result.success(R.InvestmentLog.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.InvestmentLog.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentLog> update(InvestmentLog t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			investmentLogDao.update(t);
			ParkLog parkLog = new ParkLog() ;
			parkLog.setContent(BusinessActivator.getSessionUser().getRealName()+"修改了"+t.getInvestment().getName()+"招商信息");
			parkLog.setCreateTime(new Date());
			parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
			parkLog.setParkLogType(ParkLogTypeEnums.INVESTMENT);
			parkLogDao.save(parkLog);
			return Result.success(R.InvestmentLog.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentLog.class)));
		} catch (Exception e) {
			return Result.failure(R.InvestmentLog.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentLog> getBeanById(Serializable id) {
		try {
			return Result.value(investmentLogDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.InvestmentLog.LOAD_FAILURE);
		}
	}

	@Override
	public Result<InvestmentLog> getBeanByFilter(Filter filter) {
		try {
			return Result.value(investmentLogDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.InvestmentLog.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<InvestmentLog>> getList() {
		try {
			return Result.value(investmentLogDao.getList());
		} catch (Exception e) {
			return Result.failure(R.InvestmentLog.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<InvestmentLog>> getListByFilter(Filter filter) {
		try {
			return Result.value(investmentLogDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.InvestmentLog.LOAD_FAILURE);
		}
	}

}
