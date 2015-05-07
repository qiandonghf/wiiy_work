package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.ContractModifyLogDao;
import com.wiiy.business.entity.ContractModifyLog;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.ContractModifyLogService;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
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
	public Result<ContractModifyLog> save(ContractModifyLog t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			contractModifyLogDao.save(t);
			return Result.success(R.ContractModifyLog.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ContractModifyLog.class)));
		} catch (Exception e) {
			return Result.failure(R.ContractModifyLog.SAVE_FAILURE);
		}
	}

	@Override
	public Result<ContractModifyLog> delete(ContractModifyLog t) {
		try {
			contractModifyLogDao.delete(t);
			return Result.success(R.ContractModifyLog.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.ContractModifyLog.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContractModifyLog> deleteById(Serializable id) {
		try {
			contractModifyLogDao.deleteById(id);
			return Result.success(R.ContractModifyLog.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.ContractModifyLog.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContractModifyLog> deleteByIds(String ids) {
		try {
			contractModifyLogDao.deleteByIds(ids);
			return Result.success(R.ContractModifyLog.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.ContractModifyLog.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContractModifyLog> update(ContractModifyLog t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			contractModifyLogDao.update(t);
			return Result.success(R.ContractModifyLog.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ContractModifyLog.class)));
		} catch (Exception e) {
			return Result.failure(R.ContractModifyLog.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<ContractModifyLog> getBeanById(Serializable id) {
		try {
			return Result.value(contractModifyLogDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.ContractModifyLog.LOAD_FAILURE);
		}
	}

	@Override
	public Result<ContractModifyLog> getBeanByFilter(Filter filter) {
		try {
			return Result.value(contractModifyLogDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.ContractModifyLog.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ContractModifyLog>> getList() {
		try {
			return Result.value(contractModifyLogDao.getList());
		} catch (Exception e) {
			return Result.failure(R.ContractModifyLog.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ContractModifyLog>> getListByFilter(Filter filter) {
		try {
			return Result.value(contractModifyLogDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.ContractModifyLog.LOAD_FAILURE);
		}
	}

}
