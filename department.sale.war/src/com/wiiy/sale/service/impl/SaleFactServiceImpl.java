package com.wiiy.sale.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.sale.activator.SaleActivator;
import com.wiiy.sale.dao.SaleFactDao;
import com.wiiy.sale.entity.SaleFact;
import com.wiiy.sale.preferences.R;
import com.wiiy.sale.service.SaleFactService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class SaleFactServiceImpl implements SaleFactService{
	
	private SaleFactDao saleFactDao;
	
	public void setSaleFactDao(SaleFactDao saleFactDao) {
		this.saleFactDao = saleFactDao;
	}

	@Override
	public Result<SaleFact> save(SaleFact t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SaleActivator.getSessionUser().getRealName());
			t.setCreatorId(SaleActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			saleFactDao.save(t);
			return Result.success(R.SaleFact.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleFact.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleFact.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SaleFact> delete(SaleFact t) {
		try {
			saleFactDao.delete(t);
			return Result.success(R.SaleFact.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleFact.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleFact> deleteById(Serializable id) {
		try {
			saleFactDao.deleteById(id);
			return Result.success(R.SaleFact.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleFact.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleFact> deleteByIds(String ids) {
		try {
			saleFactDao.deleteByIds(ids);
			return Result.success(R.SaleFact.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleFact.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleFact> update(SaleFact t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SaleActivator.getSessionUser().getRealName());
			t.setModifierId(SaleActivator.getSessionUser().getId());
			saleFactDao.update(t);
			return Result.success(R.SaleFact.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleFact.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleFact.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SaleFact> getBeanById(Serializable id) {
		try {
			return Result.value(saleFactDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleFact.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SaleFact> getBeanByFilter(Filter filter) {
		try {
			return Result.value(saleFactDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleFact.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleFact>> getList() {
		try {
			return Result.value(saleFactDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleFact.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleFact>> getListByFilter(Filter filter) {
		try {
			return Result.value(saleFactDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleFact.LOAD_FAILURE);
		}
	}

}
