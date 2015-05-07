package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.BusinessContractAttDao;
import com.wiiy.business.entity.BusinessContractAtt;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.ContractAttService;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class ContractAttServiceImpl implements ContractAttService{
	
	private BusinessContractAttDao contractAttDao;
	
	public void setContractAttDao(BusinessContractAttDao contractAttDao) {
		this.contractAttDao = contractAttDao;
	}

	@Override
	public Result<BusinessContractAtt> save(BusinessContractAtt t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			contractAttDao.save(t);
			return Result.success(R.BusinessContractAtt.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),BusinessContractAtt.class)));
		} catch (Exception e) {
			return Result.failure(R.BusinessContractAtt.SAVE_FAILURE);
		}
	}

	@Override
	public Result<BusinessContractAtt> delete(BusinessContractAtt t) {
		try {
			contractAttDao.delete(t);
			return Result.success(R.BusinessContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.BusinessContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<BusinessContractAtt> deleteById(Serializable id) {
		try {
			contractAttDao.deleteById(id);
			return Result.success(R.BusinessContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.BusinessContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<BusinessContractAtt> deleteByIds(String ids) {
		try {
			contractAttDao.deleteByIds(ids);
			return Result.success(R.BusinessContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.BusinessContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<BusinessContractAtt> update(BusinessContractAtt t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			contractAttDao.update(t);
			return Result.success(R.BusinessContractAtt.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),BusinessContractAtt.class)));
		} catch (Exception e) {
			return Result.failure(R.BusinessContractAtt.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<BusinessContractAtt> getBeanById(Serializable id) {
		try {
			return Result.value(contractAttDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.BusinessContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<BusinessContractAtt> getBeanByFilter(Filter filter) {
		try {
			return Result.value(contractAttDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.BusinessContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<BusinessContractAtt>> getList() {
		try {
			return Result.value(contractAttDao.getList());
		} catch (Exception e) {
			return Result.failure(R.BusinessContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<BusinessContractAtt>> getListByFilter(Filter filter) {
		try {
			return Result.value(contractAttDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.BusinessContractAtt.LOAD_FAILURE);
		}
	}

}
