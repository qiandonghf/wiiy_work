package com.wiiy.estate.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.estate.entity.FixFee;
import com.wiiy.estate.dao.FixFeeDao;
import com.wiiy.estate.service.FixFeeService;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.activator.EstateActivator;

/**
 * @author my
 */
public class FixFeeServiceImpl implements FixFeeService{
	
	private FixFeeDao fixFeeDao;
	
	public void setFixFeeDao(FixFeeDao fixFeeDao) {
		this.fixFeeDao = fixFeeDao;
	}

	@Override
	public Result<FixFee> save(FixFee t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			fixFeeDao.save(t);
			return Result.success(R.FixFee.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),FixFee.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.FixFee.SAVE_FAILURE);
		}
	}

	@Override
	public Result<FixFee> delete(FixFee t) {
		try {
			fixFeeDao.delete(t);
			return Result.success(R.FixFee.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.FixFee.DELETE_FAILURE);
		}
	}

	@Override
	public Result<FixFee> deleteById(Serializable id) {
		try {
			fixFeeDao.deleteById(id);
			return Result.success(R.FixFee.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.FixFee.DELETE_FAILURE);
		}
	}

	@Override
	public Result<FixFee> deleteByIds(String ids) {
		try {
			fixFeeDao.deleteByIds(ids);
			return Result.success(R.FixFee.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.FixFee.DELETE_FAILURE);
		}
	}

	@Override
	public Result<FixFee> update(FixFee t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			fixFeeDao.update(t);
			return Result.success(R.FixFee.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),FixFee.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.FixFee.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<FixFee> getBeanById(Serializable id) {
		try {
			return Result.value(fixFeeDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.FixFee.LOAD_FAILURE);
		}
	}

	@Override
	public Result<FixFee> getBeanByFilter(Filter filter) {
		try {
			return Result.value(fixFeeDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.FixFee.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<FixFee>> getList() {
		try {
			return Result.value(fixFeeDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.FixFee.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<FixFee>> getListByFilter(Filter filter) {
		try {
			return Result.value(fixFeeDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.FixFee.LOAD_FAILURE);
		}
	}

}
