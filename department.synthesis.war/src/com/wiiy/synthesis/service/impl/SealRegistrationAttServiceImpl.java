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
import com.wiiy.synthesis.entity.SealRegistrationAtt;
import com.wiiy.synthesis.dao.SealRegistrationAttDao;
import com.wiiy.synthesis.service.SealRegistrationAttService;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.activator.SynthesisActivator;

/**
 * @author my
 */
public class SealRegistrationAttServiceImpl implements SealRegistrationAttService{
	
	private SealRegistrationAttDao sealRegistrationAttDao;
	
	public void setSealRegistrationAttDao(SealRegistrationAttDao sealRegistrationAttDao) {
		this.sealRegistrationAttDao = sealRegistrationAttDao;
	}

	@Override
	public Result<SealRegistrationAtt> save(SealRegistrationAtt t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			sealRegistrationAttDao.save(t);
			return Result.success(R.SealRegistrationAtt.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SealRegistrationAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SealRegistrationAtt.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SealRegistrationAtt> delete(SealRegistrationAtt t) {
		try {
			sealRegistrationAttDao.delete(t);
			return Result.success(R.SealRegistrationAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SealRegistrationAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SealRegistrationAtt> deleteById(Serializable id) {
		try {
			sealRegistrationAttDao.deleteById(id);
			return Result.success(R.SealRegistrationAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SealRegistrationAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SealRegistrationAtt> deleteByIds(String ids) {
		try {
			sealRegistrationAttDao.deleteByIds(ids);
			return Result.success(R.SealRegistrationAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SealRegistrationAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SealRegistrationAtt> update(SealRegistrationAtt t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			sealRegistrationAttDao.update(t);
			return Result.success(R.SealRegistrationAtt.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SealRegistrationAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SealRegistrationAtt.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SealRegistrationAtt> getBeanById(Serializable id) {
		try {
			return Result.value(sealRegistrationAttDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SealRegistrationAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SealRegistrationAtt> getBeanByFilter(Filter filter) {
		try {
			return Result.value(sealRegistrationAttDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SealRegistrationAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SealRegistrationAtt>> getList() {
		try {
			return Result.value(sealRegistrationAttDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SealRegistrationAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SealRegistrationAtt>> getListByFilter(Filter filter) {
		try {
			return Result.value(sealRegistrationAttDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SealRegistrationAtt.LOAD_FAILURE);
		}
	}

}
