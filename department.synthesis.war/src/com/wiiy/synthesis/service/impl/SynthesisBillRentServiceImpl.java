package com.wiiy.synthesis.service.impl;


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
import com.wiiy.synthesis.entity.SynthesisBillRent;
import com.wiiy.synthesis.dao.SynthesisBillRentDao;
import com.wiiy.synthesis.service.SynthesisBillRentService;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.activator.SynthesisActivator;

/**
 * @author my
 */
public class SynthesisBillRentServiceImpl implements SynthesisBillRentService{
	
	private SynthesisBillRentDao synthesisBillRentDao;
	
	public void setSynthesisBillRentDao(SynthesisBillRentDao synthesisBillRentDao) {
		this.synthesisBillRentDao = synthesisBillRentDao;
	}

	@Override
	public Result<SynthesisBillRent> save(SynthesisBillRent t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			synthesisBillRentDao.save(t);
			return Result.success(R.SynthesisBillRent.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisBillRent.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisBillRent.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisBillRent> delete(SynthesisBillRent t) {
		try {
			synthesisBillRentDao.delete(t);
			return Result.success(R.SynthesisBillRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisBillRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisBillRent> deleteById(Serializable id) {
		try {
			synthesisBillRentDao.deleteById(id);
			return Result.success(R.SynthesisBillRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisBillRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisBillRent> deleteByIds(String ids) {
		try {
			synthesisBillRentDao.deleteByIds(ids);
			return Result.success(R.SynthesisBillRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisBillRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisBillRent> update(SynthesisBillRent t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			synthesisBillRentDao.update(t);
			return Result.success(R.SynthesisBillRent.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisBillRent.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisBillRent.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisBillRent> getBeanById(Serializable id) {
		try {
			return Result.value(synthesisBillRentDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisBillRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SynthesisBillRent> getBeanByFilter(Filter filter) {
		try {
			return Result.value(synthesisBillRentDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisBillRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SynthesisBillRent>> getList() {
		try {
			return Result.value(synthesisBillRentDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisBillRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SynthesisBillRent>> getListByFilter(Filter filter) {
		try {
			return Result.value(synthesisBillRentDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisBillRent.LOAD_FAILURE);
		}
	}
	
	@Override
	public Result<List> getResultBySql(String sql) {
		Session session = null;
		try {
			session = synthesisBillRentDao.openSession();
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
