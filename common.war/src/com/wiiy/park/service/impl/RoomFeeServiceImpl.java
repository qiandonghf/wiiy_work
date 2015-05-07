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
import com.wiiy.park.dao.RoomFeeDao;
import com.wiiy.park.entity.RoomFee;
import com.wiiy.park.service.RoomFeeService;

/**
 * @author my
 */
public class RoomFeeServiceImpl implements RoomFeeService{
	
	private RoomFeeDao roomFeeDao;
	
	public void setRoomFeeDao(RoomFeeDao roomFeeDao) {
		this.roomFeeDao = roomFeeDao;
	}

	@Override
	public Result<RoomFee> save(RoomFee t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(ProjectActivator.getSessionUser().getRealName());
			t.setCreatorId(ProjectActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			roomFeeDao.save(t);
			return Result.success(R.RoomFee.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),RoomFee.class)));
		} catch (Exception e) {
			return Result.failure(R.RoomFee.SAVE_FAILURE);
		}
	}

	@Override
	public Result<RoomFee> delete(RoomFee t) {
		try {
			roomFeeDao.delete(t);
			return Result.success(R.RoomFee.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.RoomFee.DELETE_FAILURE);
		}
	}

	@Override
	public Result<RoomFee> deleteById(Serializable id) {
		try {
			roomFeeDao.deleteById(id);
			return Result.success(R.RoomFee.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.RoomFee.DELETE_FAILURE);
		}
	}

	@Override
	public Result<RoomFee> deleteByIds(String ids) {
		try {
			roomFeeDao.deleteByIds(ids);
			return Result.success(R.RoomFee.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.RoomFee.DELETE_FAILURE);
		}
	}

	@Override
	public Result<RoomFee> update(RoomFee t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(ProjectActivator.getSessionUser().getRealName());
			t.setModifierId(ProjectActivator.getSessionUser().getId());
			roomFeeDao.update(t);
			return Result.success(R.RoomFee.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),RoomFee.class)));
		} catch (Exception e) {
			return Result.failure(R.RoomFee.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<RoomFee> getBeanById(Serializable id) {
		try {
			return Result.value(roomFeeDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.RoomFee.LOAD_FAILURE);
		}
	}

	@Override
	public Result<RoomFee> getBeanByFilter(Filter filter) {
		try {
			return Result.value(roomFeeDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.RoomFee.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<RoomFee>> getList() {
		try {
			return Result.value(roomFeeDao.getList());
		} catch (Exception e) {
			return Result.failure(R.RoomFee.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<RoomFee>> getListByFilter(Filter filter) {
		try {
			return Result.value(roomFeeDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.RoomFee.LOAD_FAILURE);
		}
	}

}
