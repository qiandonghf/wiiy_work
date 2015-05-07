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
import com.wiiy.synthesis.entity.Car;
import com.wiiy.synthesis.dao.CarDao;
import com.wiiy.synthesis.service.CarService;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.activator.SynthesisActivator;

/**
 * @author my
 */
public class CarServiceImpl implements CarService{
	
	private CarDao carDao;
	
	public void setCarDao(CarDao carDao) {
		this.carDao = carDao;
	}

	@Override
	public Result<Car> save(Car t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			carDao.save(t);
			return Result.success(R.Car.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Car.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Car.SAVE_FAILURE);
		}
	}

	@Override
	public Result<Car> delete(Car t) {
		try {
			carDao.delete(t);
			return Result.success(R.Car.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Car.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Car> deleteById(Serializable id) {
		try {
			carDao.deleteById(id);
			return Result.success(R.Car.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Car.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Car> deleteByIds(String ids) {
		try {
			carDao.deleteByIds(ids);
			return Result.success(R.Car.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Car.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Car> update(Car t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			carDao.update(t);
			return Result.success(R.Car.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Car.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Car.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<Car> getBeanById(Serializable id) {
		try {
			return Result.value(carDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Car.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Car> getBeanByFilter(Filter filter) {
		try {
			return Result.value(carDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Car.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Car>> getList() {
		try {
			return Result.value(carDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Car.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Car>> getListByFilter(Filter filter) {
		try {
			return Result.value(carDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Car.LOAD_FAILURE);
		}
	}

}
