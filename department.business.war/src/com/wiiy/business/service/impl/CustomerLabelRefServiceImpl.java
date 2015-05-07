package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.CustomerLabelRefDao;
import com.wiiy.business.entity.CustomerLabelRef;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.CustomerLabelRefService;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class CustomerLabelRefServiceImpl implements CustomerLabelRefService{
	
	private CustomerLabelRefDao customerLabelRefDao;
	
	public void setCustomerLabelRefDao(CustomerLabelRefDao customerLabelRefDao) {
		this.customerLabelRefDao = customerLabelRefDao;
	}

	@Override
	public Result<CustomerLabelRef> save(CustomerLabelRef t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			customerLabelRefDao.save(t);
			return Result.success(R.CustomerLabelRef.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CustomerLabelRef.class)));
		} catch (Exception e) {
			return Result.failure(R.CustomerLabelRef.SAVE_FAILURE);
		}
	}

	@Override
	public Result<CustomerLabelRef> delete(CustomerLabelRef t) {
		try {
			customerLabelRefDao.delete(t);
			return Result.success(R.CustomerLabelRef.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.CustomerLabelRef.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CustomerLabelRef> deleteById(Serializable id) {
		try {
			customerLabelRefDao.deleteById(id);
			return Result.success(R.CustomerLabelRef.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.CustomerLabelRef.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CustomerLabelRef> deleteByIds(String ids) {
		try {
			customerLabelRefDao.deleteByIds(ids);
			return Result.success(R.CustomerLabelRef.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.CustomerLabelRef.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CustomerLabelRef> update(CustomerLabelRef t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			customerLabelRefDao.update(t);
			return Result.success(R.CustomerLabelRef.UPDATE_SUCCESS,t);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CustomerLabelRef.class)));
		} catch (Exception e) {
			return Result.failure(R.CustomerLabelRef.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<CustomerLabelRef> getBeanById(Serializable id) {
		try {
			return Result.value(customerLabelRefDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.CustomerLabelRef.LOAD_FAILURE);
		}
	}

	@Override
	public Result<CustomerLabelRef> getBeanByFilter(Filter filter) {
		try {
			return Result.value(customerLabelRefDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.CustomerLabelRef.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CustomerLabelRef>> getList() {
		try {
			return Result.value(customerLabelRefDao.getList());
		} catch (Exception e) {
			return Result.failure(R.CustomerLabelRef.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CustomerLabelRef>> getListByFilter(Filter filter) {
		try {
			return Result.value(customerLabelRefDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.CustomerLabelRef.LOAD_FAILURE);
		}
	}

}
