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
import com.wiiy.sale.entity.SaleCustomerCategory;
import com.wiiy.sale.dao.SaleCustomerCategoryDao;
import com.wiiy.sale.service.SaleCustomerCategoryService;
import com.wiiy.sale.preferences.R;
import com.wiiy.sale.activator.SaleActivator;

/**
 * @author my
 */
public class SaleCustomerCategoryServiceImpl implements SaleCustomerCategoryService{
	
	private SaleCustomerCategoryDao customerCategoryDao;
	
	public void setCustomerCategoryDao(SaleCustomerCategoryDao customerCategoryDao) {
		this.customerCategoryDao = customerCategoryDao;
	}

	@Override
	public Result<SaleCustomerCategory> save(SaleCustomerCategory t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SaleActivator.getSessionUser().getRealName());
			t.setCreatorId(SaleActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			customerCategoryDao.save(t);
			return Result.success(R.SaleCustomerCategory.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleCustomerCategory.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerCategory.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomerCategory> delete(SaleCustomerCategory t) {
		try {
			customerCategoryDao.delete(t);
			return Result.success(R.SaleCustomerCategory.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerCategory.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomerCategory> deleteById(Serializable id) {
		try {
			customerCategoryDao.deleteById(id);
			return Result.success(R.SaleCustomerCategory.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerCategory.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomerCategory> deleteByIds(String ids) {
		try {
			customerCategoryDao.deleteByIds(ids);
			return Result.success(R.SaleCustomerCategory.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerCategory.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomerCategory> update(SaleCustomerCategory t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SaleActivator.getSessionUser().getRealName());
			t.setModifierId(SaleActivator.getSessionUser().getId());
			customerCategoryDao.update(t);
			return Result.success(R.SaleCustomerCategory.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleCustomerCategory.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerCategory.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomerCategory> getBeanById(Serializable id) {
		try {
			return Result.value(customerCategoryDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerCategory.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomerCategory> getBeanByFilter(Filter filter) {
		try {
			return Result.value(customerCategoryDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerCategory.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleCustomerCategory>> getList() {
		try {
			return Result.value(customerCategoryDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerCategory.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleCustomerCategory>> getListByFilter(Filter filter) {
		try {
			return Result.value(customerCategoryDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerCategory.LOAD_FAILURE);
		}
	}

}
