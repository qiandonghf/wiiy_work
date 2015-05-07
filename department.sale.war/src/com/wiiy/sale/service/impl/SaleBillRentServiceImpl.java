package com.wiiy.sale.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.sale.entity.SaleBillRent;
import com.wiiy.sale.dao.SaleBillRentDao;
import com.wiiy.sale.service.SaleBillRentService;
import com.wiiy.sale.preferences.R;
import com.wiiy.sale.activator.SaleActivator;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public class SaleBillRentServiceImpl implements SaleBillRentService{
	
	private SaleBillRentDao saleBillRentDao;
	
	public void setSaleBillRentDao(SaleBillRentDao saleBillRentDao) {
		this.saleBillRentDao = saleBillRentDao;
	}

	@Override
	public Result<SaleBillRent> save(SaleBillRent t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SaleActivator.getSessionUser().getRealName());
			t.setCreatorId(SaleActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			saleBillRentDao.save(t);
			return Result.success(R.SaleBillRent.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleBillRent.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleBillRent.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SaleBillRent> delete(SaleBillRent t) {
		try {
			saleBillRentDao.delete(t);
			return Result.success(R.SaleBillRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleBillRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleBillRent> deleteById(Serializable id) {
		try {
			saleBillRentDao.deleteById(id);
			return Result.success(R.SaleBillRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleBillRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleBillRent> deleteByIds(String ids) {
		try {
			saleBillRentDao.deleteByIds(ids);
			return Result.success(R.SaleBillRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleBillRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleBillRent> update(SaleBillRent t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SaleActivator.getSessionUser().getRealName());
			t.setModifierId(SaleActivator.getSessionUser().getId());
			saleBillRentDao.update(t);
			return Result.success(R.SaleBillRent.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleBillRent.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleBillRent.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SaleBillRent> getBeanById(Serializable id) {
		try {
			return Result.value(saleBillRentDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleBillRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SaleBillRent> getBeanByFilter(Filter filter) {
		try {
			return Result.value(saleBillRentDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleBillRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleBillRent>> getList() {
		try {
			return Result.value(saleBillRentDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleBillRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleBillRent>> getListByFilter(Filter filter) {
		try {
			return Result.value(saleBillRentDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleBillRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List> getResultBySql(String sql) {
		Session session = null;
		try {
			session = saleBillRentDao.openSession();
			List list = session.createSQLQuery(sql).list();
			return Result.value(list);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (session != null) {
				session.close();
			}
		}
		return null;
	}

}
