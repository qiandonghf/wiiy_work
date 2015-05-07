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
import com.wiiy.park.dao.RoomHistoryDao;
import com.wiiy.park.entity.RoomHistory;
import com.wiiy.park.service.RoomHistoryService;

/**
 * @author my
 */
public class RoomHistoryServiceImpl implements RoomHistoryService{
	
	private RoomHistoryDao roomHistoryDao;
	
	public void setRoomHistoryDao(RoomHistoryDao roomHistoryDao) {
		this.roomHistoryDao = roomHistoryDao;
	}

	@Override
	public Result<RoomHistory> save(RoomHistory t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(ProjectActivator.getSessionUser().getRealName());
			t.setCreatorId(ProjectActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			roomHistoryDao.save(t);
			return Result.success(R.RoomHistory.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),RoomHistory.class)));
		} catch (Exception e) {
			return Result.failure(R.RoomHistory.SAVE_FAILURE);
		}
	}

	@Override
	public Result<RoomHistory> delete(RoomHistory t) {
		try {
			roomHistoryDao.delete(t);
			return Result.success(R.RoomHistory.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.RoomHistory.DELETE_FAILURE);
		}
	}

	@Override
	public Result<RoomHistory> deleteById(Serializable id) {
		try {
			roomHistoryDao.deleteById(id);
			return Result.success(R.RoomHistory.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.RoomHistory.DELETE_FAILURE);
		}
	}

	@Override
	public Result<RoomHistory> deleteByIds(String ids) {
		try {
			roomHistoryDao.deleteByIds(ids);
			return Result.success(R.RoomHistory.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.RoomHistory.DELETE_FAILURE);
		}
	}

	@Override
	public Result<RoomHistory> update(RoomHistory t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(ProjectActivator.getSessionUser().getRealName());
			t.setModifierId(ProjectActivator.getSessionUser().getId());
			roomHistoryDao.update(t);
			return Result.success(R.RoomHistory.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),RoomHistory.class)));
		} catch (Exception e) {
			return Result.failure(R.RoomHistory.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<RoomHistory> getBeanById(Serializable id) {
		try {
			return Result.value(roomHistoryDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.RoomHistory.LOAD_FAILURE);
		}
	}

	@Override
	public Result<RoomHistory> getBeanByFilter(Filter filter) {
		try {
			return Result.value(roomHistoryDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.RoomHistory.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<RoomHistory>> getList() {
		try {
			return Result.value(roomHistoryDao.getList());
		} catch (Exception e) {
			return Result.failure(R.RoomHistory.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<RoomHistory>> getListByFilter(Filter filter) {
		try {
			return Result.value(roomHistoryDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.RoomHistory.LOAD_FAILURE);
		}
	}

}
