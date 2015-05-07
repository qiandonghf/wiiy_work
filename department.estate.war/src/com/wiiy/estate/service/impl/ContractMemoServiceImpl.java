package com.wiiy.estate.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dao.ContractMemoDao;
import com.wiiy.estate.entity.EstateContractMemo;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.service.ContractMemoService;
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
	public Result<EstateContractMemo> save(EstateContractMemo t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			contractMemoDao.save(t);
			return Result.success(R.EstateContractMemo.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateContractMemo.class)));
		} catch (Exception e) {
			return Result.failure(R.EstateContractMemo.SAVE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractMemo> delete(EstateContractMemo t) {
		try {
			contractMemoDao.delete(t);
			return Result.success(R.EstateContractMemo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateContractMemo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractMemo> deleteById(Serializable id) {
		try {
			contractMemoDao.deleteById(id);
			return Result.success(R.EstateContractMemo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateContractMemo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractMemo> deleteByIds(String ids) {
		try {
			contractMemoDao.deleteByIds(ids);
			return Result.success(R.EstateContractMemo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateContractMemo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractMemo> update(EstateContractMemo t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			contractMemoDao.update(t);
			return Result.success(R.EstateContractMemo.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateContractMemo.class)));
		} catch (Exception e) {
			return Result.failure(R.EstateContractMemo.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractMemo> getBeanById(Serializable id) {
		try {
			return Result.value(contractMemoDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.EstateContractMemo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EstateContractMemo> getBeanByFilter(Filter filter) {
		try {
			return Result.value(contractMemoDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.EstateContractMemo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateContractMemo>> getList() {
		try {
			return Result.value(contractMemoDao.getList());
		} catch (Exception e) {
			return Result.failure(R.EstateContractMemo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateContractMemo>> getListByFilter(Filter filter) {
		try {
			return Result.value(contractMemoDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.EstateContractMemo.LOAD_FAILURE);
		}
	}

}
