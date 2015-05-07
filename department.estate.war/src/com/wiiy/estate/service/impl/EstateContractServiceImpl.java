package com.wiiy.estate.service.impl;


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
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dao.EstateContractDao;
import com.wiiy.estate.entity.EstateContract;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.service.EstateContractService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class EstateContractServiceImpl implements EstateContractService{
	
	private EstateContractDao estateContractDao;
	
	public void setEstateContractDao(EstateContractDao estateContractDao) {
		this.estateContractDao = estateContractDao;
	}

	@Override
	public Result<EstateContract> save(EstateContract t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = estateContractDao.openSession();
			tr = session.beginTransaction();
			setCreateAndModifyTime(t);
			session.save(t);
			/*Contract baseContract = new Contract();
			BeanUtil.copyProperties(t, baseContract);
			baseContract.setContractId(t.getId());
			baseContract.setId(null);
			baseContract.setDepartment(DepartmentEnum.ESTATE);
			session.save(baseContract);*/
			tr.commit();
			return Result.success(R.EstateContract.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(//
					BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateContract.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.EstateContract.SAVE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<EstateContract> delete(EstateContract t) {
		try {
			estateContractDao.delete(t);
			return Result.success(R.EstateContract.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateContract.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateContract> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = estateContractDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("DELETE FROM EstateContract WHERE id ="+id).executeUpdate();
			session.createQuery("DELETE FROM Contract WHERE department='ENGINEERING' and contractId ="+id).executeUpdate();
			tr.commit();
			return Result.success(R.EstateContract.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.EstateContract.DELETE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<EstateContract> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = estateContractDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("DELETE FROM EstateContract WHERE id in("+ids+")").executeUpdate();
			session.createQuery("DELETE FROM Contract WHERE department='ENGINEERING' and contractId in("+ids+")").executeUpdate();
			tr.commit();
			return Result.success(R.EstateContract.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.EstateContract.DELETE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<EstateContract> update(EstateContract t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = estateContractDao.openSession();
			tr = session.beginTransaction();
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			session.update(t);
			/*Contract contract = (Contract) session.get(Contract.class, t.getId());
			Long id = contract.getId();
			BeanUtil.copyProperties(t, contract);
			contract.setId(id);
			contract.setDepartment(DepartmentEnum.ENGINEERING);
			session.update(contract);*/
			tr.commit();
			return Result.success(R.EstateContract.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateContract.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.EstateContract.UPDATE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<EstateContract> getBeanById(Serializable id) {
		try {
			return Result.value(estateContractDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EstateContract> getBeanByFilter(Filter filter) {
		try {
			return Result.value(estateContractDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateContract>> getList() {
		try {
			return Result.value(estateContractDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateContract>> getListByFilter(Filter filter) {
		try {
			return Result.value(estateContractDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<String> generateCode() {
		String code = "["+CalendarUtil.now().get(Calendar.YEAR)+"]";
		EstateContract contract = estateContractDao.getBeanByFilter(//
				new Filter(EstateContract.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)).//
				orderBy("id", Filter.DESC).maxResults(1));
		int count = estateContractDao.getRowCount(//
				new Filter(EstateContract.class).//
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
		entity.setCreator(EstateActivator.getSessionUser().getRealName());
		entity.setCreatorId(EstateActivator.getSessionUser().getId());
		entity.setModifyTime(entity.getCreateTime());
		entity.setModifier(entity.getCreator());
		entity.setModifierId(entity.getCreatorId());
		entity.setEntityStatus(EntityStatus.NORMAL);
	}

}
