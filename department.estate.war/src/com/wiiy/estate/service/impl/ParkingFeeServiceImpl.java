package com.wiiy.estate.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.estate.entity.ParkingFee;
import com.wiiy.estate.dao.ParkingFeeDao;
import com.wiiy.estate.service.ParkingFeeService;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.activator.EstateActivator;

/**
 * @author my
 */
public class ParkingFeeServiceImpl implements ParkingFeeService{
	
	private ParkingFeeDao parkingFeeDao;
	
	public void setParkingFeeDao(ParkingFeeDao parkingFeeDao) {
		this.parkingFeeDao = parkingFeeDao;
	}

	@Override
	public Result<ParkingFee> save(ParkingFee t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			parkingFeeDao.save(t);
			return Result.success(R.ParkingFee.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ParkingFee.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ParkingFee.SAVE_FAILURE);
		}
	}

	@Override
	public Result<ParkingFee> delete(ParkingFee t) {
		try {
			parkingFeeDao.delete(t);
			return Result.success(R.ParkingFee.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ParkingFee.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ParkingFee> deleteById(Serializable id) {
		try {
			parkingFeeDao.deleteById(id);
			return Result.success(R.ParkingFee.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ParkingFee.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ParkingFee> deleteByIds(String ids) {
		try {
			parkingFeeDao.deleteByIds(ids);
			return Result.success(R.ParkingFee.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ParkingFee.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ParkingFee> update(ParkingFee t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			parkingFeeDao.update(t);
			return Result.success(R.ParkingFee.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ParkingFee.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ParkingFee.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<ParkingFee> getBeanById(Serializable id) {
		try {
			return Result.value(parkingFeeDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ParkingFee.LOAD_FAILURE);
		}
	}

	@Override
	public Result<ParkingFee> getBeanByFilter(Filter filter) {
		try {
			return Result.value(parkingFeeDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ParkingFee.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ParkingFee>> getList() {
		try {
			return Result.value(parkingFeeDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ParkingFee.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ParkingFee>> getListByFilter(Filter filter) {
		try {
			return Result.value(parkingFeeDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ParkingFee.LOAD_FAILURE);
		}
	}

	@Override
	public List<Object> getListBySql(String sql) {
		return parkingFeeDao.getObjectListBySql(sql);
	}

}
