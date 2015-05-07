package com.wiiy.estate.service.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.estate.dao.ContractAgreementDao;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.entity.EstateContractAgreement;
import com.wiiy.estate.entity.EstateContractAgreementAtt;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.service.ContractAgreementService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
/**
 * @author my
 */
public class ContractAgreementServiceImpl implements ContractAgreementService{
	
	private ContractAgreementDao contractAgreementDao;

	public void setEstateContractAgreementDao(ContractAgreementDao contractAgreementDao) {
		this.contractAgreementDao = contractAgreementDao;
	}

	@Override
	public Result<EstateContractAgreement> save(EstateContractAgreement t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			contractAgreementDao.save(t);
			return Result.success(R.EstateContractAgreement.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateContractAgreement.class)));
		} catch (Exception e) {
			return Result.failure(R.EstateContractAgreement.SAVE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractAgreement> delete(EstateContractAgreement t) {
		try {
			contractAgreementDao.delete(t);
			return Result.success(R.EstateContractAgreement.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateContractAgreement.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractAgreement> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = contractAgreementDao.openSession();
			tr = session.beginTransaction();
			EstateContractAgreement c = getBeanById(id).getValue();
			if(c.getAtts()!=null&&c.getAtts().size()>0){
				List<EstateContractAgreementAtt> list = new ArrayList<EstateContractAgreementAtt>(c.getAtts());
				for(EstateContractAgreementAtt att : list){
					session.delete(att);
				}
			}
			session.delete(c);
			tr.commit();
			return Result.success(R.EstateContractAgreement.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateContractAgreement.DELETE_FAILURE);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result<EstateContractAgreement> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = contractAgreementDao.openSession();
			tr = session.beginTransaction();
			List<EstateContractAgreement> list = session.createQuery(
					"from EstateContractAgreement where id in (" + ids+")").list();
			for (EstateContractAgreement c : list) {
				if(c.getAtts()!=null&&c.getAtts().size()>0){
					List<EstateContractAgreementAtt> atts = new ArrayList<EstateContractAgreementAtt>(c.getAtts());
					for(EstateContractAgreementAtt att : atts){
						session.delete(att);
					}
				}
				session.delete(c);
			}
			tr.commit();
			return Result.success(R.EstateContractAgreement.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.EstateContractAgreement.DELETE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<EstateContractAgreement> update(EstateContractAgreement t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			contractAgreementDao.update(t);
			return Result.success(R.EstateContractAgreement.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateContractAgreement.class)));
		} catch (Exception e) {
			return Result.failure(R.EstateContractAgreement.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractAgreement> getBeanById(Serializable id) {
		try {
			return Result.value(contractAgreementDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.EstateContractAgreement.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EstateContractAgreement> getBeanByFilter(Filter filter) {
		try {
			return Result.value(contractAgreementDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.EstateContractAgreement.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateContractAgreement>> getList() {
		try {
			return Result.value(contractAgreementDao.getList());
		} catch (Exception e) {
			return Result.failure(R.EstateContractAgreement.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateContractAgreement>> getListByFilter(Filter filter) {
		try {
			return Result.value(contractAgreementDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.EstateContractAgreement.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EstateContractAgreement> save(EstateContractAgreement t,List<EstateContractAgreementAtt> sessionEstateContractAgreementAttList) {
		Session session = null;
		Transaction tr = null;
		try {
			session = contractAgreementDao.openSession();
			tr = session.beginTransaction();
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			session.save(t);
			if(sessionEstateContractAgreementAttList!=null){
				for (EstateContractAgreementAtt contractAgreementAtt : sessionEstateContractAgreementAttList) {
					contractAgreementAtt.setContractAgreementId(t.getId());
					contractAgreementAtt.setCreateTime(new Date());
					contractAgreementAtt.setCreator(EstateActivator.getSessionUser().getRealName());
					contractAgreementAtt.setCreatorId(EstateActivator.getSessionUser().getId());
					contractAgreementAtt.setModifyTime(t.getCreateTime());
					contractAgreementAtt.setModifier(t.getCreator());
					contractAgreementAtt.setModifierId(t.getCreatorId());
					contractAgreementAtt.setEntityStatus(EntityStatus.NORMAL);
					session.save(contractAgreementAtt);
				}
			}
			tr.commit();
			return Result.success(R.EstateContractAgreement.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateContractAgreement.class)));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.EstateContractAgreement.SAVE_FAILURE);
		}finally{
			session.close();
		}
	}

}
