package com.wiiy.estate.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dao.ContractModifyLogDao;
import com.wiiy.estate.entity.EstateContractModifyLog;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.service.ContractModifyLogService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class ContractModifyLogServiceImpl implements ContractModifyLogService{
	
	private ContractModifyLogDao contractModifyLogDao;
	
	public void setContractModifyLogDao(ContractModifyLogDao contractModifyLogDao) {
		this.contractModifyLogDao = contractModifyLogDao;
	}

	@Override
	public Result<EstateContractModifyLog> save(EstateContractModifyLog t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			contractModifyLogDao.save(t);
			return Result.success(R.EstateContractModifyLog.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateContractModifyLog.class)));
		} catch (Exception e) {
			return Result.failure(R.EstateContractModifyLog.SAVE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractModifyLog> delete(EstateContractModifyLog t) {
		try {
			contractModifyLogDao.delete(t);
			return Result.success(R.EstateContractModifyLog.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateContractModifyLog.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractModifyLog> deleteById(Serializable id) {
		try {
			contractModifyLogDao.deleteById(id);
			return Result.success(R.EstateContractModifyLog.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateContractModifyLog.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractModifyLog> deleteByIds(String ids) {
		try {
			contractModifyLogDao.deleteByIds(ids);
			return Result.success(R.EstateContractModifyLog.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateContractModifyLog.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractModifyLog> update(EstateContractModifyLog t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			contractModifyLogDao.update(t);
			return Result.success(R.EstateContractModifyLog.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateContractModifyLog.class)));
		} catch (Exception e) {
			return Result.failure(R.EstateContractModifyLog.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractModifyLog> getBeanById(Serializable id) {
		try {
			return Result.value(contractModifyLogDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.EstateContractModifyLog.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EstateContractModifyLog> getBeanByFilter(Filter filter) {
		try {
			return Result.value(contractModifyLogDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.EstateContractModifyLog.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateContractModifyLog>> getList() {
		try {
			return Result.value(contractModifyLogDao.getList());
		} catch (Exception e) {
			return Result.failure(R.EstateContractModifyLog.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateContractModifyLog>> getListByFilter(Filter filter) {
		try {
			return Result.value(contractModifyLogDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.EstateContractModifyLog.LOAD_FAILURE);
		}
	}

}
