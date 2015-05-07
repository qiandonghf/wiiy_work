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
import com.wiiy.estate.dao.DeviceYearlyDao;
import com.wiiy.estate.entity.DeviceYearly;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.service.DeviceYearlyService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class DeviceYearlyServiceImpl implements DeviceYearlyService{
	
	private DeviceYearlyDao deviceYearlyDao;
	
	public void setDeviceYearlyDao(DeviceYearlyDao deviceYearlyDao) {
		this.deviceYearlyDao = deviceYearlyDao;
	}

	@Override
	public Result<DeviceYearly> save(DeviceYearly t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			deviceYearlyDao.save(t);
			return Result.success(R.DeviceYearly.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),DeviceYearly.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceYearly.SAVE_FAILURE);
		}
	}

	@Override
	public Result<DeviceYearly> delete(DeviceYearly t) {
		try {
			deviceYearlyDao.delete(t);
			return Result.success(R.DeviceYearly.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceYearly.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DeviceYearly> deleteById(Serializable id) {
		try {
			deviceYearlyDao.deleteById(id);
			return Result.success(R.DeviceYearly.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceYearly.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DeviceYearly> deleteByIds(String ids) {
		try {
			deviceYearlyDao.deleteByIds(ids);
			return Result.success(R.DeviceYearly.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceYearly.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DeviceYearly> update(DeviceYearly t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			deviceYearlyDao.update(t);
			return Result.success(R.DeviceYearly.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),DeviceYearly.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceYearly.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<DeviceYearly> getBeanById(Serializable id) {
		try {
			return Result.value(deviceYearlyDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceYearly.LOAD_FAILURE);
		}
	}

	@Override
	public Result<DeviceYearly> getBeanByFilter(Filter filter) {
		try {
			return Result.value(deviceYearlyDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceYearly.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<DeviceYearly>> getList() {
		try {
			return Result.value(deviceYearlyDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceYearly.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<DeviceYearly>> getListByFilter(Filter filter) {
		try {
			return Result.value(deviceYearlyDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.DeviceYearly.LOAD_FAILURE);
		}
	}

	@Override
	public List<Object> getListBySql(String sql) {
		return deviceYearlyDao.getObjectListBySql(sql);
	}
	
	//查询年检日期(上一次年检到目前为止是否已经年检过)
	@Override
 	public boolean checkYear(String previous, String curTime) {
 		Session session = null;
 		Transaction ts = null;
 		List<DeviceYearly> list = new ArrayList<DeviceYearly>();
 		try {
			session = deviceYearlyDao.openSession();
			ts = session.beginTransaction();
			String sql = "select * from estate_device_yearly where operation_date>'"+previous+"' and operation_date<'"+curTime+"'";
			list = session.createSQLQuery(sql).list();
			if(list.size() > 0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}

}
