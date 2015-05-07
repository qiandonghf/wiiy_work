package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.business.entity.InvestmentBook;
import com.wiiy.business.dao.RegistrationBookDao;
import com.wiiy.business.service.RegistrationBookService;
import com.wiiy.business.preferences.R;
import com.wiiy.business.activator.BusinessActivator;

/**
 * @author my
 */
public class RegistrationBookServiceImpl implements RegistrationBookService{
	
	private RegistrationBookDao registrationBookDao;
	
	public void setRegistrationBookDao(RegistrationBookDao registrationBookDao) {
		this.registrationBookDao = registrationBookDao;
	}

	@Override
	public Result<InvestmentBook> save(InvestmentBook t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			registrationBookDao.save(t);
			return Result.success(R.InvestmentBook.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentBook.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentBook.SAVE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentBook> delete(InvestmentBook t) {
		try {
			registrationBookDao.delete(t);
			return Result.success(R.InvestmentBook.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentBook.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentBook> deleteById(Serializable id) {
		try {
			registrationBookDao.deleteById(id);
			return Result.success(R.InvestmentBook.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentBook.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentBook> deleteByIds(String ids) {
		try {
			registrationBookDao.deleteByIds(ids);
			return Result.success(R.InvestmentBook.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentBook.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentBook> update(InvestmentBook t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			registrationBookDao.update(t);
			return Result.success(R.InvestmentBook.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentBook.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentBook.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentBook> getBeanById(Serializable id) {
		try {
			return Result.value(registrationBookDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentBook.LOAD_FAILURE);
		}
	}

	@Override
	public Result<InvestmentBook> getBeanByFilter(Filter filter) {
		try {
			return Result.value(registrationBookDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentBook.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<InvestmentBook>> getList() {
		try {
			return Result.value(registrationBookDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentBook.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<InvestmentBook>> getListByFilter(Filter filter) {
		try {
			return Result.value(registrationBookDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentBook.LOAD_FAILURE);
		}
	}
	
	 
	@Override
	public Result changeState(Long id){
		Session session = null;
		Transaction tr = null;
		try {
			session = registrationBookDao.openSession();
			tr = session.beginTransaction();
			InvestmentBook t = (InvestmentBook) session.get(InvestmentBook.class, id);
			if (t.getEntityStatus() == EntityStatus.NORMAL) {
				t.setEntityStatus(EntityStatus.LOCKED);
			}else {
				t.setEntityStatus(EntityStatus.NORMAL);
			}
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			session.update(t);
			tr.commit();
			return Result.success("更新状态成功");
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentBook.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure("更新状态失败");
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result getRowCount(Filter filter) {
		try{
			return Result.value(registrationBookDao.getRowCount(filter));
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	} 
}
