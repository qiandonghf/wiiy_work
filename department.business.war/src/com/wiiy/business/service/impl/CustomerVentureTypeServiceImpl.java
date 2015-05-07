package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.CustomerVentureTypeDao;
import com.wiiy.business.entity.CustomerVentureType;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.CustomerVentureTypeService;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class CustomerVentureTypeServiceImpl implements CustomerVentureTypeService{
	
	private CustomerVentureTypeDao customerVentureTypeDao;
	
	public void setCustomerVentureTypeDao(CustomerVentureTypeDao customerVentureTypeDao) {
		this.customerVentureTypeDao = customerVentureTypeDao;
	}

	@Override
	public Result<CustomerVentureType> save(CustomerVentureType t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			customerVentureTypeDao.save(t);
			return Result.success(R.InvestmentVentureType.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CustomerVentureType.class)));
		} catch (Exception e) {
			return Result.failure(R.InvestmentVentureType.SAVE_FAILURE);
		}
	}

	@Override
	public Result<CustomerVentureType> delete(CustomerVentureType t) {
		try {
			customerVentureTypeDao.delete(t);
			return Result.success(R.InvestmentVentureType.SAVE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.InvestmentVentureType.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CustomerVentureType> deleteById(Serializable id) {
		try {
			customerVentureTypeDao.deleteById(id);
			return Result.success(R.InvestmentVentureType.SAVE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.InvestmentVentureType.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CustomerVentureType> deleteByIds(String ids) {
		try {
			customerVentureTypeDao.deleteByIds(ids);
			return Result.success(R.InvestmentVentureType.SAVE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.InvestmentVentureType.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CustomerVentureType> update(CustomerVentureType t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			customerVentureTypeDao.update(t);
			return Result.success(R.InvestmentVentureType.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CustomerVentureType.class)));
		} catch (Exception e) {
			return Result.failure(R.InvestmentVentureType.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<CustomerVentureType> getBeanById(Serializable id) {
		try {
			return Result.value(customerVentureTypeDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.InvestmentVentureType.LOAD_FAILURE);
		}
	}

	@Override
	public Result<CustomerVentureType> getBeanByFilter(Filter filter) {
		try {
			return Result.value(customerVentureTypeDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.InvestmentVentureType.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CustomerVentureType>> getList() {
		try {
			return Result.value(customerVentureTypeDao.getList());
		} catch (Exception e) {
			return Result.failure(R.InvestmentVentureType.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CustomerVentureType>> getListByFilter(Filter filter) {
		try {
			return Result.value(customerVentureTypeDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.InvestmentVentureType.LOAD_FAILURE);
		}
	}

}
