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
import com.wiiy.estate.entity.DeviceMaintenance;
import com.wiiy.estate.dao.DeviceMaintenanceDao;
import com.wiiy.estate.service.DeviceMaintenanceService;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.activator.EstateActivator;

/**
 * @author my
 */
public class DeviceMaintenanceServiceImpl implements DeviceMaintenanceService{
	
	private DeviceMaintenanceDao deviceMaintenanceDao;
	
	public void setDeviceMaintenanceDao(DeviceMaintenanceDao deviceMaintenanceDao) {
		this.deviceMaintenanceDao = deviceMaintenanceDao;
	}

	@Override
	public Result<DeviceMaintenance> save(DeviceMaintenance t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			deviceMaintenanceDao.save(t);
			return Result.success(R.DeviceMaintenance.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),DeviceMaintenance.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceMaintenance.SAVE_FAILURE);
		}
	}

	@Override
	public Result<DeviceMaintenance> delete(DeviceMaintenance t) {
		try {
			deviceMaintenanceDao.delete(t);
			return Result.success(R.DeviceMaintenance.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceMaintenance.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DeviceMaintenance> deleteById(Serializable id) {
		try {
			deviceMaintenanceDao.deleteById(id);
			return Result.success(R.DeviceMaintenance.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceMaintenance.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DeviceMaintenance> deleteByIds(String ids) {
		try {
			deviceMaintenanceDao.deleteByIds(ids);
			return Result.success(R.DeviceMaintenance.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceMaintenance.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DeviceMaintenance> update(DeviceMaintenance t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			deviceMaintenanceDao.update(t);
			return Result.success(R.DeviceMaintenance.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),DeviceMaintenance.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceMaintenance.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<DeviceMaintenance> getBeanById(Serializable id) {
		try {
			return Result.value(deviceMaintenanceDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceMaintenance.LOAD_FAILURE);
		}
	}

	@Override
	public Result<DeviceMaintenance> getBeanByFilter(Filter filter) {
		try {
			return Result.value(deviceMaintenanceDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceMaintenance.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<DeviceMaintenance>> getList() {
		try {
			return Result.value(deviceMaintenanceDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceMaintenance.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<DeviceMaintenance>> getListByFilter(Filter filter) {
		try {
			return Result.value(deviceMaintenanceDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceMaintenance.LOAD_FAILURE);
		}
	}

}
