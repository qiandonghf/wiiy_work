package com.wiiy.park.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.common.activator.ProjectActivator;
import com.wiiy.common.preferences.R;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.park.dao.FloorDao;
import com.wiiy.park.entity.Floor;
import com.wiiy.park.service.FloorService;

/**
 * @author my
 */
public class FloorServiceImpl implements FloorService{
	
	private FloorDao floorDao;
	
	public void setFloorDao(FloorDao floorDao) {
		this.floorDao = floorDao;
	}

	@Override
	public Result<Floor> save(Floor t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(ProjectActivator.getSessionUser().getRealName());
			t.setCreatorId(ProjectActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			floorDao.save(t);
			return Result.success(R.Floor.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Floor.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Floor.SAVE_FAILURE);
		}
	}

	@Override
	public Result<Floor> delete(Floor t) {
		try {
			floorDao.delete(t);
			return Result.success(R.Floor.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Floor.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Floor> deleteById(Serializable id) {
		try {
			floorDao.deleteById(id);
			return Result.success(R.Floor.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Floor.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Floor> deleteByIds(String ids) {
		try {
			floorDao.deleteByIds(ids);
			return Result.success(R.Floor.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Floor.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Floor> update(Floor t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(ProjectActivator.getSessionUser().getRealName());
			t.setModifierId(ProjectActivator.getSessionUser().getId());
			floorDao.update(t);
			return Result.success(R.Floor.UPDATE_SUCCESS,t);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Floor.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Floor.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<Floor> getBeanById(Serializable id) {
		try {
			return Result.value(floorDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Floor.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Floor> getBeanByFilter(Filter filter) {
		try {
			return Result.value(floorDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Floor.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Floor>> getList() {
		try {
			return Result.value(floorDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Floor.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Floor>> getListByFilter(Filter filter) {
		try {
			return Result.value(floorDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Floor.LOAD_FAILURE);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result exchangeOrder(Long id1, Long id2) {
		Session session = null;
		Transaction tr = null;
		try {
			session = floorDao.openSession();
			tr = session.beginTransaction();
			Floor floor1 = (Floor) session.get(Floor.class,id1);
			Floor floor2 = (Floor) session.get(Floor.class,id2);
			int temp = floor1.getOrderNo();
			floor1.setOrderNo(floor2.getOrderNo());
			floor1.setModifyTime(new Date());
			floor1.setModifier(ProjectActivator.getSessionUser().getRealName());
			floor1.setModifierId(ProjectActivator.getSessionUser().getId());
			floor2.setOrderNo(temp);
			floor2.setModifyTime(new Date());
			floor2.setModifier(ProjectActivator.getSessionUser().getRealName());
			floor2.setModifierId(ProjectActivator.getSessionUser().getId());
			floorDao.update(floor1);
			floorDao.update(floor2);
			tr.commit();
			return Result.success(R.Floor.UPDATE_SUCCESS);
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.Floor.UPDATE_FAILURE);
		} finally {
			session.close();
		}
	}

}
