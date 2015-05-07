package com.wiiy.estate.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dao.SubjectRentDao;
import com.wiiy.estate.entity.EstateSubjectRent;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.service.SubjectRentService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class SubjectRentServiceImpl implements SubjectRentService{
	
	private SubjectRentDao subjectRentDao;
	
	public void setSubjectRentDao(SubjectRentDao subjectRentDao) {
		this.subjectRentDao = subjectRentDao;
	}

	@Override
	public Result<EstateSubjectRent> save(EstateSubjectRent t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = subjectRentDao.openSession();
			tr = session.beginTransaction();
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			session.save(t);
			tr.commit();
			return Result.success(R.EstateSubjectRent.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateSubjectRent.class)));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.EstateSubjectRent.SAVE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<EstateSubjectRent> delete(EstateSubjectRent t) {
		try {
			subjectRentDao.delete(t);
			return Result.success(R.EstateSubjectRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateSubjectRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateSubjectRent> deleteById(Serializable id) {
		try {
			subjectRentDao.deleteById(id);
			return Result.success(R.EstateSubjectRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateSubjectRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateSubjectRent> deleteByIds(String ids) {
		try {
			subjectRentDao.deleteByIds(ids);
			return Result.success(R.EstateSubjectRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateSubjectRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateSubjectRent> update(EstateSubjectRent t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = subjectRentDao.openSession();
			tr = session.beginTransaction();
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			session.update(t);
			tr.commit();
			return Result.success(R.EstateSubjectRent.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateSubjectRent.class)));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.EstateSubjectRent.UPDATE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<EstateSubjectRent> getBeanById(Serializable id) {
		try {
			return Result.value(subjectRentDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.EstateSubjectRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EstateSubjectRent> getBeanByFilter(Filter filter) {
		try {
			return Result.value(subjectRentDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.EstateSubjectRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateSubjectRent>> getList() {
		try {
			return Result.value(subjectRentDao.getList());
		} catch (Exception e) {
			return Result.failure(R.EstateSubjectRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateSubjectRent>> getListByFilter(Filter filter) {
		try {
			return Result.value(subjectRentDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.EstateSubjectRent.LOAD_FAILURE);
		}
	}
	
	@Override
	public Result<List> getListBySql(String sql) {
		try {
			return Result.value(subjectRentDao.getObjectListBySql(sql));
		} catch (Exception e) {
			return Result.failure(R.EstateSubjectRent.LOAD_FAILURE);
		}
	}

}
