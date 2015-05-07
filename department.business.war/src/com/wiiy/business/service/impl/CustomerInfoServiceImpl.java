package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.CustomerInfoDao;
import com.wiiy.business.entity.CustomerInfo;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.CustomerInfoService;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class CustomerInfoServiceImpl implements CustomerInfoService{
	
	private CustomerInfoDao customerInfoDao;
	
	public void setCustomerInfoDao(CustomerInfoDao customerInfoDao) {
		this.customerInfoDao = customerInfoDao;
	}

	@Override
	public Result<CustomerInfo> save(CustomerInfo t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			customerInfoDao.save(t);
			return Result.success(R.CustomerInfo.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CustomerInfo.class)));
		} catch (Exception e) {
			return Result.failure(R.CustomerInfo.SAVE_FAILURE);
		}
	}

	@Override
	public Result<CustomerInfo> delete(CustomerInfo t) {
		try {
			customerInfoDao.delete(t);
			return Result.success(R.CustomerInfo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.CustomerInfo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CustomerInfo> deleteById(Serializable id) {
		try {
			customerInfoDao.deleteById(id);
			return Result.success(R.CustomerInfo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.CustomerInfo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CustomerInfo> deleteByIds(String ids) {
		try {
			customerInfoDao.deleteByIds(ids);
			return Result.success(R.CustomerInfo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.CustomerInfo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CustomerInfo> update(CustomerInfo t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			customerInfoDao.update(t);
			return Result.success(R.CustomerInfo.UPDATE_SUCCESS,t);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CustomerInfo.class)));
		} catch (Exception e) {
			return Result.failure(R.CustomerInfo.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<CustomerInfo> getBeanById(Serializable id) {
		try {
			return Result.value(customerInfoDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.CustomerInfo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<CustomerInfo> getBeanByFilter(Filter filter) {
		try {
			return Result.value(customerInfoDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.CustomerInfo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CustomerInfo>> getList() {
		try {
			return Result.value(customerInfoDao.getList());
		} catch (Exception e) {
			return Result.failure(R.CustomerInfo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CustomerInfo>> getListByFilter(Filter filter) {
		try {
			return Result.value(customerInfoDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.CustomerInfo.LOAD_FAILURE);
		}
	}
	@Override
	public List getListBySql(String sql) {
		return customerInfoDao.getObjectListBySql(sql);
	}

}
