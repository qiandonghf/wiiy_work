package com.wiiy.sale.service.impl;


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
import com.wiiy.sale.activator.SaleActivator;
import com.wiiy.sale.dao.SaleContractDao;
import com.wiiy.sale.entity.SaleContract;
import com.wiiy.sale.preferences.R;
import com.wiiy.sale.service.SaleContractService;

/**
 * @author my
 */
public class SaleContractServiceImpl implements SaleContractService{
	
	private SaleContractDao saleContractDao;
	
	public void setSaleContractDao(SaleContractDao saleContractDao) {
		this.saleContractDao = saleContractDao;
	}

	@Override
	public Result<SaleContract> save(SaleContract t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = saleContractDao.openSession();
			tr = session.beginTransaction();
			setCreateAndModifyTime(t);
			session.save(t);
			/*Contract baseContract = new Contract();
			BeanUtil.copyProperties(t,baseContract);
			baseContract.setContractId(t.getId());
			baseContract.setId(null);
			baseContract.setDepartment(DepartmentEnum.SALE);
			session.save(baseContract);*/
			tr.commit();
			return Result.success(R.SaleContract.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleContract.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SaleContract.SAVE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<SaleContract> delete(SaleContract t) {
		try {
			saleContractDao.delete(t);
			return Result.success(R.SaleContract.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleContract.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleContract> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = saleContractDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("DELETE FROM SaleContract WHERE id ="+id).executeUpdate();
			session.createQuery("DELETE FROM Contract WHERE department='SALE' and contractId ="+id).executeUpdate();
			tr.commit();
			return Result.success(R.SaleContract.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SaleContract.DELETE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<SaleContract> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = saleContractDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("DELETE FROM SaleContract WHERE id in("+ids+")").executeUpdate();
			session.createQuery("DELETE FROM Contract WHERE department='SALE' and contractId in("+ids+")").executeUpdate();
			tr.commit();
			return Result.success(R.SaleContract.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SaleContract.DELETE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<SaleContract> update(SaleContract t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = saleContractDao.openSession();
			tr = session.beginTransaction();
			t.setModifyTime(new Date());
			t.setModifier(SaleActivator.getSessionUser().getRealName());
			t.setModifierId(SaleActivator.getSessionUser().getId());
			session.update(t);
			/*Contract contract = (Contract) session.createQuery(//
					"From Contract WHERE department='SALE' and contractId ="+t.getId()).uniqueResult();
			Long id = contract.getId();
			BeanUtil.copyProperties(t, contract);
			contract.setId(id);
			contract.setDepartment(DepartmentEnum.SALE);
			session.update(contract);*/
			tr.commit();
			return Result.success(R.SaleContract.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleContract.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SaleContract.UPDATE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<SaleContract> getBeanById(Serializable id) {
		try {
			return Result.value(saleContractDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SaleContract> getBeanByFilter(Filter filter) {
		try {
			return Result.value(saleContractDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleContract>> getList() {
		try {
			return Result.value(saleContractDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleContract>> getListByFilter(Filter filter) {
		try {
			return Result.value(saleContractDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleContract.LOAD_FAILURE);
		}
	}

	@Override
	public Result<String> generateCode() {
		String code = "["+CalendarUtil.now().get(Calendar.YEAR)+"]";
		SaleContract contract = saleContractDao.getBeanByFilter(//
				new Filter(SaleContract.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)).//
				orderBy("id", Filter.DESC).maxResults(1));
		int count = saleContractDao.getRowCount(//
				new Filter(SaleContract.class).//
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
		entity.setCreator(SaleActivator.getSessionUser().getRealName());
		entity.setCreatorId(SaleActivator.getSessionUser().getId());
		entity.setModifyTime(entity.getCreateTime());
		entity.setModifier(entity.getCreator());
		entity.setModifierId(entity.getCreatorId());
		entity.setEntityStatus(EntityStatus.NORMAL);
	}

}
