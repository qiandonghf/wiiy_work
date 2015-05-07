package com.wiiy.pf.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.core.entity.User;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.pf.activator.PfActivator;
import com.wiiy.pf.dao.ParkinDao;
import com.wiiy.pf.entity.Parkin;
import com.wiiy.pf.preferences.R;
import com.wiiy.pf.service.ParkinService;

/**
 * @author my
 */
public class ParkinServiceImpl implements ParkinService{
	
	private ParkinDao leaveDao;
	
	public void setParkinDao(ParkinDao leaveDao) {
		this.leaveDao = leaveDao;
	}

	@Override
	public Result<Parkin> save(Parkin t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = leaveDao.openSession();
			tr = session.beginTransaction();
			t.setCreateTime(new Date());
			User user = PfActivator.getSessionUser();
			t.setCreator(user.getRealName());
			t.setCreatorId(user.getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setStartUserId(user.getId().toString());
			t.setStartUserName(user.getRealName());
			t.setApplyTime(new Date());
			t.setEntityStatus(EntityStatus.NORMAL);
			leaveDao.save(t);
			tr.commit();
			return Result.success(R.Parkin.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Parkin.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.Parkin.SAVE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<Parkin> delete(Parkin t) {
		try {
			leaveDao.delete(t);
			return Result.success(R.Parkin.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Parkin.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Parkin> deleteById(Serializable id) {
		try {
			leaveDao.deleteById(id);
			return Result.success(R.Parkin.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Parkin.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Parkin> deleteByIds(String ids) {
		try {
			leaveDao.deleteByIds(ids);
			return Result.success(R.Parkin.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Parkin.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Parkin> update(Parkin t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(PfActivator.getSessionUser().getRealName());
			t.setModifierId(PfActivator.getSessionUser().getId());
			leaveDao.update(t);
			return Result.success(R.Parkin.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Parkin.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Parkin.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<Parkin> getBeanById(Serializable id) {
		try {
			return Result.value(leaveDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Parkin.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Parkin> getBeanByFilter(Filter filter) {
		try {
			return Result.value(leaveDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Parkin.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Parkin>> getList() {
		try {
			return Result.value(leaveDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Parkin.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Parkin>> getListByFilter(Filter filter) {
		try {
			return Result.value(leaveDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Parkin.LOAD_FAILURE);
		}
	}

}
