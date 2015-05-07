package com.wiiy.business.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.business.dao.InvestmentVentureTypeDao;
import com.wiiy.business.entity.Investment;
import com.wiiy.business.entity.InvestmentVentureType;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.InvestmentVentureTypeService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.business.activator.BusinessActivator;

public class InvestmentVentureTypeServiceImpl implements InvestmentVentureTypeService{
	private InvestmentVentureTypeDao investmentVentureTypeDao;
	public void setInvestmentVentureTypeDao(
			InvestmentVentureTypeDao investmentVentureTypeDao) {
		this.investmentVentureTypeDao = investmentVentureTypeDao;
	}
	

	@Override
	public Result<InvestmentVentureType> save(InvestmentVentureType t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			investmentVentureTypeDao.save(t);
			return Result.success(R.InvestmentVentureType.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentVentureType.class)));
		} catch (Exception e) {
			return Result.failure(R.InvestmentVentureType.SAVE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentVentureType> update(InvestmentVentureType t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			investmentVentureTypeDao.update(t);
			return Result.success(R.InvestmentVentureType.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentVentureType.class)));
		} catch (Exception e) {
			return Result.failure(R.InvestmentVentureType.UPDATE_FAILURE);
		}
	}
	
	@Override
	public Result<InvestmentVentureType> delete(InvestmentVentureType t) {
		try {
			investmentVentureTypeDao.delete(t);
			return Result.success(R.InvestmentVentureType.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.InvestmentVentureType.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentVentureType> deleteById(Serializable id) {
		try {
			investmentVentureTypeDao.deleteById(id);
			return Result.success(R.InvestmentVentureType.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.InvestmentVentureType.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentVentureType> deleteByIds(String ids) {
		try {
			investmentVentureTypeDao.deleteByIds(ids);
			return Result.success(R.InvestmentVentureType.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.InvestmentVentureType.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentVentureType> getBeanByFilter(Filter filter) {
		try {
			return Result.value(investmentVentureTypeDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.InvestmentVentureType.LOAD_FAILURE);
		}
	}
	
	@Override
	public Result<InvestmentVentureType> getBeanById(Serializable id) {
		try {
			return Result.value(investmentVentureTypeDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.InvestmentVentureType.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<InvestmentVentureType>> getList() {
		try {
			return Result.value(investmentVentureTypeDao.getList());
		} catch (Exception e) {
			return Result.failure(R.InvestmentVentureType.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<InvestmentVentureType>> getListByFilter(Filter filter) {
		try {
			return Result.value(investmentVentureTypeDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.InvestmentVentureType.LOAD_FAILURE);
		}
	}
}
