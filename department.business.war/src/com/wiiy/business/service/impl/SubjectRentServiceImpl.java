package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.SubjectRentDao;
import com.wiiy.business.entity.BusinessSubjectRent;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.SubjectRentService;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
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
	public Result<BusinessSubjectRent> save(BusinessSubjectRent t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = subjectRentDao.openSession();
			tr = session.beginTransaction();
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			session.save(t);
			tr.commit();
			return Result.success(R.BusinessSubjectRent.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),BusinessSubjectRent.class)));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.BusinessSubjectRent.SAVE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<BusinessSubjectRent> delete(BusinessSubjectRent t) {
		try {
			subjectRentDao.delete(t);
			return Result.success(R.BusinessSubjectRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.BusinessSubjectRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<BusinessSubjectRent> deleteById(Serializable id) {
		try {
			subjectRentDao.deleteById(id);
			return Result.success(R.BusinessSubjectRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.BusinessSubjectRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<BusinessSubjectRent> deleteByIds(String ids) {
		try {
			subjectRentDao.deleteByIds(ids);
			return Result.success(R.BusinessSubjectRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.BusinessSubjectRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<BusinessSubjectRent> update(BusinessSubjectRent t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = subjectRentDao.openSession();
			tr = session.beginTransaction();
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			session.update(t);
			tr.commit();
			return Result.success(R.BusinessSubjectRent.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),BusinessSubjectRent.class)));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.BusinessSubjectRent.UPDATE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<BusinessSubjectRent> getBeanById(Serializable id) {
		try {
			return Result.value(subjectRentDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.BusinessSubjectRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<BusinessSubjectRent> getBeanByFilter(Filter filter) {
		try {
			return Result.value(subjectRentDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.BusinessSubjectRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<BusinessSubjectRent>> getList() {
		try {
			return Result.value(subjectRentDao.getList());
		} catch (Exception e) {
			return Result.failure(R.BusinessSubjectRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<BusinessSubjectRent>> getListByFilter(Filter filter) {
		try {
			return Result.value(subjectRentDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.BusinessSubjectRent.LOAD_FAILURE);
		}
	}
	
	@Override
	public Result<List> getListBySql(String sql) {
		try {
			return Result.value(subjectRentDao.getObjectListBySql(sql));
		} catch (Exception e) {
			return Result.failure(R.BusinessSubjectRent.LOAD_FAILURE);
		}
	}

}
