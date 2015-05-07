package com.wiiy.park.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.common.activator.ProjectActivator;
import com.wiiy.common.preferences.R;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.park.dao.ParkDao;
import com.wiiy.park.entity.Park;
import com.wiiy.park.service.ParkService;

/**
 * @author my
 */
public class ParkServiceImpl implements ParkService{
	
	private ParkDao parkDao;
	
	public void setParkDao(ParkDao parkDao) {
		this.parkDao = parkDao;
	}

	@Override
	public Result<Park> save(Park t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(ProjectActivator.getSessionUser().getRealName());
			t.setCreatorId(ProjectActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			parkDao.save(t);
			return Result.success(R.Park.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Park.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Park.SAVE_FAILURE);
		}
	}

	@Override
	public Result<Park> delete(Park t) {
		try {
			parkDao.delete(t);
			return Result.success(R.Park.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Park.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Park> deleteById(Serializable id) {
		try {
			parkDao.deleteById(id);
			return Result.success(R.Park.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Park.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Park> deleteByIds(String ids) {
		try {
			parkDao.deleteByIds(ids);
			return Result.success(R.Park.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Park.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Park> update(Park t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(ProjectActivator.getSessionUser().getRealName());
			t.setModifierId(ProjectActivator.getSessionUser().getId());
			parkDao.update(t);
			return Result.success(R.Park.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Park.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Park.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<Park> getBeanById(Serializable id) {
		try {
			return Result.value(parkDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Park.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Park> getBeanByFilter(Filter filter) {
		try {
			return Result.value(parkDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Park.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Park>> getList() {
		try {
			return Result.value(parkDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Park.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Park>> getListByFilter(Filter filter) {
		try {
			return Result.value(parkDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Park.LOAD_FAILURE);
		}
	}

}
