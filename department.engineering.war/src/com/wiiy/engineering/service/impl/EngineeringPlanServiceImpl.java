package com.wiiy.engineering.service.impl;


import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.engineering.activator.EngineeringActivator;
import com.wiiy.engineering.dao.EngineeringPlanDao;
import com.wiiy.engineering.entity.EngineeringPlan;
import com.wiiy.engineering.preferences.R;
import com.wiiy.engineering.service.EngineeringPlanService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class EngineeringPlanServiceImpl implements EngineeringPlanService{
	
	private EngineeringPlanDao engineeringPlanDao;
	
	public void setEngineeringPlanDao(EngineeringPlanDao engineeringPlanDao) {
		this.engineeringPlanDao = engineeringPlanDao;
	}

	@Override
	public Result<EngineeringPlan> save(EngineeringPlan t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EngineeringActivator.getSessionUser().getRealName());
			t.setCreatorId(EngineeringActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			engineeringPlanDao.save(t);
			return Result.success(R.EngineeringPlan.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EngineeringPlan.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringPlan.SAVE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringPlan> delete(EngineeringPlan t) {
		try {
			engineeringPlanDao.delete(t);
			return Result.success(R.EngineeringPlan.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringPlan.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringPlan> deleteById(Serializable id) {
		try {
			engineeringPlanDao.deleteById(id);
			return Result.success(R.EngineeringPlan.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringPlan.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringPlan> deleteByIds(String ids) {
		try {
			engineeringPlanDao.deleteByIds(ids);
			return Result.success(R.EngineeringPlan.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringPlan.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringPlan> update(EngineeringPlan t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EngineeringActivator.getSessionUser().getRealName());
			t.setModifierId(EngineeringActivator.getSessionUser().getId());
			engineeringPlanDao.update(t);
			return Result.success(R.EngineeringPlan.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EngineeringPlan.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringPlan.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringPlan> getBeanById(Serializable id) {
		try {
			return Result.value(engineeringPlanDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringPlan.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EngineeringPlan> getBeanByFilter(Filter filter) {
		try {
			return Result.value(engineeringPlanDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringPlan.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EngineeringPlan>> getList() {
		try {
			return Result.value(engineeringPlanDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringPlan.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EngineeringPlan>> getListByFilter(Filter filter) {
		try {
			return Result.value(engineeringPlanDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringPlan.LOAD_FAILURE);
		}
	}
	
	@Override
	public Result<String> generateCode() {
		String code = "["+CalendarUtil.now().get(Calendar.YEAR)+"]";
		EngineeringPlan plan = engineeringPlanDao.getBeanByFilter(//
				new Filter(EngineeringPlan.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)).//
				orderBy("id", Filter.DESC).maxResults(1));
		int count = engineeringPlanDao.getRowCount(//
				new Filter(EngineeringPlan.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)));
		try {
			String oldCode = plan.getCode();
			count = Integer.parseInt(//
					oldCode.replace("["+CalendarUtil.now().get(Calendar.YEAR)+"]", ""));
		} catch (Exception e) {
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		nf.setMinimumIntegerDigits(6);
		code = code + nf.format(count+1);
		return Result.value(code);
	}
}
