package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.ContractMemoDao;
import com.wiiy.business.entity.ContractMemo;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.ContractMemoService;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class ContractMemoServiceImpl implements ContractMemoService{
	
	private ContractMemoDao contractMemoDao;
	
	public void setContractMemoDao(ContractMemoDao contractMemoDao) {
		this.contractMemoDao = contractMemoDao;
	}

	@Override
	public Result<ContractMemo> save(ContractMemo t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			contractMemoDao.save(t);
			return Result.success(R.ContractMemo.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ContractMemo.class)));
		} catch (Exception e) {
			return Result.failure(R.ContractMemo.SAVE_FAILURE);
		}
	}

	@Override
	public Result<ContractMemo> delete(ContractMemo t) {
		try {
			contractMemoDao.delete(t);
			return Result.success(R.ContractMemo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.ContractMemo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContractMemo> deleteById(Serializable id) {
		try {
			contractMemoDao.deleteById(id);
			return Result.success(R.ContractMemo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.ContractMemo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContractMemo> deleteByIds(String ids) {
		try {
			contractMemoDao.deleteByIds(ids);
			return Result.success(R.ContractMemo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.ContractMemo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContractMemo> update(ContractMemo t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			contractMemoDao.update(t);
			return Result.success(R.ContractMemo.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ContractMemo.class)));
		} catch (Exception e) {
			return Result.failure(R.ContractMemo.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<ContractMemo> getBeanById(Serializable id) {
		try {
			return Result.value(contractMemoDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.ContractMemo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<ContractMemo> getBeanByFilter(Filter filter) {
		try {
			return Result.value(contractMemoDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.ContractMemo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ContractMemo>> getList() {
		try {
			return Result.value(contractMemoDao.getList());
		} catch (Exception e) {
			return Result.failure(R.ContractMemo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ContractMemo>> getListByFilter(Filter filter) {
		try {
			return Result.value(contractMemoDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.ContractMemo.LOAD_FAILURE);
		}
	}

}
