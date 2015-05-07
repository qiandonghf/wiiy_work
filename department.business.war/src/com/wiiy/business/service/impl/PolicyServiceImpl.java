package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.business.dao.PolicyDao;
import com.wiiy.business.entity.Policy;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.PolicyService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.business.activator.BusinessActivator;

/**
 * @author my
 */
public class PolicyServiceImpl implements PolicyService{
	
	private PolicyDao policyDao;
	
	public void setPolicyDao(PolicyDao policyDao) {
		this.policyDao = policyDao;
	}

	@Override
	public Result<Policy> save(Policy t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			policyDao.save(t);
			return Result.success(R.Policy.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Policy.class)));
		} catch (Exception e) {
			return Result.failure(R.Policy.SAVE_FAILURE);
		}
	}

	@Override
	public Result<Policy> delete(Policy t) {
		try {
			policyDao.delete(t);
			return Result.success(R.Policy.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.Policy.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Policy> deleteById(Serializable id) {
		try {
			policyDao.deleteById(id);
			return Result.success(R.Policy.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.Policy.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Policy> deleteByIds(String ids) {
		try {
			policyDao.deleteByIds(ids);
			return Result.success(R.Policy.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.Policy.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Policy> update(Policy t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			policyDao.update(t);
			return Result.success(R.Policy.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Policy.class)));
		} catch (Exception e) {
			return Result.failure(R.Policy.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<Policy> getBeanById(Serializable id) {
		try {
			return Result.value(policyDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.Policy.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Policy> getBeanByFilter(Filter filter) {
		try {
			return Result.value(policyDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Policy.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Policy>> getList() {
		try {
			return Result.value(policyDao.getList());
		} catch (Exception e) {
			return Result.failure(R.Policy.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Policy>> getListByFilter(Filter filter) {
		try {
			return Result.value(policyDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Policy.LOAD_FAILURE);
		}
	}

}
