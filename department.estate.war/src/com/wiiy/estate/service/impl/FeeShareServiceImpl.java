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
import com.wiiy.estate.entity.FeeShare;
import com.wiiy.estate.dao.FeeShareDao;
import com.wiiy.estate.service.FeeShareService;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.activator.EstateActivator;

/**
 * @author my
 */
public class FeeShareServiceImpl implements FeeShareService{
	
	private FeeShareDao feeShareDao;
	
	public void setFeeShareDao(FeeShareDao feeShareDao) {
		this.feeShareDao = feeShareDao;
	}

	@Override
	public Result<FeeShare> save(FeeShare t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			feeShareDao.save(t);
			return Result.success(R.FeeShare.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),FeeShare.class)));
		} catch (Exception e) {
			return Result.failure(R.FeeShare.SAVE_FAILURE);
		}
	}

	@Override
	public Result<FeeShare> delete(FeeShare t) {
		try {
			feeShareDao.delete(t);
			return Result.success(R.FeeShare.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.FeeShare.DELETE_FAILURE);
		}
	}

	@Override
	public Result<FeeShare> deleteById(Serializable id) {
		try {
			feeShareDao.deleteById(id);
			return Result.success(R.FeeShare.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.FeeShare.DELETE_FAILURE);
		}
	}

	@Override
	public Result<FeeShare> deleteByIds(String ids) {
		try {
			feeShareDao.deleteByIds(ids);
			return Result.success(R.FeeShare.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.FeeShare.DELETE_FAILURE);
		}
	}

	@Override
	public Result<FeeShare> update(FeeShare t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			feeShareDao.update(t);
			return Result.success(R.FeeShare.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),FeeShare.class)));
		} catch (Exception e) {
			return Result.failure(R.FeeShare.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<FeeShare> getBeanById(Serializable id) {
		try {
			return Result.value(feeShareDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.FeeShare.LOAD_FAILURE);
		}
	}

	@Override
	public Result<FeeShare> getBeanByFilter(Filter filter) {
		try {
			return Result.value(feeShareDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.FeeShare.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<FeeShare>> getList() {
		try {
			return Result.value(feeShareDao.getList());
		} catch (Exception e) {
			return Result.failure(R.FeeShare.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<FeeShare>> getListByFilter(Filter filter) {
		try {
			return Result.value(feeShareDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.FeeShare.LOAD_FAILURE);
		}
	}

}
