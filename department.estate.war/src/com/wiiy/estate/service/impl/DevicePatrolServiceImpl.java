package com.wiiy.estate.service.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dao.DevicePatrolDao;
import com.wiiy.estate.entity.DevicePatrol;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.service.DevicePatrolService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class DevicePatrolServiceImpl implements DevicePatrolService{
	
	private DevicePatrolDao devicePatrolDao;
	
	public void setDevicePatrolDao(DevicePatrolDao devicePatrolDao) {
		this.devicePatrolDao = devicePatrolDao;
	}

	@Override
	public Result<DevicePatrol> save(DevicePatrol t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			devicePatrolDao.save(t);
			return Result.success(R.DevicePatrol.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),DevicePatrol.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DevicePatrol.SAVE_FAILURE);
		}
	}

	@Override
	public Result<DevicePatrol> delete(DevicePatrol t) {
		try {
			devicePatrolDao.delete(t);
			return Result.success(R.DevicePatrol.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DevicePatrol.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DevicePatrol> deleteById(Serializable id) {
		try {
			devicePatrolDao.deleteById(id);
			return Result.success(R.DevicePatrol.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DevicePatrol.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DevicePatrol> deleteByIds(String ids) {
		try {
			devicePatrolDao.deleteByIds(ids);
			return Result.success(R.DevicePatrol.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DevicePatrol.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DevicePatrol> update(DevicePatrol t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			devicePatrolDao.update(t);
			return Result.success(R.DevicePatrol.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),DevicePatrol.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DevicePatrol.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<DevicePatrol> getBeanById(Serializable id) {
		try {
			return Result.value(devicePatrolDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DevicePatrol.LOAD_FAILURE);
		}
	}

	@Override
	public Result<DevicePatrol> getBeanByFilter(Filter filter) {
		try {
			return Result.value(devicePatrolDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DevicePatrol.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<DevicePatrol>> getList() {
		try {
			return Result.value(devicePatrolDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DevicePatrol.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<DevicePatrol>> getListByFilter(Filter filter) {
		try {
			return Result.value(devicePatrolDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DevicePatrol.LOAD_FAILURE);
		}
	}
	
	/**
	 * 判断是否已经巡检(上一次巡检到目前为止是否已经巡检过)
	 */
	@Override
	public boolean checkPatrol(String previous, String curTime) {
		Session session = null;
		Transaction ts = null;
		List<DevicePatrol> list = new ArrayList<DevicePatrol>();
		try {
			session = devicePatrolDao.openSession();
			ts = session.beginTransaction();
			//上次巡检日期开始，到目前为止是否有巡检过
			String sql = "select * from estate_device_patrol where operation_date>'"+previous+"' and operation_date<='"+curTime+"'";
			list = session.createSQLQuery(sql).list();
			if(list.size() > 0){
				return true;
			}
			ts.commit();
		} catch (Exception e) {
			ts.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}

}
