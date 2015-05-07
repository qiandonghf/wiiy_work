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
import com.wiiy.business.entity.InvestmentArchiveAtt;
import com.wiiy.business.entity.InvestmentContractAtt;
import com.wiiy.business.dao.InvestmentContractAttDao;
import com.wiiy.business.service.InvestmentContractAttService;
import com.wiiy.business.preferences.R;
import com.wiiy.business.activator.BusinessActivator;

/**
 * @author my
 */
public class InvestmentContractAttServiceImpl implements InvestmentContractAttService{
	
	private InvestmentContractAttDao investmentContractAttDao;
	
	public void setInvestmentContractAttDao(InvestmentContractAttDao investmentContractAttDao) {
		this.investmentContractAttDao = investmentContractAttDao;
	}

	@Override
	public Result<InvestmentContractAtt> save(InvestmentContractAtt t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			investmentContractAttDao.save(t);
			return Result.success(R.InvestmentContractAtt.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentContractAtt.class)));
		} catch (Exception e) {
			return Result.failure(R.InvestmentContractAtt.SAVE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentContractAtt> delete(InvestmentContractAtt t) {
		try {
			investmentContractAttDao.delete(t);
			return Result.success(R.InvestmentContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.InvestmentContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentContractAtt> deleteById(Serializable id) {
		try {
			InvestmentContractAtt att = (InvestmentContractAtt) investmentContractAttDao.getBeanById(id);
			String path = att.getNewName();
			BusinessActivator.getResourcesService().delete(path);
			investmentContractAttDao.deleteById(id);
			return Result.success(R.InvestmentContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.InvestmentContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentContractAtt> deleteByIds(String ids) {
		try {
			investmentContractAttDao.deleteByIds(ids);
			return Result.success(R.InvestmentContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.InvestmentContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentContractAtt> update(InvestmentContractAtt t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			investmentContractAttDao.update(t);
			return Result.success(R.InvestmentContractAtt.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentContractAtt.class)));
		} catch (Exception e) {
			return Result.failure(R.InvestmentContractAtt.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentContractAtt> getBeanById(Serializable id) {
		try {
			return Result.value(investmentContractAttDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.InvestmentContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<InvestmentContractAtt> getBeanByFilter(Filter filter) {
		try {
			return Result.value(investmentContractAttDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.InvestmentContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<InvestmentContractAtt>> getList() {
		try {
			return Result.value(investmentContractAttDao.getList());
		} catch (Exception e) {
			return Result.failure(R.InvestmentContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<InvestmentContractAtt>> getListByFilter(Filter filter) {
		try {
			return Result.value(investmentContractAttDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.InvestmentContractAtt.LOAD_FAILURE);
		}
	}

}
