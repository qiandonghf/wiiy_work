package com.wiiy.estate.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dao.ContractAgreementAttDao;
import com.wiiy.estate.entity.EstateContractAgreementAtt;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.service.ContractAgreementAttService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
/**
 * @author my
 */
public class ContractAgreementAttServiceImpl implements ContractAgreementAttService{
	
	private ContractAgreementAttDao contractAgreementAttDao;
	
	public void setContractAgreementAttDao(ContractAgreementAttDao contractAgreementAttDao) {
		this.contractAgreementAttDao = contractAgreementAttDao;
	}

	@Override
	public Result<EstateContractAgreementAtt> save(EstateContractAgreementAtt t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			contractAgreementAttDao.save(t);
			return Result.success(R.EstateContractAgreementAtt.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateContractAgreementAtt.class)));
		} catch (Exception e) {
			return Result.failure(R.EstateContractAgreementAtt.SAVE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractAgreementAtt> delete(EstateContractAgreementAtt t) {
		try {
			contractAgreementAttDao.delete(t);
			EstateActivator.getResourcesService().delete(t.getNewName());
			return Result.success(R.EstateContractAgreementAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateContractAgreementAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractAgreementAtt> deleteById(Serializable id) {
		try {
			EstateContractAgreementAtt t = getBeanById(id).getValue();
			String path = t.getNewName();
			contractAgreementAttDao.delete(t);
			EstateActivator.getResourcesService().delete(path);
			return Result.success(R.EstateContractAgreementAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateContractAgreementAtt.DELETE_FAILURE);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result<EstateContractAgreementAtt> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = contractAgreementAttDao.openSession();
			tr = session.beginTransaction();
			List<EstateContractAgreementAtt> list = session.createQuery(
					"from ContractAgreementAtt where id in (" + ids+")").list();
			for (EstateContractAgreementAtt Att : list) {
				String path = Att.getNewName();
				EstateActivator.getResourcesService().delete(path);
				session.delete(Att);
			}
			tr.commit();
			return Result.success(R.EstateContractAgreementAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.EstateContractAgreementAtt.DELETE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<EstateContractAgreementAtt> update(EstateContractAgreementAtt t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			contractAgreementAttDao.update(t);
			return Result.success(R.EstateContractAgreementAtt.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateContractAgreementAtt.class)));
		} catch (Exception e) {
			return Result.failure(R.EstateContractAgreementAtt.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractAgreementAtt> getBeanById(Serializable id) {
		try {
			return Result.value(contractAgreementAttDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.EstateContractAgreementAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EstateContractAgreementAtt> getBeanByFilter(Filter filter) {
		try {
			return Result.value(contractAgreementAttDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.EstateContractAgreementAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateContractAgreementAtt>> getList() {
		try {
			return Result.value(contractAgreementAttDao.getList());
		} catch (Exception e) {
			return Result.failure(R.EstateContractAgreementAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateContractAgreementAtt>> getListByFilter(Filter filter) {
		try {
			return Result.value(contractAgreementAttDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.EstateContractAgreementAtt.LOAD_FAILURE);
		}
	}

}
