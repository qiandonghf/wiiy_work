package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.business.dao.DataReportPropertyDao;
import com.wiiy.business.entity.DataReportProperty;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.DataReportPropertyService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.business.activator.BusinessActivator;

/**
 * @author my
 */
public class DataReportPropertyServiceImpl implements DataReportPropertyService{
	
	private DataReportPropertyDao dataReportPropertyDao;
	
	public void setDataReportPropertyDao(DataReportPropertyDao dataReportPropertyDao) {
		this.dataReportPropertyDao = dataReportPropertyDao;
	}

	@Override
	public Result<DataReportProperty> save(DataReportProperty t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			dataReportPropertyDao.save(t);
			return Result.success(R.DataReportProperty.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),DataReportProperty.class)));
		} catch (Exception e) {
			return Result.failure(R.DataReportProperty.SAVE_FAILURE);
		}
	}

	@Override
	public Result<DataReportProperty> delete(DataReportProperty t) {
		try {
			dataReportPropertyDao.delete(t);
			return Result.success(R.DataReportProperty.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.DataReportProperty.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DataReportProperty> deleteById(Serializable id) {
		try {
			dataReportPropertyDao.deleteById(id);
			return Result.success(R.DataReportProperty.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.DataReportProperty.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DataReportProperty> deleteByIds(String ids) {
		try {
			dataReportPropertyDao.deleteByIds(ids);
			return Result.success(R.DataReportProperty.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.DataReportProperty.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DataReportProperty> update(DataReportProperty t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			dataReportPropertyDao.update(t);
			return Result.success(R.DataReportProperty.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),DataReportProperty.class)));
		} catch (Exception e) {
			return Result.failure(R.DataReportProperty.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<DataReportProperty> getBeanById(Serializable id) {
		try {
			return Result.value(dataReportPropertyDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.DataReportProperty.LOAD_FAILURE);
		}
	}

	@Override
	public Result<DataReportProperty> getBeanByFilter(Filter filter) {
		try {
			return Result.value(dataReportPropertyDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.DataReportProperty.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<DataReportProperty>> getList() {
		try {
			return Result.value(dataReportPropertyDao.getList());
		} catch (Exception e) {
			return Result.failure(R.DataReportProperty.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<DataReportProperty>> getListByFilter(Filter filter) {
		try {
			return Result.value(dataReportPropertyDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.DataReportProperty.LOAD_FAILURE);
		}
	}

}
