package com.wiiy.pf.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.pf.activator.PfActivator;
import com.wiiy.pf.dao.ProcessTypeDao;
import com.wiiy.pf.entity.ProcessType;
import com.wiiy.pf.preferences.R;
import com.wiiy.pf.service.ProcessTypeService;

/**
 * @author my
 */
public class ProcessTypeServiceImpl implements ProcessTypeService{
	
	private ProcessTypeDao processTypeDao;
	
	public void setProcessTypeDao(ProcessTypeDao processTypeDao) {
		this.processTypeDao = processTypeDao;
	}

	@Override
	public Result<ProcessType> save(ProcessType t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(PfActivator.getSessionUser().getRealName());
			t.setCreatorId(PfActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			processTypeDao.save(t);
			return Result.success(R.ProcessType.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ProcessType.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ProcessType.SAVE_FAILURE);
		}
	}

	@Override
	public Result<ProcessType> delete(ProcessType t) {
		try {
			processTypeDao.delete(t);
			return Result.success(R.ProcessType.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ProcessType.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ProcessType> deleteById(Serializable id) {
		try {
			processTypeDao.deleteById(id);
			return Result.success(R.ProcessType.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ProcessType.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ProcessType> deleteByIds(String ids) {
		try {
			processTypeDao.deleteByIds(ids);
			return Result.success(R.ProcessType.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ProcessType.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ProcessType> update(ProcessType t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(PfActivator.getSessionUser().getRealName());
			t.setModifierId(PfActivator.getSessionUser().getId());
			processTypeDao.update(t);
			return Result.success(R.ProcessType.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ProcessType.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ProcessType.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<ProcessType> getBeanById(Serializable id) {
		try {
			return Result.value(processTypeDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ProcessType.LOAD_FAILURE);
		}
	}

	@Override
	public Result<ProcessType> getBeanByFilter(Filter filter) {
		try {
			return Result.value(processTypeDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ProcessType.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ProcessType>> getList() {
		try {
			return Result.value(processTypeDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ProcessType.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ProcessType>> getListByFilter(Filter filter) {
		try {
			return Result.value(processTypeDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ProcessType.LOAD_FAILURE);
		}
	}

}
