package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.business.entity.InvestmentDirector;
import com.wiiy.business.dao.InvestmentDirectorDao;
import com.wiiy.business.service.InvestmentDirectorService;
import com.wiiy.business.preferences.R;
import com.wiiy.business.activator.BusinessActivator;

/**
 * @author my
 */
public class InvestmentDirectorServiceImpl implements InvestmentDirectorService{
	
	private InvestmentDirectorDao investmentDirectorDao;
	
	public void setInvestmentDirectorDao(InvestmentDirectorDao investmentDirectorDao) {
		this.investmentDirectorDao = investmentDirectorDao;
	}

	@Override
	public Result<InvestmentDirector> save(InvestmentDirector t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			investmentDirectorDao.save(t);
			return Result.success(R.InvestmentDirector.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentDirector.class)));
		} catch (Exception e) {
			return Result.failure(R.InvestmentDirector.SAVE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentDirector> delete(InvestmentDirector t) {
		try {
			investmentDirectorDao.delete(t);
			return Result.success(R.InvestmentDirector.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.InvestmentDirector.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentDirector> deleteById(Serializable id) {
		try {
			investmentDirectorDao.deleteById(id);
			return Result.success(R.InvestmentDirector.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.InvestmentDirector.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentDirector> deleteByIds(String ids) {
		try {
			investmentDirectorDao.deleteByIds(ids);
			return Result.success(R.InvestmentDirector.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.InvestmentDirector.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentDirector> update(InvestmentDirector t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			investmentDirectorDao.update(t);
			return Result.success(R.InvestmentDirector.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentDirector.class)));
		} catch (Exception e) {
			return Result.failure(R.InvestmentDirector.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentDirector> getBeanById(Serializable id) {
		try {
			return Result.value(investmentDirectorDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.InvestmentDirector.LOAD_FAILURE);
		}
	}

	@Override
	public Result<InvestmentDirector> getBeanByFilter(Filter filter) {
		try {
			return Result.value(investmentDirectorDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.InvestmentDirector.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<InvestmentDirector>> getList() {
		try {
			return Result.value(investmentDirectorDao.getList());
		} catch (Exception e) {
			return Result.failure(R.InvestmentDirector.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<InvestmentDirector>> getListByFilter(Filter filter) {
		try {
			return Result.value(investmentDirectorDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.InvestmentDirector.LOAD_FAILURE);
		}
	}

}
