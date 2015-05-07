package com.wiiy.engineering.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.engineering.activator.EngineeringActivator;
import com.wiiy.engineering.dao.EngineeringFactDao;
import com.wiiy.engineering.entity.EngineeringFact;
import com.wiiy.engineering.preferences.R;
import com.wiiy.engineering.service.EngineeringFactService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class EngineeringFactServiceImpl implements EngineeringFactService{
	
	private EngineeringFactDao engineeringFactDao;
	
	public void setEngineeringFactDao(EngineeringFactDao engineeringFactDao) {
		this.engineeringFactDao = engineeringFactDao;
	}

	@Override
	public Result<EngineeringFact> save(EngineeringFact t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EngineeringActivator.getSessionUser().getRealName());
			t.setCreatorId(EngineeringActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			engineeringFactDao.save(t);
			return Result.success(R.EngineeringFact.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EngineeringFact.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringFact.SAVE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringFact> delete(EngineeringFact t) {
		try {
			engineeringFactDao.delete(t);
			return Result.success(R.EngineeringFact.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringFact.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringFact> deleteById(Serializable id) {
		try {
			engineeringFactDao.deleteById(id);
			return Result.success(R.EngineeringFact.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringFact.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringFact> deleteByIds(String ids) {
		try {
			engineeringFactDao.deleteByIds(ids);
			return Result.success(R.EngineeringFact.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringFact.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringFact> update(EngineeringFact t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EngineeringActivator.getSessionUser().getRealName());
			t.setModifierId(EngineeringActivator.getSessionUser().getId());
			engineeringFactDao.update(t);
			return Result.success(R.EngineeringFact.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EngineeringFact.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringFact.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringFact> getBeanById(Serializable id) {
		try {
			return Result.value(engineeringFactDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringFact.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EngineeringFact> getBeanByFilter(Filter filter) {
		try {
			return Result.value(engineeringFactDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringFact.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EngineeringFact>> getList() {
		try {
			return Result.value(engineeringFactDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringFact.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EngineeringFact>> getListByFilter(Filter filter) {
		try {
			return Result.value(engineeringFactDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringFact.LOAD_FAILURE);
		}
	}

}
