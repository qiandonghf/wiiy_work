package com.wiiy.pf.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.pf.activator.PfActivator;
import com.wiiy.pf.dao.ContactBaseLogDao;
import com.wiiy.pf.entity.ContactBaseLog;
import com.wiiy.pf.preferences.R;
import com.wiiy.pf.service.ContactBaseLogService;

/**
 * @author my
 */
public class ContactBaseLogServiceImpl implements ContactBaseLogService{
	
	private ContactBaseLogDao contactBaseLogDao;
	
	public void setContactBaseLogDao(ContactBaseLogDao contactBaseLogDao) {
		this.contactBaseLogDao = contactBaseLogDao;
	}

	@Override
	public Result<ContactBaseLog> save(ContactBaseLog t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(PfActivator.getSessionUser().getRealName());
			t.setCreatorId(PfActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			contactBaseLogDao.save(t);
			return Result.success(R.ContactBaseLog.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ContactBaseLog.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactBaseLog.SAVE_FAILURE);
		}
	}

	@Override
	public Result<ContactBaseLog> delete(ContactBaseLog t) {
		try {
			contactBaseLogDao.delete(t);
			return Result.success(R.ContactBaseLog.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactBaseLog.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContactBaseLog> deleteById(Serializable id) {
		try {
			contactBaseLogDao.deleteById(id);
			return Result.success(R.ContactBaseLog.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactBaseLog.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContactBaseLog> deleteByIds(String ids) {
		try {
			contactBaseLogDao.deleteByIds(ids);
			return Result.success(R.ContactBaseLog.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactBaseLog.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContactBaseLog> update(ContactBaseLog t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(PfActivator.getSessionUser().getRealName());
			t.setModifierId(PfActivator.getSessionUser().getId());
			contactBaseLogDao.update(t);
			return Result.success(R.ContactBaseLog.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ContactBaseLog.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactBaseLog.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<ContactBaseLog> getBeanById(Serializable id) {
		try {
			return Result.value(contactBaseLogDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactBaseLog.LOAD_FAILURE);
		}
	}

	@Override
	public Result<ContactBaseLog> getBeanByFilter(Filter filter) {
		try {
			return Result.value(contactBaseLogDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactBaseLog.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ContactBaseLog>> getList() {
		try {
			return Result.value(contactBaseLogDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactBaseLog.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ContactBaseLog>> getListByFilter(Filter filter) {
		try {
			return Result.value(contactBaseLogDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactBaseLog.LOAD_FAILURE);
		}
	}

}
