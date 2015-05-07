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
import com.wiiy.synthesis.dao.SynthesisCustomerDao;
import com.wiiy.synthesis.entity.SynthesisCustomer;
import com.wiiy.synthesis.entity.SynthesisCustomerInfo;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.service.SynthesisCustomerService;

/**
 * @author my
 */
public class SynthesisCustomerServiceImpl implements SynthesisCustomerService{
	
	private SynthesisCustomerDao synthesisCustomerDao;
	
	public void setSynthesisCustomerDao(SynthesisCustomerDao synthesisCustomerDao) {
		this.synthesisCustomerDao = synthesisCustomerDao;
	}

	@Override
	public Result<SynthesisCustomer> save(SynthesisCustomer t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			synthesisCustomerDao.save(t);
			return Result.success(R.SynthesisCustomer.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisCustomer.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomer.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisCustomer> delete(SynthesisCustomer t) {
		try {
			synthesisCustomerDao.delete(t);
			return Result.success(R.SynthesisCustomer.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomer.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisCustomer> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = synthesisCustomerDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("DELETE FROM SynthesisCustomer WHERE id ="+id).executeUpdate();
			session.createQuery("DELETE FROM SynthesisCustomerInfo WHERE id ="+id).executeUpdate();
			session.createQuery("DELETE FROM Customer WHERE department='SYNTHESIS' and customerId ="+id).executeUpdate();
			tr.commit();
			return Result.success(R.SynthesisCustomer.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomer.DELETE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<SynthesisCustomer> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = synthesisCustomerDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("DELETE FROM SynthesisCustomer WHERE id in("+ids+")").executeUpdate();
			session.createQuery("DELETE FROM SynthesisCustomerInfo WHERE id in("+ids+")").executeUpdate();
			session.createQuery("DELETE FROM Customer WHERE department='SYNTHESIS' and customerId in("+ids+")").executeUpdate();
			tr.commit();
			return Result.success(R.SynthesisCustomer.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomer.DELETE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<SynthesisCustomer> update(SynthesisCustomer t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			synthesisCustomerDao.update(t);
			return Result.success(R.SynthesisCustomer.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisCustomer.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomer.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisCustomer> getBeanById(Serializable id) {
		try {
			return Result.value(synthesisCustomerDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomer.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SynthesisCustomer> getBeanByFilter(Filter filter) {
		try {
			return Result.value(synthesisCustomerDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomer.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SynthesisCustomer>> getList() {
		try {
			return Result.value(synthesisCustomerDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomer.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SynthesisCustomer>> getListByFilter(Filter filter) {
		try {
			return Result.value(synthesisCustomerDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomer.LOAD_FAILURE);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result save(SynthesisCustomer customer,
			SynthesisCustomerInfo customerInfo) {
		Session session = null;
		Transaction tr = null;
		try {
			session = synthesisCustomerDao.openSession();
			tr = session.beginTransaction();
			setCreateAndModifyTime(customerInfo);
			session.save(customerInfo);
			setCreateAndModifyTime(customer);
			customer.setCustomerInfo(customerInfo);
			session.save(customer);
			/*Customer baseCustomer = new Customer();
			BeanUtil.copyProperties(customer, baseCustomer);
			baseCustomer.setDepartment(DepartmentEnum.SYNTHESIS);
			baseCustomer.setCustomerId(customer.getId());
			baseCustomer.setId(null);
			session.save(baseCustomer);*/
			tr.commit();
			return Result.success(R.SynthesisCustomer.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisCustomer.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomer.SAVE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<String> generateCode() {
		String code = "["+CalendarUtil.now().get(Calendar.YEAR)+"]";
		SynthesisCustomer customer = synthesisCustomerDao.getBeanByFilter(//
				new Filter(SynthesisCustomer.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)).//
				orderBy("id", Filter.DESC).maxResults(1));
		int count = synthesisCustomerDao.getRowCount(//
				new Filter(SynthesisCustomer.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)));
		try {
			String oldCode = customer.getCode();
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
	
	private void setModifyTime(BaseEntity entity) {
		entity.setModifyTime(entity.getCreateTime());
		entity.setModifier(entity.getCreator());
		entity.setModifierId(entity.getCreatorId());
		entity.setEntityStatus(EntityStatus.NORMAL);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result update(SynthesisCustomer customer,
			SynthesisCustomerInfo customerInfo) {
		Session session = null;
		Transaction tr = null;
		try {
			session = synthesisCustomerDao.openSession();
			tr = session.beginTransaction();
			setModifyTime(customerInfo);
			session.update(customerInfo);
			setModifyTime(customer);
			customer.setCustomerInfo(customerInfo);
			session.update(customer);
			/*Customer baseCustomer = (Customer) session.createQuery(//
					"From Customer WHERE department='SYNTHESIS' and customerId ="+customer.getId()).uniqueResult();
			Long id = customer.getId();
			BeanUtil.copyProperties(customer, baseCustomer);
			baseCustomer.setId(id);
			baseCustomer.setDepartment(DepartmentEnum.SYNTHESIS);
			session.update(baseCustomer);*/
			tr.commit();
			return Result.success(R.SynthesisCustomer.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisCustomer.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SynthesisCustomer.SAVE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
