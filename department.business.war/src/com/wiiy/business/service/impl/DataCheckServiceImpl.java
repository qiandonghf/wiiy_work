package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.business.dao.DataCheckDao;
import com.wiiy.business.entity.DataCheck;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.DataCheckService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.business.activator.BusinessActivator;

/**
 * @author my
 */
public class DataCheckServiceImpl implements DataCheckService{
	
	private DataCheckDao dataCheckDao;
	
	public void setDataCheckDao(DataCheckDao dataCheckDao) {
		this.dataCheckDao = dataCheckDao;
	}

	@Override
	public Result<DataCheck> save(DataCheck t) {
		try {
			t.setCreateTime(new Date());
			/*t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());*/
			t.setModifyTime(t.getCreateTime());
			/*t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());*/
			t.setEntityStatus(EntityStatus.NORMAL);
			dataCheckDao.save(t);
			return Result.success(R.DataCheck.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),DataCheck.class)));
		} catch (Exception e) {
			return Result.failure(R.DataCheck.SAVE_FAILURE);
		}
	}

	@Override
	public Result<DataCheck> delete(DataCheck t) {
		try {
			dataCheckDao.delete(t);
			return Result.success(R.DataCheck.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.DataCheck.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DataCheck> deleteById(Serializable id) {
		try {
			dataCheckDao.deleteById(id);
			return Result.success(R.DataCheck.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.DataCheck.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DataCheck> deleteByIds(String ids) {
		try {
			dataCheckDao.deleteByIds(ids);
			return Result.success(R.DataCheck.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.DataCheck.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DataCheck> update(DataCheck t) {
		try {
			t.setModifyTime(new Date());
			/*t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());*/
			dataCheckDao.update(t);
			return Result.success(R.DataCheck.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),DataCheck.class)));
		} catch (Exception e) {
			return Result.failure(R.DataCheck.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<DataCheck> getBeanById(Serializable id) {
		try {
			return Result.value(dataCheckDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.DataCheck.LOAD_FAILURE);
		}
	}

	@Override
	public Result<DataCheck> getBeanByFilter(Filter filter) {
		try {
			return Result.value(dataCheckDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.DataCheck.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<DataCheck>> getList() {
		try {
			return Result.value(dataCheckDao.getList());
		} catch (Exception e) {
			return Result.failure(R.DataCheck.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<DataCheck>> getListByFilter(Filter filter) {
		try {
			return Result.value(dataCheckDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.DataCheck.LOAD_FAILURE);
		}
	}

}
