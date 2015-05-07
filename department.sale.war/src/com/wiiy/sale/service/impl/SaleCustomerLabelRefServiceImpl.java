package com.wiiy.sale.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.sale.entity.SaleCustomerLabelRef;
import com.wiiy.sale.dao.SaleCustomerLabelRefDao;
import com.wiiy.sale.service.SaleCustomerLabelRefService;
import com.wiiy.sale.preferences.R;
import com.wiiy.sale.activator.SaleActivator;

/**
 * @author my
 */
public class SaleCustomerLabelRefServiceImpl implements SaleCustomerLabelRefService{
	
	private SaleCustomerLabelRefDao customerLabelRefDao;
	
	public void setCustomerLabelRefDao(SaleCustomerLabelRefDao customerLabelRefDao) {
		this.customerLabelRefDao = customerLabelRefDao;
	}

	@Override
	public Result<SaleCustomerLabelRef> save(SaleCustomerLabelRef t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SaleActivator.getSessionUser().getRealName());
			t.setCreatorId(SaleActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			customerLabelRefDao.save(t);
			return Result.success(R.SaleCustomerLabelRef.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleCustomerLabelRef.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerLabelRef.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomerLabelRef> delete(SaleCustomerLabelRef t) {
		try {
			customerLabelRefDao.delete(t);
			return Result.success(R.SaleCustomerLabelRef.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerLabelRef.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomerLabelRef> deleteById(Serializable id) {
		try {
			customerLabelRefDao.deleteById(id);
			return Result.success(R.SaleCustomerLabelRef.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerLabelRef.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomerLabelRef> deleteByIds(String ids) {
		try {
			customerLabelRefDao.deleteByIds(ids);
			return Result.success(R.SaleCustomerLabelRef.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerLabelRef.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomerLabelRef> update(SaleCustomerLabelRef t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SaleActivator.getSessionUser().getRealName());
			t.setModifierId(SaleActivator.getSessionUser().getId());
			customerLabelRefDao.update(t);
			return Result.success(R.SaleCustomerLabelRef.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleCustomerLabelRef.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerLabelRef.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomerLabelRef> getBeanById(Serializable id) {
		try {
			return Result.value(customerLabelRefDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerLabelRef.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomerLabelRef> getBeanByFilter(Filter filter) {
		try {
			return Result.value(customerLabelRefDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerLabelRef.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleCustomerLabelRef>> getList() {
		try {
			return Result.value(customerLabelRefDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerLabelRef.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleCustomerLabelRef>> getListByFilter(Filter filter) {
		try {
			return Result.value(customerLabelRefDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerLabelRef.LOAD_FAILURE);
		}
	}

}
