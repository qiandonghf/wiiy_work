package com.wiiy.estate.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.estate.entity.ParkingManage;
import com.wiiy.estate.dao.ParkingManageDao;
import com.wiiy.estate.service.ParkingManageService;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.preferences.enums.ParkingManageEnum;
import com.wiiy.estate.activator.EstateActivator;

/**
 * @author my
 */
public class ParkingManageServiceImpl implements ParkingManageService{
	
	private ParkingManageDao parkingManageDao;
	
	public void setParkingManageDao(ParkingManageDao parkingManageDao) {
		this.parkingManageDao = parkingManageDao;
	}

	@Override
	public Result<ParkingManage> save(ParkingManage t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			parkingManageDao.save(t);
			
			return Result.success(R.ParkingManage.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ParkingManage.class)));
		} catch (Exception e) {
			return Result.failure(R.ParkingManage.SAVE_FAILURE);
		}
	}

	@Override
	public Result<ParkingManage> delete(ParkingManage t) {
		try {
			parkingManageDao.delete(t);
			return Result.success(R.ParkingManage.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.ParkingManage.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ParkingManage> deleteById(Serializable id) {
		try {
			parkingManageDao.deleteById(id);
			return Result.success(R.ParkingManage.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.ParkingManage.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ParkingManage> deleteByIds(String ids) {
		try {
			parkingManageDao.deleteByIds(ids);
			return Result.success(R.ParkingManage.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.ParkingManage.DELETE_FAILURE);
		}
	}
	private void setCreatorModifier(BaseEntity entity){
		entity.setCreateTime(new Date());
		entity.setCreatorId(EstateActivator.getSessionUser().getId());
		entity.setCreator(EstateActivator.getSessionUser().getRealName());
		entity.setModifyTime(entity.getCreateTime());
		entity.setModifier(entity.getCreator());
		entity.setModifierId(entity.getCreatorId());
		entity.setEntityStatus(EntityStatus.NORMAL);
	}
	@Override
	public Result<ParkingManage> update(ParkingManage t) {
		try {
			setCreatorModifier(t);
			parkingManageDao.update(t);
			return Result.success(R.ParkingManage.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ParkingManage.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ParkingManage.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<ParkingManage> getBeanById(Serializable id) {
		try {
			return Result.value(parkingManageDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.ParkingManage.LOAD_FAILURE);
		}
	}

	@Override
	public Result<ParkingManage> getBeanByFilter(Filter filter) {
		try {
			return Result.value(parkingManageDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.ParkingManage.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ParkingManage>> getList() {
		try {
			return Result.value(parkingManageDao.getList());
		} catch (Exception e) {
			return Result.failure(R.ParkingManage.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ParkingManage>> getListByFilter(Filter filter) {
		try {
			return Result.value(parkingManageDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.ParkingManage.LOAD_FAILURE);
		}
	}

}
