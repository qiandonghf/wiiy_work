package com.wiiy.estate.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dao.VehicleManagementDao;
import com.wiiy.estate.entity.VehicleManagement;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.service.VehicleManagementService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class VehicleManagementServiceImpl implements VehicleManagementService{
	
	private VehicleManagementDao vehicleManagementDao;
	
	public void setVehicleManagementDao(VehicleManagementDao vehicleManagementDao) {
		this.vehicleManagementDao = vehicleManagementDao;
	}

	@Override
	public Result<VehicleManagement> save(VehicleManagement t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			vehicleManagementDao.save(t);
			return Result.success(R.VehicleManagement.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),VehicleManagement.class)));
		} catch (Exception e) {
			return Result.failure(R.VehicleManagement.SAVE_FAILURE);
		}
	}

	@Override
	public Result<VehicleManagement> delete(VehicleManagement t) {
		try {
			vehicleManagementDao.delete(t);
			return Result.success(R.VehicleManagement.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.VehicleManagement.DELETE_FAILURE);
		}
	}

	@Override
	public Result<VehicleManagement> deleteById(Serializable id) {
		try {
			vehicleManagementDao.deleteById(id);
			return Result.success(R.VehicleManagement.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.VehicleManagement.DELETE_FAILURE);
		}
	}

	@Override
	public Result<VehicleManagement> deleteByIds(String ids) {
		try {
			vehicleManagementDao.deleteByIds(ids);
			return Result.success(R.VehicleManagement.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.VehicleManagement.DELETE_FAILURE);
		}
	}

	@Override
	public Result<VehicleManagement> update(VehicleManagement t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			vehicleManagementDao.update(t);
			return Result.success(R.VehicleManagement.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),VehicleManagement.class)));
		} catch (Exception e) {
			return Result.failure(R.VehicleManagement.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<VehicleManagement> getBeanById(Serializable id) {
		try {
			return Result.value(vehicleManagementDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.VehicleManagement.LOAD_FAILURE);
		}
	}

	@Override
	public Result<VehicleManagement> getBeanByFilter(Filter filter) {
		try {
			return Result.value(vehicleManagementDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.VehicleManagement.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<VehicleManagement>> getList() {
		try {
			return Result.value(vehicleManagementDao.getList());
		} catch (Exception e) {
			return Result.failure(R.VehicleManagement.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<VehicleManagement>> getListByFilter(Filter filter) {
		try {
			return Result.value(vehicleManagementDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.VehicleManagement.LOAD_FAILURE);
		}
	}

}
