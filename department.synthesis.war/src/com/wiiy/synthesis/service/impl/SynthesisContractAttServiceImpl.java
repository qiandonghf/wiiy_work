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
import com.wiiy.synthesis.entity.SynthesisContractAtt;
import com.wiiy.synthesis.dao.SynthesisContractAttDao;
import com.wiiy.synthesis.service.SynthesisContractAttService;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.activator.SynthesisActivator;

/**
 * @author my
 */
public class SynthesisContractAttServiceImpl implements SynthesisContractAttService{
	
	private SynthesisContractAttDao synthesisContractAttDao;
	
	public void setSynthesisContractAttDao(SynthesisContractAttDao synthesisContractAttDao) {
		this.synthesisContractAttDao = synthesisContractAttDao;
	}

	@Override
	public Result<SynthesisContractAtt> save(SynthesisContractAtt t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			synthesisContractAttDao.save(t);
			return Result.success(R.SynthesisContractAtt.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisContractAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisContractAtt.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisContractAtt> delete(SynthesisContractAtt t) {
		try {
			synthesisContractAttDao.delete(t);
			return Result.success(R.SynthesisContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisContractAtt> deleteById(Serializable id) {
		try {
			synthesisContractAttDao.deleteById(id);
			return Result.success(R.SynthesisContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisContractAtt> deleteByIds(String ids) {
		try {
			synthesisContractAttDao.deleteByIds(ids);
			return Result.success(R.SynthesisContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisContractAtt> update(SynthesisContractAtt t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			synthesisContractAttDao.update(t);
			return Result.success(R.SynthesisContractAtt.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisContractAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisContractAtt.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisContractAtt> getBeanById(Serializable id) {
		try {
			return Result.value(synthesisContractAttDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SynthesisContractAtt> getBeanByFilter(Filter filter) {
		try {
			return Result.value(synthesisContractAttDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SynthesisContractAtt>> getList() {
		try {
			return Result.value(synthesisContractAttDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SynthesisContractAtt>> getListByFilter(Filter filter) {
		try {
			return Result.value(synthesisContractAttDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisContractAtt.LOAD_FAILURE);
		}
	}

}
