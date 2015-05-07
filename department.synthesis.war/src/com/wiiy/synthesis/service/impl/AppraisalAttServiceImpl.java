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
import com.wiiy.synthesis.entity.AppraisalAtt;
import com.wiiy.synthesis.dao.AppraisalAttDao;
import com.wiiy.synthesis.service.AppraisalAttService;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.activator.SynthesisActivator;

/**
 * @author my
 */
public class AppraisalAttServiceImpl implements AppraisalAttService{
	
	private AppraisalAttDao appraisalAttDao;
	
	public void setAppraisalAttDao(AppraisalAttDao appraisalAttDao) {
		this.appraisalAttDao = appraisalAttDao;
	}

	@Override
	public Result<AppraisalAtt> save(AppraisalAtt t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			appraisalAttDao.save(t);
			return Result.success(R.AppraisalAtt.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),AppraisalAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.AppraisalAtt.SAVE_FAILURE);
		}
	}

	@Override
	public Result<AppraisalAtt> delete(AppraisalAtt t) {
		try {
			appraisalAttDao.delete(t);
			return Result.success(R.AppraisalAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.AppraisalAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<AppraisalAtt> deleteById(Serializable id) {
		try {
			appraisalAttDao.deleteById(id);
			return Result.success(R.AppraisalAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.AppraisalAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<AppraisalAtt> deleteByIds(String ids) {
		try {
			appraisalAttDao.deleteByIds(ids);
			return Result.success(R.AppraisalAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.AppraisalAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<AppraisalAtt> update(AppraisalAtt t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			appraisalAttDao.update(t);
			return Result.success(R.AppraisalAtt.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),AppraisalAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.AppraisalAtt.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<AppraisalAtt> getBeanById(Serializable id) {
		try {
			return Result.value(appraisalAttDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.AppraisalAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<AppraisalAtt> getBeanByFilter(Filter filter) {
		try {
			return Result.value(appraisalAttDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.AppraisalAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<AppraisalAtt>> getList() {
		try {
			return Result.value(appraisalAttDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.AppraisalAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<AppraisalAtt>> getListByFilter(Filter filter) {
		try {
			return Result.value(appraisalAttDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.AppraisalAtt.LOAD_FAILURE);
		}
	}

}
