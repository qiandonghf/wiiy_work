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
import com.wiiy.synthesis.entity.Seal;
import com.wiiy.synthesis.dao.SealDao;
import com.wiiy.synthesis.service.SealService;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.activator.SynthesisActivator;

/**
 * @author my
 */
public class SealServiceImpl implements SealService{
	
	private SealDao sealDao;
	
	public void setSealDao(SealDao sealDao) {
		this.sealDao = sealDao;
	}

	@Override
	public Result<Seal> save(Seal t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			sealDao.save(t);
			return Result.success(R.Seal.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Seal.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Seal.SAVE_FAILURE);
		}
	}

	@Override
	public Result<Seal> delete(Seal t) {
		try {
			sealDao.delete(t);
			return Result.success(R.Seal.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Seal.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Seal> deleteById(Serializable id) {
		try {
			sealDao.deleteById(id);
			return Result.success(R.Seal.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Seal.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Seal> deleteByIds(String ids) {
		try {
			sealDao.deleteByIds(ids);
			return Result.success(R.Seal.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Seal.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Seal> update(Seal t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			sealDao.update(t);
			return Result.success(R.Seal.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Seal.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Seal.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<Seal> getBeanById(Serializable id) {
		try {
			return Result.value(sealDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Seal.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Seal> getBeanByFilter(Filter filter) {
		try {
			return Result.value(sealDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Seal.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Seal>> getList() {
		try {
			return Result.value(sealDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Seal.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Seal>> getListByFilter(Filter filter) {
		try {
			return Result.value(sealDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Seal.LOAD_FAILURE);
		}
	}

}
