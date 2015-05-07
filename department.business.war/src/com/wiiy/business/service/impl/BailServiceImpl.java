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
import com.wiiy.business.entity.Bail;
import com.wiiy.business.dao.BailDao;
import com.wiiy.business.service.BailService;
import com.wiiy.business.preferences.R;
import com.wiiy.business.activator.BusinessActivator;

/**
 * @author my
 */
public class BailServiceImpl implements BailService{
	
	private BailDao bailDao;
	
	public void setBailDao(BailDao bailDao) {
		this.bailDao = bailDao;
	}

	@Override
	public Result<Bail> save(Bail t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			bailDao.save(t);
			return Result.success(R.Bail.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Bail.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bail.SAVE_FAILURE);
		}
	}

	@Override
	public Result<Bail> delete(Bail t) {
		try {
			bailDao.delete(t);
			return Result.success(R.Bail.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bail.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Bail> deleteById(Serializable id) {
		try {
			bailDao.deleteById(id);
			return Result.success(R.Bail.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bail.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Bail> deleteByIds(String ids) {
		try {
			bailDao.deleteByIds(ids);
			return Result.success(R.Bail.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bail.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Bail> update(Bail t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			bailDao.update(t);
			return Result.success(R.Bail.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Bail.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bail.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<Bail> getBeanById(Serializable id) {
		try {
			return Result.value(bailDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bail.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Bail> getBeanByFilter(Filter filter) {
		try {
			return Result.value(bailDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bail.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Bail>> getList() {
		try {
			return Result.value(bailDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bail.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Bail>> getListByFilter(Filter filter) {
		try {
			return Result.value(bailDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bail.LOAD_FAILURE);
		}
	}

}
