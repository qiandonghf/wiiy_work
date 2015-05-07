package com.wiiy.synthesis.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.synthesis.entity.Archives;
import com.wiiy.synthesis.dao.ArchivesDao;
import com.wiiy.synthesis.service.ArchivesService;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.activator.SynthesisActivator;

/**
 * @author my
 */
public class ArchivesServiceImpl implements ArchivesService{
	
	private ArchivesDao archivesDao;
	
	public void setArchivesDao(ArchivesDao archivesDao) {
		this.archivesDao = archivesDao;
	}

	@Override
	public Result<Archives> save(Archives t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			archivesDao.save(t);
			return Result.success(R.Archives.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Archives.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Archives.SAVE_FAILURE);
		}
	}

	@Override
	public Result<Archives> delete(Archives t) {
		try {
			archivesDao.delete(t);
			return Result.success(R.Archives.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Archives.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Archives> deleteById(Serializable id) {
		try {
			archivesDao.deleteById(id);
			return Result.success(R.Archives.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Archives.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Archives> deleteByIds(String ids) {
		try {
			archivesDao.deleteByIds(ids);
			return Result.success(R.Archives.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Archives.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Archives> update(Archives t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			archivesDao.update(t);
			return Result.success(R.Archives.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Archives.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Archives.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<Archives> getBeanById(Serializable id) {
		try {
			return Result.value(archivesDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Archives.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Archives> getBeanByFilter(Filter filter) {
		try {
			return Result.value(archivesDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Archives.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Archives>> getList() {
		try {
			return Result.value(archivesDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Archives.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Archives>> getListByFilter(Filter filter) {
		try {
			return Result.value(archivesDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Archives.LOAD_FAILURE);
		}
	}


}
