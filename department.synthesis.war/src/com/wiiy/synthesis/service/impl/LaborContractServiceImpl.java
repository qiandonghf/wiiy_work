package com.wiiy.synthesis.service.impl;


import java.io.Serializable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.core.entity.User;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.synthesis.entity.Appraisal;
import com.wiiy.synthesis.entity.AppraisalAtt;
import com.wiiy.synthesis.entity.LaborContract;
import com.wiiy.synthesis.entity.LaborContractAtt;
import com.wiiy.synthesis.dao.LaborContractDao;
import com.wiiy.synthesis.service.LaborContractService;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.activator.SynthesisActivator;

/**
 * @author my
 */
public class LaborContractServiceImpl implements LaborContractService{
	
	private LaborContractDao laborContractDao;
	
	public void setLaborContractDao(LaborContractDao laborContractDao) {
		this.laborContractDao = laborContractDao;
	}
	private void setCreatorModifier(BaseEntity entity){
		entity.setCreateTime(new Date());
		entity.setCreatorId(SynthesisActivator.getSessionUser().getId());
		entity.setCreator(SynthesisActivator.getSessionUser().getRealName());
		entity.setModifyTime(entity.getCreateTime());
		entity.setModifier(entity.getCreator());
		entity.setModifierId(entity.getCreatorId());
		entity.setEntityStatus(EntityStatus.NORMAL);
	}
	@Override
	public Result<LaborContract> save(LaborContract t, List<LaborContractAtt> atts) {
		Session session= null;
		Transaction tr = null;
		try{
			session= laborContractDao.openSession();
			tr = session.beginTransaction();
			if(t.getUserId()!=null){
				setCreatorModifier(t);
				session.save(t);
				if(t.getId()!=null){
					long id = t.getId();
					for(LaborContractAtt att : atts){
						att.setLaborContractId(id);
						setCreatorModifier(att);
						session.save(att);
					}
				}
			}
			tr.commit();
			return Result.success(R.LaborContract.SAVE_SUCCESS,t);
		}catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),LaborContract.class)));
		}catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.LaborContract.SAVE_FAILURE);
		}finally{
			session.close();
		}
	}
	@Override
	public Result<LaborContract> save(LaborContract t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			laborContractDao.save(t);
			return Result.success(R.LaborContract.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),LaborContract.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.LaborContract.SAVE_FAILURE);
		}
	}

	@Override
	public Result<LaborContract> delete(LaborContract t) {
		try {
			laborContractDao.delete(t);
			return Result.success(R.LaborContract.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.LaborContract.DELETE_FAILURE);
		}
	}

	@Override
	public Result<LaborContract> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = laborContractDao.openSession();
			tr = session.beginTransaction();
			List<LaborContractAtt> laborcontractAtts = (List<LaborContractAtt>)session.createQuery("from LaborContractAtt where laborcontractId = "+id).list();
			for (LaborContractAtt laborContractAtt : laborcontractAtts) {
				String path = laborContractAtt.getNewName();
				session.delete(laborContractAtt);
				SynthesisActivator.getResourcesService().delete(path);
			}
			LaborContract a = (LaborContract)session.createQuery("from LaborContract where id = "+id).list().get(0);
			session.delete(a);
			tr.commit();
			return Result.success(R.LaborContract.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.LaborContract.DELETE_FAILURE);
		}finally{
			session.close();
		}
	}
	@Override
	public Result<LaborContract> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null; 
		try {
			session = laborContractDao.openSession();
			tr = session.beginTransaction();
			List<LaborContractAtt> laborContractAtts = (List<LaborContractAtt>)session.createQuery("from LaborContractAtt where laborContractId in ("+ids+")").list();
			for (LaborContractAtt laborcontractAtt : laborContractAtts) {
				String path = laborcontractAtt.getNewName();
				session.delete(laborcontractAtt);
				SynthesisActivator.getResourcesService().delete(path);
			}
			List<LaborContract> t = session.createQuery("from LaborContract where id in ("+ids+")").list();
			for (LaborContract laborContract : t) {
				session.delete(laborContract);
			}
			tr.commit();
			return Result.success(R.LaborContract.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.LaborContract.DELETE_FAILURE);
		}finally{
			session.close();
		}
	}

	@Override
	public Result<LaborContract> update(LaborContract t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			laborContractDao.update(t);
			return Result.success(R.LaborContract.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),LaborContract.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.LaborContract.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<LaborContract> getBeanById(Serializable id) {
		try {
			return Result.value(laborContractDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.LaborContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<LaborContract> getBeanByFilter(Filter filter) {
		try {
			return Result.value(laborContractDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.LaborContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<LaborContract>> getList() {
		try {
			return Result.value(laborContractDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.LaborContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<LaborContract>> getListByFilter(Filter filter) {
		try {
			return Result.value(laborContractDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.LaborContract.LOAD_FAILURE);
		}
	}

}
