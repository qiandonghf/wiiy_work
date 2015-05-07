package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.business.entity.ProjectLib;
import com.wiiy.business.dao.ProjectLibDao;
import com.wiiy.business.service.ProjectLibService;
import com.wiiy.business.preferences.R;
import com.wiiy.business.activator.BusinessActivator;

/**
 * @author my
 */
public class ProjectLibServiceImpl implements ProjectLibService{
	
	private ProjectLibDao projectLibDao;
	
	public void setprojectLibDao(ProjectLibDao projectLibDao) {
		this.projectLibDao = projectLibDao;
	}

	@Override
	public Result<ProjectLib> save(ProjectLib t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			projectLibDao.save(t);
			return Result.success(R.ProjectLib.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ProjectLib.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ProjectLib.SAVE_FAILURE);
		}
	}

	@Override
	public Result<ProjectLib> delete(ProjectLib t) {
		try {
			projectLibDao.delete(t);
			return Result.success(R.ProjectLib.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ProjectLib.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ProjectLib> deleteById(Serializable id) {
		try {
			projectLibDao.deleteById(id);
			return Result.success(R.ProjectLib.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ProjectLib.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ProjectLib> deleteByIds(String ids) {
		try {
			projectLibDao.deleteByIds(ids);
			return Result.success(R.ProjectLib.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ProjectLib.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ProjectLib> update(ProjectLib t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			projectLibDao.update(t);
			return Result.success(R.ProjectLib.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ProjectLib.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ProjectLib.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<ProjectLib> getBeanById(Serializable id) {
		try {
			return Result.value(projectLibDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ProjectLib.LOAD_FAILURE);
		}
	}

	@Override
	public Result<ProjectLib> getBeanByFilter(Filter filter) {
		try {
			return Result.value(projectLibDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ProjectLib.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ProjectLib>> getList() {
		try {
			return Result.value(projectLibDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ProjectLib.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ProjectLib>> getListByFilter(Filter filter) {
		try {
			return Result.value(projectLibDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ProjectLib.LOAD_FAILURE);
		}
	}

}
