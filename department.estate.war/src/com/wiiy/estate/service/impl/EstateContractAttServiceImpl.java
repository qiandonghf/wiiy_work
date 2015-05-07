package com.wiiy.estate.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dao.EstateContractAttDao;
import com.wiiy.estate.entity.EstateContractAtt;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.service.EstateContractAttService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class EstateContractAttServiceImpl implements EstateContractAttService{
	
	private EstateContractAttDao estateContractAttDao;
	
	public void setEstateContractAttDao(EstateContractAttDao estateContractAttDao) {
		this.estateContractAttDao = estateContractAttDao;
	}

	@Override
	public Result<EstateContractAtt> save(EstateContractAtt t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			estateContractAttDao.save(t);
			return Result.success(R.EstateContractAtt.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateContractAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateContractAtt.SAVE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractAtt> delete(EstateContractAtt t) {
		try {
			estateContractAttDao.delete(t);
			return Result.success(R.EstateContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractAtt> deleteById(Serializable id) {
		try {
			estateContractAttDao.deleteById(id);
			return Result.success(R.EstateContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractAtt> deleteByIds(String ids) {
		try {
			estateContractAttDao.deleteByIds(ids);
			return Result.success(R.EstateContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractAtt> update(EstateContractAtt t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			estateContractAttDao.update(t);
			return Result.success(R.EstateContractAtt.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateContractAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateContractAtt.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractAtt> getBeanById(Serializable id) {
		try {
			return Result.value(estateContractAttDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EstateContractAtt> getBeanByFilter(Filter filter) {
		try {
			return Result.value(estateContractAttDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateContractAtt>> getList() {
		try {
			return Result.value(estateContractAttDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateContractAtt>> getListByFilter(Filter filter) {
		try {
			return Result.value(estateContractAttDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateContractAtt.LOAD_FAILURE);
		}
	}

}
