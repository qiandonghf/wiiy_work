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
import com.wiiy.estate.entity.DeviceWorkOrder;
import com.wiiy.estate.dao.DeviceWorkOrderDao;
import com.wiiy.estate.service.DeviceWorkOrderService;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.activator.EstateActivator;

/**
 * @author my
 */
public class DeviceWorkOrderServiceImpl implements DeviceWorkOrderService{
	
	private DeviceWorkOrderDao deviceWorkOrderDao;
	
	public void setDeviceWorkOrderDao(DeviceWorkOrderDao deviceWorkOrderDao) {
		this.deviceWorkOrderDao = deviceWorkOrderDao;
	}

	@Override
	public Result<DeviceWorkOrder> save(DeviceWorkOrder t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			deviceWorkOrderDao.save(t);
			return Result.success(R.DeviceWorkOrder.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),DeviceWorkOrder.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceWorkOrder.SAVE_FAILURE);
		}
	}

	@Override
	public Result<DeviceWorkOrder> delete(DeviceWorkOrder t) {
		try {
			deviceWorkOrderDao.delete(t);
			return Result.success(R.DeviceWorkOrder.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceWorkOrder.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DeviceWorkOrder> deleteById(Serializable id) {
		try {
			deviceWorkOrderDao.deleteById(id);
			return Result.success(R.DeviceWorkOrder.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceWorkOrder.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DeviceWorkOrder> deleteByIds(String ids) {
		try {
			deviceWorkOrderDao.deleteByIds(ids);
			return Result.success(R.DeviceWorkOrder.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceWorkOrder.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DeviceWorkOrder> update(DeviceWorkOrder t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			deviceWorkOrderDao.update(t);
			return Result.success(R.DeviceWorkOrder.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),DeviceWorkOrder.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceWorkOrder.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<DeviceWorkOrder> getBeanById(Serializable id) {
		try {
			return Result.value(deviceWorkOrderDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceWorkOrder.LOAD_FAILURE);
		}
	}

	@Override
	public Result<DeviceWorkOrder> getBeanByFilter(Filter filter) {
		try {
			return Result.value(deviceWorkOrderDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceWorkOrder.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<DeviceWorkOrder>> getList() {
		try {
			return Result.value(deviceWorkOrderDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceWorkOrder.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<DeviceWorkOrder>> getListByFilter(Filter filter) {
		try {
			return Result.value(deviceWorkOrderDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceWorkOrder.LOAD_FAILURE);
		}
	}

}
