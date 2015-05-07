package com.wiiy.engineering.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.engineering.entity.EngineeringContractAtt;
import com.wiiy.engineering.dao.EngineeringContractAttDao;
import com.wiiy.engineering.service.EngineeringContractAttService;
import com.wiiy.engineering.preferences.R;
import com.wiiy.engineering.activator.EngineeringActivator;

/**
 * @author my
 */
public class EngineeringContractAttServiceImpl implements EngineeringContractAttService{
	
	private EngineeringContractAttDao engineeringContractAttDao;
	
	public void setEngineeringContractAttDao(EngineeringContractAttDao engineeringContractAttDao) {
		this.engineeringContractAttDao = engineeringContractAttDao;
	}

	@Override
	public Result<EngineeringContractAtt> save(EngineeringContractAtt t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EngineeringActivator.getSessionUser().getRealName());
			t.setCreatorId(EngineeringActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			engineeringContractAttDao.save(t);
			return Result.success(R.EngineeringContractAtt.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EngineeringContractAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringContractAtt.SAVE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringContractAtt> delete(EngineeringContractAtt t) {
		try {
			engineeringContractAttDao.delete(t);
			return Result.success(R.EngineeringContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringContractAtt> deleteById(Serializable id) {
		try {
			engineeringContractAttDao.deleteById(id);
			return Result.success(R.EngineeringContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringContractAtt> deleteByIds(String ids) {
		try {
			engineeringContractAttDao.deleteByIds(ids);
			return Result.success(R.EngineeringContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringContractAtt> update(EngineeringContractAtt t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EngineeringActivator.getSessionUser().getRealName());
			t.setModifierId(EngineeringActivator.getSessionUser().getId());
			engineeringContractAttDao.update(t);
			return Result.success(R.EngineeringContractAtt.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EngineeringContractAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringContractAtt.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringContractAtt> getBeanById(Serializable id) {
		try {
			return Result.value(engineeringContractAttDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EngineeringContractAtt> getBeanByFilter(Filter filter) {
		try {
			return Result.value(engineeringContractAttDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EngineeringContractAtt>> getList() {
		try {
			return Result.value(engineeringContractAttDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EngineeringContractAtt>> getListByFilter(Filter filter) {
		try {
			return Result.value(engineeringContractAttDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringContractAtt.LOAD_FAILURE);
		}
	}

}
