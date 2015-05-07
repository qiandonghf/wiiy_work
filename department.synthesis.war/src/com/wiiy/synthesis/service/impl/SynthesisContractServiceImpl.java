package com.wiiy.synthesis.service.impl;


import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.synthesis.activator.SynthesisActivator;
import com.wiiy.synthesis.dao.SynthesisContractDao;
import com.wiiy.synthesis.entity.SynthesisContract;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.service.SynthesisContractService;

/**
 * @author my
 */
public class SynthesisContractServiceImpl implements SynthesisContractService{
	
	private SynthesisContractDao synthesisContractDao;
	
	public void setSynthesisContractDao(SynthesisContractDao synthesisContractDao) {
		this.synthesisContractDao = synthesisContractDao;
	}

	@Override
	public Result<SynthesisContract> save(SynthesisContract t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = synthesisContractDao.openSession();
			tr = session.beginTransaction();
			setCreateAndModifyTime(t);
			session.save(t);
			/*Contract baseContract = new Contract();
			BeanUtil.copyProperties(t,baseContract);
			baseContract.setContractId(t.getId());
			baseContract.setId(null);
			baseContract.setDepartment(DepartmentEnum.SYNTHESIS);
			session.save(baseContract);*/
			tr.commit();
			return Result.success(R.SynthesisContract.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisContract.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisContract.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisContract> delete(SynthesisContract t) {
		try {
			synthesisContractDao.delete(t);
			return Result.success(R.SynthesisContract.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisContract.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisContract> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = synthesisContractDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("DELETE FROM SynthesisContract WHERE id ="+id).executeUpdate();
			session.createQuery("DELETE FROM Contract WHERE department='SYNTHESIS' and contractId ="+id).executeUpdate();
			tr.commit();
			return Result.success(R.SynthesisContract.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SynthesisContract.DELETE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<SynthesisContract> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = synthesisContractDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("DELETE FROM SynthesisContract WHERE id in("+ids+")").executeUpdate();
			session.createQuery("DELETE FROM Contract WHERE department='SYNTHESIS' and contractId in("+ids+")").executeUpdate();
			tr.commit();
			return Result.success(R.SynthesisContract.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SynthesisContract.DELETE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<SynthesisContract> update(SynthesisContract t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = synthesisContractDao.openSession();
			tr = session.beginTransaction();
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			session.update(t);
			/*Contract contract = (Contract) session.createQuery(//
					"From Contract WHERE department='SYNTHESIS' and contractId ="+t.getId()).uniqueResult();
			Long id = contract.getId();
			BeanUtil.copyProperties(t, contract);
			contract.setId(id);
			contract.setDepartment(DepartmentEnum.SYNTHESIS);
			session.update(contract);*/
			tr.commit();
			return Result.success(R.SynthesisContract.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisContract.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisContract.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisContract> getBeanById(Serializable id) {
		try {
			return Result.value(synthesisContractDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SynthesisContract> getBeanByFilter(Filter filter) {
		try {
			return Result.value(synthesisContractDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SynthesisContract>> getList() {
		try {
			return Result.value(synthesisContractDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SynthesisContract>> getListByFilter(Filter filter) {
		try {
			return Result.value(synthesisContractDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisContract.LOAD_FAILURE);
		}
	}
	
	@Override
	public Result<String> generateCode() {
		String code = "["+CalendarUtil.now().get(Calendar.YEAR)+"]";
		SynthesisContract contract = synthesisContractDao.getBeanByFilter(//
				new Filter(SynthesisContract.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)).//
				orderBy("id", Filter.DESC).maxResults(1));
		int count = synthesisContractDao.getRowCount(//
				new Filter(SynthesisContract.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)));
		try {
			String oldCode = contract.getCode();
			count = Integer.parseInt(//
					oldCode.replace("["+CalendarUtil.now().get(Calendar.YEAR)+"]", ""));
		} catch (Exception e) {
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		nf.setMinimumIntegerDigits(6);
		code = code + nf.format(count+1);
		return Result.value(code);
	}

	private void setCreateAndModifyTime(BaseEntity entity) {
		entity.setCreateTime(new Date());
		entity.setCreator(SynthesisActivator.getSessionUser().getRealName());
		entity.setCreatorId(SynthesisActivator.getSessionUser().getId());
		entity.setModifyTime(entity.getCreateTime());
		entity.setModifier(entity.getCreator());
		entity.setModifierId(entity.getCreatorId());
		entity.setEntityStatus(EntityStatus.NORMAL);
	}

	@Override
	public List<Object> getListBySql(String sql) {
		
		return synthesisContractDao.getObjectListBySql(sql);
	}

}
