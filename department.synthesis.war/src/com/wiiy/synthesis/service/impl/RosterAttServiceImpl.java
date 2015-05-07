package com.wiiy.synthesis.service.impl;


import java.io.Serializable;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.synthesis.entity.RosterAtt;
import com.wiiy.synthesis.dao.RosterAttDao;
import com.wiiy.synthesis.service.RosterAttService;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.activator.SynthesisActivator;

/**
 * @author my
 */
public class RosterAttServiceImpl implements RosterAttService{
	
	private RosterAttDao rosterAttDao;
	
	public void setRosterAttDao(RosterAttDao rosterAttDao) {
		this.rosterAttDao = rosterAttDao;
	}
	@Override
	public Result<RosterAtt> save(RosterAtt t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			rosterAttDao.save(t);
			return Result.success(R.RosterAtt.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),RosterAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RosterAtt.SAVE_FAILURE);
		}
	}

	@Override
	public Result<RosterAtt> delete(RosterAtt t) {
		try {
			rosterAttDao.delete(t);
			return Result.success(R.RosterAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RosterAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<RosterAtt> deleteById(Serializable id) {
		try {
			rosterAttDao.deleteById(id);
			return Result.success(R.RosterAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RosterAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<RosterAtt> deleteByIds(String ids) {
		try {
			rosterAttDao.deleteByIds(ids);
			return Result.success(R.RosterAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RosterAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<RosterAtt> update(RosterAtt t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			rosterAttDao.update(t);
			return Result.success(R.RosterAtt.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),RosterAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RosterAtt.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<RosterAtt> getBeanById(Serializable id) {
		try {
			return Result.value(rosterAttDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RosterAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<RosterAtt> getBeanByFilter(Filter filter) {
		try {
			return Result.value(rosterAttDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RosterAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<RosterAtt>> getList() {
		try {
			return Result.value(rosterAttDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RosterAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<RosterAtt>> getListByFilter(Filter filter) {
		try {
			return Result.value(rosterAttDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RosterAtt.LOAD_FAILURE);
		}
	}

}
