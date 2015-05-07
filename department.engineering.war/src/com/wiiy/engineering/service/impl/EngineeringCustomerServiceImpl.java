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
import com.wiiy.engineering.dao.EngineeringCustomerDao;
import com.wiiy.engineering.entity.EngineeringCustomer;
import com.wiiy.engineering.entity.EngineeringCustomerInfo;
import com.wiiy.engineering.preferences.R;
import com.wiiy.engineering.service.EngineeringCustomerService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public class EngineeringCustomerServiceImpl implements EngineeringCustomerService{
	
	private EngineeringCustomerDao engineeringCustomerDao;
	
	public void setEngineeringCustomerDao(EngineeringCustomerDao engineeringCustomerDao) {
		this.engineeringCustomerDao = engineeringCustomerDao;
	}

	@Override
	public Result<EngineeringCustomer> save(EngineeringCustomer t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EngineeringActivator.getSessionUser().getRealName());
			t.setCreatorId(EngineeringActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			engineeringCustomerDao.save(t);
			return Result.success(R.EngineeringCustomer.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EngineeringCustomer.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringCustomer.SAVE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringCustomer> delete(EngineeringCustomer t) {
		try {
			engineeringCustomerDao.delete(t);
			return Result.success(R.EngineeringCustomer.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringCustomer.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringCustomer> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = engineeringCustomerDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("DELETE FROM EngineeringCustomer WHERE id ="+id).executeUpdate();
			session.createQuery("DELETE FROM EngineeringCustomerInfo WHERE id ="+id).executeUpdate();
			session.createQuery("DELETE FROM Customer WHERE department='ENGINEERING' and customerId ="+id).executeUpdate();
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
	public Result<EngineeringCustomer> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = engineeringCustomerDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("DELETE FROM EngineeringCustomer WHERE id in("+ids+")").executeUpdate();
			session.createQuery("DELETE FROM EngineeringCustomerInfo WHERE id in("+ids+")").executeUpdate();
			session.createQuery("DELETE FROM Customer WHERE department='ENGINEERING' and customerId in("+ids+")").executeUpdate();
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
	public Result<EngineeringCustomer> update(EngineeringCustomer t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EngineeringActivator.getSessionUser().getRealName());
			t.setModifierId(EngineeringActivator.getSessionUser().getId());
			engineeringCustomerDao.update(t);
			return Result.success(R.EngineeringCustomer.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EngineeringCustomer.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringCustomer.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringCustomer> getBeanById(Serializable id) {
		try {
			return Result.value(engineeringCustomerDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringCustomer.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EngineeringCustomer> getBeanByFilter(Filter filter) {
		try {
			return Result.value(engineeringCustomerDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringCustomer.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EngineeringCustomer>> getList() {
		try {
			return Result.value(engineeringCustomerDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringCustomer.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EngineeringCustomer>> getListByFilter(Filter filter) {
		try {
			return Result.value(engineeringCustomerDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringCustomer.LOAD_FAILURE);
		}
	}

	@Override
	public Result save(EngineeringCustomer customer,
			EngineeringCustomerInfo customerInfo) {
		Session session = null;
		Transaction tr = null;
		try {
			session = engineeringCustomerDao.openSession();
			tr = session.beginTransaction();
			setCreateAndModifyTime(customerInfo);
			session.save(customerInfo);
			setCreateAndModifyTime(customer);
			customer.setCustomerInfo(customerInfo);
			session.save(customer);
			/*Customer baseCustomer = new Customer();
			BeanUtil.copyProperties(customer, baseCustomer);
			baseCustomer.setCustomerId(customer.getId());
			baseCustomer.setId(null);
			baseCustomer.setDepartment(DepartmentEnum.ENGINEERING);
			session.save(baseCustomer);*/
			tr.commit();
			return Result.success(R.EngineeringCustomer.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(//
					BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EngineeringCustomer.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.EngineeringCustomer.SAVE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<String> generateCode() {
		String code = "["+CalendarUtil.now().get(Calendar.YEAR)+"]";
		EngineeringCustomer customer = engineeringCustomerDao.getBeanByFilter(//
				new Filter(EngineeringCustomer.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)).//
				orderBy("id", Filter.DESC).maxResults(1));
		int count = engineeringCustomerDao.getRowCount(//
				new Filter(EngineeringCustomer.class).//
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
		entity.setCreator(EngineeringActivator.getSessionUser().getRealName());
		entity.setCreatorId(EngineeringActivator.getSessionUser().getId());
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

	@Override
	public Result update(EngineeringCustomer customer,
			EngineeringCustomerInfo customerInfo) {
		Session session = null;
		Transaction tr = null;
		try {
			session = engineeringCustomerDao.openSession();
			tr = session.beginTransaction();
			setModifyTime(customerInfo);
			session.update(customerInfo);
			setModifyTime(customer);
			customer.setCustomerInfo(customerInfo);
			session.update(customer);
			/*Customer baseCustomer = (Customer) session.createQuery(//
					"From Customer WHERE department='ENGINEERING' and customerId ="+customer.getId()).uniqueResult();			
			Long id = baseCustomer.getId();
			BeanUtil.copyProperties(customer, baseCustomer);
			baseCustomer.setId(id);
			baseCustomer.setDepartment(DepartmentEnum.ENGINEERING);
			session.update(baseCustomer);*/
			tr.commit();
			return Result.success(R.EngineeringCustomer.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EngineeringCustomer.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.EngineeringCustomer.SAVE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
