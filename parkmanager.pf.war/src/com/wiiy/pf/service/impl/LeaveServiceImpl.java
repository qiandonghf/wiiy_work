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
import com.wiiy.pf.dao.LeaveDao;
import com.wiiy.pf.entity.ContactBaseAtt;
import com.wiiy.pf.entity.Leave;
import com.wiiy.pf.preferences.R;
import com.wiiy.pf.service.LeaveService;

/**
 * @author my
 */
public class LeaveServiceImpl implements LeaveService{
	
	private LeaveDao leaveDao;
	
	public void setLeaveDao(LeaveDao leaveDao) {
		this.leaveDao = leaveDao;
	}

	@Override
	public Result<Leave> save(Leave t) {
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
			t.setUserId(user.getId().toString());
			t.setApplyTime(new Date());
			t.setEntityStatus(EntityStatus.NORMAL);
			leaveDao.save(t);
			tr.commit();
			return Result.success(R.Leave.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Leave.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.Leave.SAVE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<Leave> delete(Leave t) {
		try {
			leaveDao.delete(t);
			return Result.success(R.Leave.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Leave.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Leave> deleteById(Serializable id) {
		try {
			leaveDao.deleteById(id);
			return Result.success(R.Leave.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Leave.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Leave> deleteByIds(String ids) {
		try {
			leaveDao.deleteByIds(ids);
			return Result.success(R.Leave.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Leave.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Leave> update(Leave t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(PfActivator.getSessionUser().getRealName());
			t.setModifierId(PfActivator.getSessionUser().getId());
			leaveDao.update(t);
			return Result.success(R.Leave.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Leave.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Leave.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<Leave> getBeanById(Serializable id) {
		try {
			return Result.value(leaveDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Leave.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Leave> getBeanByFilter(Filter filter) {
		try {
			return Result.value(leaveDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Leave.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Leave>> getList() {
		try {
			return Result.value(leaveDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Leave.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Leave>> getListByFilter(Filter filter) {
		try {
			return Result.value(leaveDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Leave.LOAD_FAILURE);
		}
	}

}
