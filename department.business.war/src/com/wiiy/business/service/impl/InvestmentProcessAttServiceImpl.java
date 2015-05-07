package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.InvestmentProcessAttDao;
import com.wiiy.business.entity.InvestmentProcessAtt;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.InvestmentProcessAttService;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class InvestmentProcessAttServiceImpl implements InvestmentProcessAttService{
	
	private InvestmentProcessAttDao investmentProcessAttDao;
	
	public void setInvestmentProcessAttDao(InvestmentProcessAttDao investmentProcessAttDao) {
		this.investmentProcessAttDao = investmentProcessAttDao;
	}

	@Override
	public Result<InvestmentProcessAtt> save(InvestmentProcessAtt t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			investmentProcessAttDao.save(t);
			return Result.success(R.InvestmentProcessAtt.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentProcessAtt.class)));
		} catch (Exception e) {
			return Result.failure(R.InvestmentProcessAtt.SAVE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentProcessAtt> delete(InvestmentProcessAtt t) {
		try {
			investmentProcessAttDao.delete(t);
			return Result.success(R.InvestmentProcessAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.InvestmentProcessAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentProcessAtt> deleteById(Serializable id) {
		try {
			Result<InvestmentProcessAtt> result = getBeanById(id);
			String path = result.getValue().getNewName();
			BusinessActivator.getResourcesService().delete(path);
			investmentProcessAttDao.deleteById(id);
			return Result.success(R.InvestmentProcessAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.InvestmentProcessAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentProcessAtt> deleteByIds(String ids) {
		try {
			investmentProcessAttDao.deleteByIds(ids);
			return Result.success(R.InvestmentProcessAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.InvestmentProcessAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentProcessAtt> update(InvestmentProcessAtt t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			investmentProcessAttDao.update(t);
			return Result.success(R.InvestmentProcessAtt.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentProcessAtt.class)));
		} catch (Exception e) {
			return Result.failure(R.InvestmentProcessAtt.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentProcessAtt> getBeanById(Serializable id) {
		try {
			return Result.value(investmentProcessAttDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.InvestmentProcessAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<InvestmentProcessAtt> getBeanByFilter(Filter filter) {
		try {
			return Result.value(investmentProcessAttDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.InvestmentProcessAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<InvestmentProcessAtt>> getList() {
		try {
			return Result.value(investmentProcessAttDao.getList());
		} catch (Exception e) {
			return Result.failure(R.InvestmentProcessAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<InvestmentProcessAtt>> getListByFilter(Filter filter) {
		try {
			return Result.value(investmentProcessAttDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.InvestmentProcessAtt.LOAD_FAILURE);
		}
	}

}
