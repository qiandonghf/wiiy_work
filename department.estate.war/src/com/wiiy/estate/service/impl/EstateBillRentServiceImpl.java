package com.wiiy.estate.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dao.EstateBillRentDao;
import com.wiiy.estate.entity.EstateBillRent;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.service.EstateBillRentService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public class EstateBillRentServiceImpl implements EstateBillRentService{
	
	private EstateBillRentDao estateBillRentDao;
	
	public void setEstateBillRentDao(EstateBillRentDao estateBillRentDao) {
		this.estateBillRentDao = estateBillRentDao;
	}

	@Override
	public Result<EstateBillRent> save(EstateBillRent t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			estateBillRentDao.save(t);
			return Result.success(R.EstateBillRent.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateBillRent.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillRent.SAVE_FAILURE);
		}
	}

	@Override
	public Result<EstateBillRent> delete(EstateBillRent t) {
		try {
			estateBillRentDao.delete(t);
			return Result.success(R.EstateBillRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateBillRent> deleteById(Serializable id) {
		try {
			estateBillRentDao.deleteById(id);
			return Result.success(R.EstateBillRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateBillRent> deleteByIds(String ids) {
		try {
			estateBillRentDao.deleteByIds(ids);
			return Result.success(R.EstateBillRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateBillRent> update(EstateBillRent t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			estateBillRentDao.update(t);
			return Result.success(R.EstateBillRent.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateBillRent.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillRent.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<EstateBillRent> getBeanById(Serializable id) {
		try {
			return Result.value(estateBillRentDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EstateBillRent> getBeanByFilter(Filter filter) {
		try {
			return Result.value(estateBillRentDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateBillRent>> getList() {
		try {
			return Result.value(estateBillRentDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateBillRent>> getListByFilter(Filter filter) {
		try {
			return Result.value(estateBillRentDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List> getResultBySql(String sql) {
		Session session = null;
		try {
			session = estateBillRentDao.openSession();
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
