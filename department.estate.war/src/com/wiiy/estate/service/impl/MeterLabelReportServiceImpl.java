package com.wiiy.estate.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.estate.entity.MeterLabelReport;
import com.wiiy.estate.dao.MeterLabelReportDao;
import com.wiiy.estate.service.MeterLabelReportService;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.activator.EstateActivator;

/**
 * @author my
 */
public class MeterLabelReportServiceImpl implements MeterLabelReportService{
	
	private MeterLabelReportDao meterLabelReportDao;
	
	public void setMeterLabelReportDao(MeterLabelReportDao meterLabelReportDao) {
		this.meterLabelReportDao = meterLabelReportDao;
	}

	@Override
	public Result<MeterLabelReport> save(MeterLabelReport t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			meterLabelReportDao.save(t);
			return Result.success(R.MeterLabelReport.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),MeterLabelReport.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.MeterLabelReport.SAVE_FAILURE);
		}
	}

	@Override
	public Result<MeterLabelReport> delete(MeterLabelReport t) {
		try {
			meterLabelReportDao.delete(t);
			return Result.success(R.MeterLabelReport.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.MeterLabelReport.DELETE_FAILURE);
		}
	}

	@Override
	public Result<MeterLabelReport> deleteById(Serializable id) {
		try {
			meterLabelReportDao.deleteById(id);
			return Result.success(R.MeterLabelReport.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.MeterLabelReport.DELETE_FAILURE);
		}
	}

	@Override
	public Result<MeterLabelReport> deleteByIds(String ids) {
		try {
			meterLabelReportDao.deleteByIds(ids);
			return Result.success(R.MeterLabelReport.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.MeterLabelReport.DELETE_FAILURE);
		}
	}

	@Override
	public Result<MeterLabelReport> update(MeterLabelReport t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			meterLabelReportDao.update(t);
			return Result.success(R.MeterLabelReport.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),MeterLabelReport.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.MeterLabelReport.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<MeterLabelReport> getBeanById(Serializable id) {
		try {
			return Result.value(meterLabelReportDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.MeterLabelReport.LOAD_FAILURE);
		}
	}

	@Override
	public Result<MeterLabelReport> getBeanByFilter(Filter filter) {
		try {
			return Result.value(meterLabelReportDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.MeterLabelReport.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<MeterLabelReport>> getList() {
		try {
			return Result.value(meterLabelReportDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.MeterLabelReport.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<MeterLabelReport>> getListByFilter(Filter filter) {
		try {
			return Result.value(meterLabelReportDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.MeterLabelReport.LOAD_FAILURE);
		}
	}

}
