package com.wiiy.sale.service.impl;


import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.sale.activator.SaleActivator;
import com.wiiy.sale.dao.SalePlanDao;
import com.wiiy.sale.entity.SalePlan;
import com.wiiy.sale.preferences.R;
import com.wiiy.sale.service.SalePlanService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class SalePlanServiceImpl implements SalePlanService{
	
	private SalePlanDao salePlanDao;
	
	public void setSalePlanDao(SalePlanDao salePlanDao) {
		this.salePlanDao = salePlanDao;
	}

	@Override
	public Result<SalePlan> save(SalePlan t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SaleActivator.getSessionUser().getRealName());
			t.setCreatorId(SaleActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			salePlanDao.save(t);
			return Result.success(R.SalePlan.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SalePlan.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SalePlan.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SalePlan> delete(SalePlan t) {
		try {
			salePlanDao.delete(t);
			return Result.success(R.SalePlan.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SalePlan.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SalePlan> deleteById(Serializable id) {
		try {
			salePlanDao.deleteById(id);
			return Result.success(R.SalePlan.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SalePlan.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SalePlan> deleteByIds(String ids) {
		try {
			salePlanDao.deleteByIds(ids);
			return Result.success(R.SalePlan.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SalePlan.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SalePlan> update(SalePlan t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SaleActivator.getSessionUser().getRealName());
			t.setModifierId(SaleActivator.getSessionUser().getId());
			salePlanDao.update(t);
			return Result.success(R.SalePlan.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SalePlan.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SalePlan.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SalePlan> getBeanById(Serializable id) {
		try {
			return Result.value(salePlanDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SalePlan.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SalePlan> getBeanByFilter(Filter filter) {
		try {
			return Result.value(salePlanDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SalePlan.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SalePlan>> getList() {
		try {
			return Result.value(salePlanDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SalePlan.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SalePlan>> getListByFilter(Filter filter) {
		try {
			return Result.value(salePlanDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SalePlan.LOAD_FAILURE);
		}
	}
	
	@Override
	public Result<String> generateCode() {
		String code = "["+CalendarUtil.now().get(Calendar.YEAR)+"]";
		SalePlan plan = salePlanDao.getBeanByFilter(//
				new Filter(SalePlan.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)).//
				orderBy("id", Filter.DESC).maxResults(1));
		int count = salePlanDao.getRowCount(//
				new Filter(SalePlan.class).//
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
