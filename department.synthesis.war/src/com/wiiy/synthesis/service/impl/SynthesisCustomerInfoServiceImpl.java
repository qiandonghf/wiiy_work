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
import com.wiiy.synthesis.entity.SynthesisCustomerInfo;
import com.wiiy.synthesis.dao.SynthesisCustomerInfoDao;
import com.wiiy.synthesis.service.SynthesisCustomerInfoService;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.activator.SynthesisActivator;

/**
 * @author my
 */
public class SynthesisCustomerInfoServiceImpl implements SynthesisCustomerInfoService{
	
	private SynthesisCustomerInfoDao synthesisCustomerInfoDao;
	
	public void setSynthesisCustomerInfoDao(SynthesisCustomerInfoDao synthesisCustomerInfoDao) {
		this.synthesisCustomerInfoDao = synthesisCustomerInfoDao;
	}

	@Override
	public Result<SynthesisCustomerInfo> save(SynthesisCustomerInfo t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			synthesisCustomerInfoDao.save(t);
			return Result.success(R.SynthesisCustomerInfo.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisCustomerInfo.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomerInfo.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisCustomerInfo> delete(SynthesisCustomerInfo t) {
		try {
			synthesisCustomerInfoDao.delete(t);
			return Result.success(R.SynthesisCustomerInfo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomerInfo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisCustomerInfo> deleteById(Serializable id) {
		try {
			synthesisCustomerInfoDao.deleteById(id);
			return Result.success(R.SynthesisCustomerInfo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomerInfo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisCustomerInfo> deleteByIds(String ids) {
		try {
			synthesisCustomerInfoDao.deleteByIds(ids);
			return Result.success(R.SynthesisCustomerInfo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomerInfo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisCustomerInfo> update(SynthesisCustomerInfo t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			synthesisCustomerInfoDao.update(t);
			return Result.success(R.SynthesisCustomerInfo.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisCustomerInfo.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomerInfo.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisCustomerInfo> getBeanById(Serializable id) {
		try {
			return Result.value(synthesisCustomerInfoDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomerInfo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SynthesisCustomerInfo> getBeanByFilter(Filter filter) {
		try {
			return Result.value(synthesisCustomerInfoDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomerInfo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SynthesisCustomerInfo>> getList() {
		try {
			return Result.value(synthesisCustomerInfoDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomerInfo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SynthesisCustomerInfo>> getListByFilter(Filter filter) {
		try {
			return Result.value(synthesisCustomerInfoDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomerInfo.LOAD_FAILURE);
		}
	}

}
