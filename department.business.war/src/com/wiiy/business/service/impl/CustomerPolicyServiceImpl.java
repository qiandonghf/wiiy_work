package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.CustomerPolicyDao;
import com.wiiy.business.entity.CustomerPolicy;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.CustomerPolicyService;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class CustomerPolicyServiceImpl implements CustomerPolicyService{
	
	private CustomerPolicyDao customerPolicyDao;
	
	public void setCustomerPolicyDao(CustomerPolicyDao customerPolicyDao) {
		this.customerPolicyDao = customerPolicyDao;
	}

	@Override
	public Result<CustomerPolicy> save(CustomerPolicy t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			customerPolicyDao.save(t);
			return Result.success(R.CustomerPolicy.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CustomerPolicy.class)));
		} catch (Exception e) {
			return Result.failure(R.CustomerPolicy.SAVE_FAILURE);
		}
	}

	@Override
	public Result<CustomerPolicy> delete(CustomerPolicy t) {
		try {
			customerPolicyDao.delete(t);
			return Result.success(R.CustomerPolicy.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.CustomerPolicy.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CustomerPolicy> deleteById(Serializable id) {
		try {
			customerPolicyDao.deleteById(id);
			return Result.success(R.CustomerPolicy.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.CustomerPolicy.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CustomerPolicy> deleteByIds(String ids) {
		try {
			customerPolicyDao.deleteByIds(ids);
			return Result.success(R.CustomerPolicy.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.CustomerPolicy.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CustomerPolicy> update(CustomerPolicy t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			customerPolicyDao.update(t);
			return Result.success(R.CustomerPolicy.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CustomerPolicy.class)));
		} catch (Exception e) {
			return Result.failure(R.CustomerPolicy.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<CustomerPolicy> getBeanById(Serializable id) {
		try {
			return Result.value(customerPolicyDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.CustomerPolicy.LOAD_FAILURE);
		}
	}

	@Override
	public Result<CustomerPolicy> getBeanByFilter(Filter filter) {
		try {
			return Result.value(customerPolicyDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.CustomerPolicy.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CustomerPolicy>> getList() {
		try {
			return Result.value(customerPolicyDao.getList());
		} catch (Exception e) {
			return Result.failure(R.CustomerPolicy.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CustomerPolicy>> getListByFilter(Filter filter) {
		try {
			return Result.value(customerPolicyDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.CustomerPolicy.LOAD_FAILURE);
		}
	}

}
