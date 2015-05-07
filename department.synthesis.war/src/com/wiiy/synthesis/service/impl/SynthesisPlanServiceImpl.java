package com.wiiy.synthesis.service.impl;


import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.synthesis.activator.SynthesisActivator;
import com.wiiy.synthesis.dao.SynthesisPlanDao;
import com.wiiy.synthesis.entity.SynthesisPlan;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.service.SynthesisPlanService;

/**
 * @author my
 */
public class SynthesisPlanServiceImpl implements SynthesisPlanService{
	
	private SynthesisPlanDao synthesisPlanDao;
	
	public void setSynthesisPlanDao(SynthesisPlanDao synthesisPlanDao) {
		this.synthesisPlanDao = synthesisPlanDao;
	}

	@Override
	public Result<SynthesisPlan> save(SynthesisPlan t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			synthesisPlanDao.save(t);
			return Result.success(R.SynthesisPlan.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisPlan.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisPlan.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisPlan> delete(SynthesisPlan t) {
		try {
			synthesisPlanDao.delete(t);
			return Result.success(R.SynthesisPlan.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisPlan.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisPlan> deleteById(Serializable id) {
		try {
			synthesisPlanDao.deleteById(id);
			return Result.success(R.SynthesisPlan.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisPlan.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisPlan> deleteByIds(String ids) {
		try {
			synthesisPlanDao.deleteByIds(ids);
			return Result.success(R.SynthesisPlan.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisPlan.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisPlan> update(SynthesisPlan t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			synthesisPlanDao.update(t);
			return Result.success(R.SynthesisPlan.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisPlan.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisPlan.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisPlan> getBeanById(Serializable id) {
		try {
			return Result.value(synthesisPlanDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisPlan.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SynthesisPlan> getBeanByFilter(Filter filter) {
		try {
			return Result.value(synthesisPlanDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisPlan.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SynthesisPlan>> getList() {
		try {
			return Result.value(synthesisPlanDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisPlan.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SynthesisPlan>> getListByFilter(Filter filter) {
		try {
			return Result.value(synthesisPlanDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisPlan.LOAD_FAILURE);
		}
	}
	
	@Override
	public Result<String> generateCode() {
		String code = "["+CalendarUtil.now().get(Calendar.YEAR)+"]";
		SynthesisPlan plan = synthesisPlanDao.getBeanByFilter(//
				new Filter(SynthesisPlan.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)).//
				orderBy("id", Filter.DESC).maxResults(1));
		int count = synthesisPlanDao.getRowCount(//
				new Filter(SynthesisPlan.class).//
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
