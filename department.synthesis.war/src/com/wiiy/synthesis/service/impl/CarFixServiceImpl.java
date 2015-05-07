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
import com.wiiy.synthesis.entity.CarFix;
import com.wiiy.synthesis.dao.CarFixDao;
import com.wiiy.synthesis.service.CarFixService;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.activator.SynthesisActivator;

/**
 * @author my
 */
public class CarFixServiceImpl implements CarFixService{
	
	private CarFixDao carFixDao;
	
	public void setCarFixDao(CarFixDao carFixDao) {
		this.carFixDao = carFixDao;
	}

	@Override
	public Result<CarFix> save(CarFix t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			carFixDao.save(t);
			return Result.success(R.CarFix.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CarFix.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarFix.SAVE_FAILURE);
		}
	}

	@Override
	public Result<CarFix> delete(CarFix t) {
		try {
			carFixDao.delete(t);
			return Result.success(R.CarFix.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarFix.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CarFix> deleteById(Serializable id) {
		try {
			carFixDao.deleteById(id);
			return Result.success(R.CarFix.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarFix.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CarFix> deleteByIds(String ids) {
		try {
			carFixDao.deleteByIds(ids);
			return Result.success(R.CarFix.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarFix.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CarFix> update(CarFix t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			carFixDao.update(t);
			return Result.success(R.CarFix.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CarFix.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarFix.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<CarFix> getBeanById(Serializable id) {
		try {
			return Result.value(carFixDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarFix.LOAD_FAILURE);
		}
	}

	@Override
	public Result<CarFix> getBeanByFilter(Filter filter) {
		try {
			return Result.value(carFixDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarFix.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CarFix>> getList() {
		try {
			return Result.value(carFixDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarFix.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CarFix>> getListByFilter(Filter filter) {
		try {
			return Result.value(carFixDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarFix.LOAD_FAILURE);
		}
	}

}
