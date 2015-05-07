package com.wiiy.synthesis.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.synthesis.entity.SynthesisFact;
import com.wiiy.synthesis.dao.SynthesisFactDao;
import com.wiiy.synthesis.service.SynthesisFactService;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.activator.SynthesisActivator;

/**
 * @author my
 */
public class SynthesisFactServiceImpl implements SynthesisFactService{
	
	private SynthesisFactDao synthesisFactDao;
	
	public void setSynthesisFactDao(SynthesisFactDao synthesisFactDao) {
		this.synthesisFactDao = synthesisFactDao;
	}

	@Override
	public Result<SynthesisFact> save(SynthesisFact t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			synthesisFactDao.save(t);
			return Result.success(R.SynthesisFact.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisFact.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisFact.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisFact> delete(SynthesisFact t) {
		try {
			synthesisFactDao.delete(t);
			return Result.success(R.SynthesisFact.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisFact.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisFact> deleteById(Serializable id) {
		try {
			synthesisFactDao.deleteById(id);
			return Result.success(R.SynthesisFact.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisFact.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisFact> deleteByIds(String ids) {
		try {
			synthesisFactDao.deleteByIds(ids);
			return Result.success(R.SynthesisFact.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisFact.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisFact> update(SynthesisFact t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			synthesisFactDao.update(t);
			return Result.success(R.SynthesisFact.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisFact.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisFact.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisFact> getBeanById(Serializable id) {
		try {
			return Result.value(synthesisFactDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisFact.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SynthesisFact> getBeanByFilter(Filter filter) {
		try {
			return Result.value(synthesisFactDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisFact.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SynthesisFact>> getList() {
		try {
			return Result.value(synthesisFactDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisFact.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SynthesisFact>> getListByFilter(Filter filter) {
		try {
			return Result.value(synthesisFactDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisFact.LOAD_FAILURE);
		}
	}

}
