package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.ProductDao;
import com.wiiy.business.entity.BusinessCustomer;
import com.wiiy.business.entity.CustomerModifyLog;
import com.wiiy.business.entity.ParkLog;
import com.wiiy.business.entity.Product;
import com.wiiy.business.preferences.R;
import com.wiiy.business.preferences.enums.ParkLogTypeEnums;
import com.wiiy.business.service.ProductService;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class ProductServiceImpl implements ProductService{
	
	private ProductDao productDao;
	
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	public Result<Product> save(Product t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = productDao.openSession();
			tr = session.beginTransaction();
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			session.save(t);
			session.save(modifyLog(t, session, "新建"));
			session.save(parkModifyLog(t, session, "新建"));
			tr.commit();
			return Result.success(R.Product.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Product.class)));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.Product.SAVE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<Product> delete(Product t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = productDao.openSession();
			tr = session.beginTransaction();
			session.delete(modifyLog(t, session, "删除"));
			session.save(parkModifyLog(t, session, "删除"));
			session.delete(t);
			tr.commit();
			return Result.success(R.Product.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.Product.DELETE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<Product> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = productDao.openSession();
			tr = session.beginTransaction();
			Product t = (Product)session.get(Product.class, id);
			//session.delete(modifyLog(t, session, "删除"));
			session.save(modifyLog(t, session, "删除"));
			session.save(parkModifyLog(t, session, "删除"));
			session.delete(t);
			tr.commit();
			return Result.success(R.Product.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.Product.DELETE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<Product> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = productDao.openSession();
			tr = session.beginTransaction();
			List<Product> list = session.createQuery("from Product where id in ("+ids+")").list();
			for (Product t : list) {
				//session.delete(modifyLog(t, session, "删除"));
				session.save(modifyLog(t, session, "删除"));
				session.save(parkModifyLog(t, session, "删除"));
				session.delete(t);
			}
			tr.commit();
			return Result.success(R.Product.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.Product.DELETE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<Product> update(Product t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = productDao.openSession();
			tr = session.beginTransaction();
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			session.update(t);
			session.save(modifyLog(t, session, "更新"));
			session.save(parkModifyLog(t, session, "更新"));
			tr.commit();
			return Result.success(R.Product.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Product.class)));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.Product.UPDATE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<Product> getBeanById(Serializable id) {
		try {
			return Result.value(productDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.Product.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Product> getBeanByFilter(Filter filter) {
		try {
			return Result.value(productDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Product.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Product>> getList() {
		try {
			return Result.value(productDao.getList());
		} catch (Exception e) {
			return Result.failure(R.Product.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Product>> getListByFilter(Filter filter) {
		try {
			return Result.value(productDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Product.LOAD_FAILURE);
		}
	}
	private CustomerModifyLog modifyLog(Product t,Session session,String op){
		BusinessCustomer customer = (BusinessCustomer)session.get(BusinessCustomer.class,t.getCustomerId());
		CustomerModifyLog customerModifyLog = new CustomerModifyLog();
		customerModifyLog.setContent(op+"项目与产品【"+t.getName()+"】");
		customerModifyLog.setCustomer(customer);
		customerModifyLog.setCustomerId(t.getCustomerId());
		customerModifyLog.setCreateTime(new Date());
		customerModifyLog.setCreator(BusinessActivator.getSessionUser().getRealName());
		customerModifyLog.setCreatorId(BusinessActivator.getSessionUser().getId());
		customerModifyLog.setModifyTime(new Date());
		customerModifyLog.setModifier(t.getCreator());
		customerModifyLog.setModifierId(t.getCreatorId());
		customerModifyLog.setModifyLogTime(t.getCreateTime());
		customerModifyLog.setEntityStatus(EntityStatus.NORMAL);
		return customerModifyLog;
	}
	private ParkLog parkModifyLog(Product t,Session session,String op){
		BusinessCustomer customer = (BusinessCustomer)session.get(BusinessCustomer.class,t.getCustomerId());
		ParkLog parkLog = new ParkLog() ;
		parkLog.setContent(customer.getName()+"    "+op+"项目与产品【"+t.getName()+"】");
		parkLog.setParkLogType(ParkLogTypeEnums.CUSTOMERMODIFY);
		parkLog.setHandlePersonnel(BusinessActivator.getSessionUser().getRealName());
		parkLog.setCreateTime(new Date());
		return parkLog ;
	}
}
