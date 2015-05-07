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
import com.wiiy.sale.entity.SaleCustomerLabel;
import com.wiiy.sale.dao.SaleCustomerLabelDao;
import com.wiiy.sale.service.SaleCustomerLabelService;
import com.wiiy.sale.preferences.R;
import com.wiiy.sale.activator.SaleActivator;

/**
 * @author my
 */
public class SaleCustomerLabelServiceImpl implements SaleCustomerLabelService{
	
	private SaleCustomerLabelDao customerLabelDao;
	
	public void setCustomerLabelDao(SaleCustomerLabelDao customerLabelDao) {
		this.customerLabelDao = customerLabelDao;
	}

	@Override
	public Result<SaleCustomerLabel> save(SaleCustomerLabel t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SaleActivator.getSessionUser().getRealName());
			t.setCreatorId(SaleActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			customerLabelDao.save(t);
			return Result.success(R.SaleCustomerLabel.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleCustomerLabel.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerLabel.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomerLabel> delete(SaleCustomerLabel t) {
		try {
			customerLabelDao.delete(t);
			return Result.success(R.SaleCustomerLabel.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerLabel.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomerLabel> deleteById(Serializable id) {
		try {
			customerLabelDao.deleteById(id);
			return Result.success(R.SaleCustomerLabel.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerLabel.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomerLabel> deleteByIds(String ids) {
		try {
			customerLabelDao.deleteByIds(ids);
			return Result.success(R.SaleCustomerLabel.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerLabel.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomerLabel> update(SaleCustomerLabel t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SaleActivator.getSessionUser().getRealName());
			t.setModifierId(SaleActivator.getSessionUser().getId());
			customerLabelDao.update(t);
			return Result.success(R.SaleCustomerLabel.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleCustomerLabel.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerLabel.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomerLabel> getBeanById(Serializable id) {
		try {
			return Result.value(customerLabelDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerLabel.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomerLabel> getBeanByFilter(Filter filter) {
		try {
			return Result.value(customerLabelDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerLabel.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleCustomerLabel>> getList() {
		try {
			return Result.value(customerLabelDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerLabel.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleCustomerLabel>> getListByFilter(Filter filter) {
		try {
			return Result.value(customerLabelDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomerLabel.LOAD_FAILURE);
		}
	}

}
