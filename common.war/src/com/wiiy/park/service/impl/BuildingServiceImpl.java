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
import com.wiiy.park.dao.BuildingDao;
import com.wiiy.park.entity.Building;
import com.wiiy.park.service.BuildingService;

/**
 * @author my
 */
public class BuildingServiceImpl implements BuildingService{
	
	private BuildingDao buildingDao;
	
	public void setBuildingDao(BuildingDao buildingDao) {
		this.buildingDao = buildingDao;
	}

	@Override
	public Result<Building> save(Building t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(ProjectActivator.getSessionUser().getRealName());
			t.setCreatorId(ProjectActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			buildingDao.save(t);
			return Result.success(R.Building.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Building.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Building.SAVE_FAILURE);
		}
	}

	@Override
	public Result<Building> delete(Building t) {
		try {
			buildingDao.delete(t);
			return Result.success(R.Building.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Building.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Building> deleteById(Serializable id) {
		try {
			buildingDao.deleteById(id);
			return Result.success(R.Building.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Building.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Building> deleteByIds(String ids) {
		try {
			buildingDao.deleteByIds(ids);
			return Result.success(R.Building.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Building.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Building> update(Building t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(ProjectActivator.getSessionUser().getRealName());
			t.setModifierId(ProjectActivator.getSessionUser().getId());
			buildingDao.update(t);
			return Result.success(R.Building.UPDATE_SUCCESS,t);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Building.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Building.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<Building> getBeanById(Serializable id) {
		try {
			return Result.value(buildingDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Building.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Building> getBeanByFilter(Filter filter) {
		try {
			return Result.value(buildingDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Building.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Building>> getList() {
		try {
			return Result.value(buildingDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Building.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Building>> getListByFilter(Filter filter) {
		try {
			return Result.value(buildingDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Building.LOAD_FAILURE);
		}
	}
	
	@Override
	public Result<List<Building>> getListByHql(String hql) {
		try {
			return Result.value(buildingDao.getListByHql(hql));
			
		} catch (Exception e) {
			return Result.failure(R.Building.LOAD_FAILURE);
		}
	}

}
