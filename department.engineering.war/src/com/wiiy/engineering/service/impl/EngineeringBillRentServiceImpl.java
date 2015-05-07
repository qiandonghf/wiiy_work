package com.wiiy.engineering.service.impl;


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
import com.wiiy.engineering.entity.EngineeringBillRent;
import com.wiiy.engineering.dao.EngineeringBillRentDao;
import com.wiiy.engineering.service.EngineeringBillRentService;
import com.wiiy.engineering.preferences.R;
import com.wiiy.engineering.activator.EngineeringActivator;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public class EngineeringBillRentServiceImpl implements EngineeringBillRentService{
	
	private EngineeringBillRentDao engineeringBillRentDao;
	
	public void setEngineeringBillRentDao(EngineeringBillRentDao engineeringBillRentDao) {
		this.engineeringBillRentDao = engineeringBillRentDao;
	}

	@Override
	public Result<EngineeringBillRent> save(EngineeringBillRent t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EngineeringActivator.getSessionUser().getRealName());
			t.setCreatorId(EngineeringActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			engineeringBillRentDao.save(t);
			return Result.success(R.EngineeringBillRent.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EngineeringBillRent.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringBillRent.SAVE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringBillRent> delete(EngineeringBillRent t) {
		try {
			engineeringBillRentDao.delete(t);
			return Result.success(R.EngineeringBillRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringBillRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringBillRent> deleteById(Serializable id) {
		try {
			engineeringBillRentDao.deleteById(id);
			return Result.success(R.EngineeringBillRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringBillRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringBillRent> deleteByIds(String ids) {
		try {
			engineeringBillRentDao.deleteByIds(ids);
			return Result.success(R.EngineeringBillRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringBillRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringBillRent> update(EngineeringBillRent t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EngineeringActivator.getSessionUser().getRealName());
			t.setModifierId(EngineeringActivator.getSessionUser().getId());
			engineeringBillRentDao.update(t);
			return Result.success(R.EngineeringBillRent.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EngineeringBillRent.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringBillRent.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringBillRent> getBeanById(Serializable id) {
		try {
			return Result.value(engineeringBillRentDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringBillRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EngineeringBillRent> getBeanByFilter(Filter filter) {
		try {
			return Result.value(engineeringBillRentDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringBillRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EngineeringBillRent>> getList() {
		try {
			return Result.value(engineeringBillRentDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringBillRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EngineeringBillRent>> getListByFilter(Filter filter) {
		try {
			return Result.value(engineeringBillRentDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringBillRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List> getResultBySql(String sql) {
		Session session = null;
		try {
			session = engineeringBillRentDao.openSession();
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
