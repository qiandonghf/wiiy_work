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
import com.wiiy.business.entity.Rate;
import com.wiiy.business.dao.RateDao;
import com.wiiy.business.service.RateService;
import com.wiiy.business.preferences.R;
import com.wiiy.business.activator.BusinessActivator;

/**
 * @author my
 */
public class RateServiceImpl implements RateService{
	
	private RateDao rateDao;
	
	public void setRateDao(RateDao rateDao) {
		this.rateDao = rateDao;
	}

	@Override
	public Result<Rate> save(Rate t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			rateDao.save(t);
			return Result.success(R.Rate.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Rate.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Rate.SAVE_FAILURE);
		}
	}

	@Override
	public Result<Rate> delete(Rate t) {
		try {
			rateDao.delete(t);
			return Result.success(R.Rate.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Rate.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Rate> deleteById(Serializable id) {
		try {
			rateDao.deleteById(id);
			return Result.success(R.Rate.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Rate.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Rate> deleteByIds(String ids) {
		try {
			rateDao.deleteByIds(ids);
			return Result.success(R.Rate.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Rate.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Rate> update(Rate t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			rateDao.update(t);
			return Result.success(R.Rate.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Rate.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Rate.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<Rate> getBeanById(Serializable id) {
		try {
			return Result.value(rateDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Rate.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Rate> getBeanByFilter(Filter filter) {
		try {
			return Result.value(rateDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Rate.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Rate>> getList() {
		try {
			return Result.value(rateDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Rate.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Rate>> getListByFilter(Filter filter) {
		try {
			return Result.value(rateDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Rate.LOAD_FAILURE);
		}
	}

}
