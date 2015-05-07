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
import com.wiiy.synthesis.entity.LaborContractAtt;
import com.wiiy.synthesis.dao.LaborContractAttDao;
import com.wiiy.synthesis.service.LaborContractAttService;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.activator.SynthesisActivator;

/**
 * @author my
 */
public class LaborContractAttServiceImpl implements LaborContractAttService{
	
	private LaborContractAttDao laborContractAttDao;
	
	public void setLaborContractAttDao(LaborContractAttDao laborContractAttDao) {
		this.laborContractAttDao = laborContractAttDao;
	}

	@Override
	public Result<LaborContractAtt> save(LaborContractAtt t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			laborContractAttDao.save(t);
			return Result.success(R.LaborContractAtt.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),LaborContractAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.LaborContractAtt.SAVE_FAILURE);
		}
	}

	@Override
	public Result<LaborContractAtt> delete(LaborContractAtt t) {
		try {
			laborContractAttDao.delete(t);
			return Result.success(R.LaborContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.LaborContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<LaborContractAtt> deleteById(Serializable id) {
		try {
			laborContractAttDao.deleteById(id);
			return Result.success(R.LaborContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.LaborContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<LaborContractAtt> deleteByIds(String ids) {
		try {
			laborContractAttDao.deleteByIds(ids);
			return Result.success(R.LaborContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.LaborContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<LaborContractAtt> update(LaborContractAtt t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			laborContractAttDao.update(t);
			return Result.success(R.LaborContractAtt.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),LaborContractAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.LaborContractAtt.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<LaborContractAtt> getBeanById(Serializable id) {
		try {
			return Result.value(laborContractAttDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.LaborContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<LaborContractAtt> getBeanByFilter(Filter filter) {
		try {
			return Result.value(laborContractAttDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.LaborContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<LaborContractAtt>> getList() {
		try {
			return Result.value(laborContractAttDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.LaborContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<LaborContractAtt>> getListByFilter(Filter filter) {
		try {
			return Result.value(laborContractAttDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.LaborContractAtt.LOAD_FAILURE);
		}
	}

}
