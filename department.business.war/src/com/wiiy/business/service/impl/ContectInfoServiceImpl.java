package com.wiiy.business.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.ContectInfoDao;
import com.wiiy.business.entity.BusinessContectInfo;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.ContectInfoService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

public class ContectInfoServiceImpl implements ContectInfoService{
	
	private ContectInfoDao contectInfoDao;
	
	public void setContectInfoDao(ContectInfoDao contectInfoDao) {
		this.contectInfoDao = contectInfoDao;
	}

	@Override
	public Result<BusinessContectInfo> save(BusinessContectInfo t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			contectInfoDao.save(t);
			BusinessActivator.getOperationLogService().logOP("新建交往信息【"+t.getContent()+"】");
			return Result.success(R.ContectInfo.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),BusinessContectInfo.class)));
		} catch (Exception e) {
			return Result.failure(R.ContectInfo.SAVE_FAILURE);
		}
	}

	@Override
	public Result<BusinessContectInfo> delete(BusinessContectInfo t) {
		try {
			BusinessActivator.getOperationLogService().logOP("删除交往信息【"+t.getContent()+"】");
			contectInfoDao.delete(t);
			return Result.success(R.ContectInfo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.ContectInfo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<BusinessContectInfo> deleteById(Serializable id) {
		try {
			contectInfoDao.deleteById(id);
			BusinessActivator.getOperationLogService().logOP("删除交往信息:id为【"+id+"】");
			return Result.success(R.ContectInfo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.ContectInfo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<BusinessContectInfo> deleteByIds(String ids) {
		try {
			contectInfoDao.deleteByIds(ids);
			BusinessActivator.getOperationLogService().logOP("删除交往信息:ids为【"+ids+"】");
			return Result.success(R.ContectInfo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.ContectInfo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<BusinessContectInfo> update(BusinessContectInfo t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			contectInfoDao.update(t);
			BusinessActivator.getOperationLogService().logOP("更新交往信息【"+t.getContent()+"】");
			return Result.success(R.ContectInfo.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),BusinessContectInfo.class)));
		} catch (Exception e) {
			return Result.failure(R.ContectInfo.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<BusinessContectInfo> getBeanById(Serializable id) {
		try {
			return Result.value(contectInfoDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.ContectInfo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<BusinessContectInfo> getBeanByFilter(Filter filter) {
		try {
			return Result.value(contectInfoDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.ContectInfo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<BusinessContectInfo>> getList() {
		try {
			return Result.value(contectInfoDao.getList());
		} catch (Exception e) {
			return Result.failure(R.ContectInfo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<BusinessContectInfo>> getListByFilter(Filter filter) {
		try {
			return Result.value(contectInfoDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.ContectInfo.LOAD_FAILURE);
		}
	}
	
}
