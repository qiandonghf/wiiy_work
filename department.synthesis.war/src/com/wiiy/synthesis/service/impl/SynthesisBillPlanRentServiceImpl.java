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
import com.wiiy.synthesis.dao.SynthesisBillPlanRentDao;
import com.wiiy.synthesis.entity.SynthesisBillPlanRent;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.service.SynthesisBillPlanRentService;

/**
 * @author my
 */
public class SynthesisBillPlanRentServiceImpl implements SynthesisBillPlanRentService{
	
	private SynthesisBillPlanRentDao synthesisBillPlanRentDao;
	
	public void setSynthesisBillPlanRentDao(SynthesisBillPlanRentDao synthesisBillPlanRentDao) {
		this.synthesisBillPlanRentDao = synthesisBillPlanRentDao;
	}

	@Override
	public Result<SynthesisBillPlanRent> save(SynthesisBillPlanRent t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			synthesisBillPlanRentDao.save(t);
			return Result.success(R.SynthesisBillPlanRent.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisBillPlanRent.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisBillPlanRent.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisBillPlanRent> delete(SynthesisBillPlanRent t) {
		try {
			synthesisBillPlanRentDao.delete(t);
			return Result.success(R.SynthesisBillPlanRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisBillPlanRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisBillPlanRent> deleteById(Serializable id) {
		try {
			synthesisBillPlanRentDao.deleteById(id);
			return Result.success(R.SynthesisBillPlanRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisBillPlanRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisBillPlanRent> deleteByIds(String ids) {
		try {
			synthesisBillPlanRentDao.deleteByIds(ids);
			return Result.success(R.SynthesisBillPlanRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisBillPlanRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisBillPlanRent> update(SynthesisBillPlanRent t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			synthesisBillPlanRentDao.update(t);
			return Result.success(R.SynthesisBillPlanRent.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisBillPlanRent.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisBillPlanRent.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisBillPlanRent> getBeanById(Serializable id) {
		try {
			return Result.value(synthesisBillPlanRentDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisBillPlanRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SynthesisBillPlanRent> getBeanByFilter(Filter filter) {
		try {
			return Result.value(synthesisBillPlanRentDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisBillPlanRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SynthesisBillPlanRent>> getList() {
		try {
			return Result.value(synthesisBillPlanRentDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisBillPlanRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SynthesisBillPlanRent>> getListByFilter(Filter filter) {
		try {
			return Result.value(synthesisBillPlanRentDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisBillPlanRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<String> generateCode() {
		String code = "["+CalendarUtil.now().get(Calendar.YEAR)+"]";
		SynthesisBillPlanRent planRent = synthesisBillPlanRentDao.getBeanByFilter(//
				new Filter(SynthesisBillPlanRent.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)).//
				orderBy("id", Filter.DESC).maxResults(1));
		int count = synthesisBillPlanRentDao.getRowCount(//
				new Filter(SynthesisBillPlanRent.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)));
		try {
			String oldCode = planRent.getCode();
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
