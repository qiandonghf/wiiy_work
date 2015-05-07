package com.wiiy.engineering.service.impl;


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
import com.wiiy.engineering.activator.EngineeringActivator;
import com.wiiy.engineering.dao.EngineeringContractDao;
import com.wiiy.engineering.entity.EngineeringContract;
import com.wiiy.engineering.preferences.R;
import com.wiiy.engineering.service.EngineeringContractService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class EngineeringContractServiceImpl implements EngineeringContractService{
	
	private EngineeringContractDao engineeringContractDao;
	
	public void setEngineeringContractDao(EngineeringContractDao engineeringContractDao) {
		this.engineeringContractDao = engineeringContractDao;
	}

	@Override
	public Result<EngineeringContract> save(EngineeringContract t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = engineeringContractDao.openSession();
			tr = session.beginTransaction();
			setCreateAndModifyTime(t);
			session.save(t);
			/*Contract baseContract = new Contract();
			BeanUtil.copyProperties(t, baseContract);
			baseContract.setContractId(t.getId());
			baseContract.setId(null);
			baseContract.setDepartment(DepartmentEnum.ENGINEERING);
			session.save(baseContract);*/
			tr.commit();
			return Result.success(R.EngineeringContract.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(//
					BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EngineeringContract.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.EngineeringContract.SAVE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<EngineeringContract> delete(EngineeringContract t) {
		try {
			engineeringContractDao.delete(t);
			return Result.success(R.EngineeringContract.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringContract.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringContract> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = engineeringContractDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("DELETE FROM EngineeringContract WHERE id ="+id).executeUpdate();
			session.createQuery("DELETE FROM Contract WHERE department='ENGINEERING' and contractId ="+id).executeUpdate();
			tr.commit();
			return Result.success(R.EngineeringContract.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.EngineeringContract.DELETE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<EngineeringContract> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = engineeringContractDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("DELETE FROM EngineeringContract WHERE id in("+ids+")").executeUpdate();
			session.createQuery("DELETE FROM Contract WHERE department='ENGINEERING' and contractId in("+ids+")").executeUpdate();
			tr.commit();
			return Result.success(R.EngineeringContract.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.EngineeringContract.DELETE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<EngineeringContract> update(EngineeringContract t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = engineeringContractDao.openSession();
			tr = session.beginTransaction();
			t.setModifyTime(new Date());
			t.setModifier(EngineeringActivator.getSessionUser().getRealName());
			t.setModifierId(EngineeringActivator.getSessionUser().getId());
			session.update(t);
			/*Contract contract = (Contract) session.createQuery(//
					"From Contract WHERE department='ENGINEERING' and contractId ="+t.getId()).uniqueResult();
			Long id = contract.getId();
			BeanUtil.copyProperties(t, contract);
			contract.setId(id);
			contract.setDepartment(DepartmentEnum.ENGINEERING);
			session.update(contract);*/
			tr.commit();
			return Result.success(R.EngineeringContract.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EngineeringContract.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.EngineeringContract.UPDATE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<EngineeringContract> getBeanById(Serializable id) {
		try {
			return Result.value(engineeringContractDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EngineeringContract> getBeanByFilter(Filter filter) {
		try {
			return Result.value(engineeringContractDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EngineeringContract>> getList() {
		try {
			return Result.value(engineeringContractDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EngineeringContract>> getListByFilter(Filter filter) {
		try {
			return Result.value(engineeringContractDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<String> generateCode() {
		String code = "["+CalendarUtil.now().get(Calendar.YEAR)+"]";
		EngineeringContract contract = engineeringContractDao.getBeanByFilter(//
				new Filter(EngineeringContract.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)).//
				orderBy("id", Filter.DESC).maxResults(1));
		int count = engineeringContractDao.getRowCount(//
				new Filter(EngineeringContract.class).//
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
		entity.setCreator(EngineeringActivator.getSessionUser().getRealName());
		entity.setCreatorId(EngineeringActivator.getSessionUser().getId());
		entity.setModifyTime(entity.getCreateTime());
		entity.setModifier(entity.getCreator());
		entity.setModifierId(entity.getCreatorId());
		entity.setEntityStatus(EntityStatus.NORMAL);
	}

}
