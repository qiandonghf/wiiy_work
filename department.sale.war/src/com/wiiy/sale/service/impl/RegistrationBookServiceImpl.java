package com.wiiy.sale.service.impl;


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
import com.wiiy.sale.entity.RegistrationBook;
import com.wiiy.sale.dao.RegistrationBookDao;
import com.wiiy.sale.service.RegistrationBookService;
import com.wiiy.sale.preferences.R;
import com.wiiy.sale.activator.SaleActivator;

/**
 * @author my
 */
public class RegistrationBookServiceImpl implements RegistrationBookService{
	
	private RegistrationBookDao registrationBookDao;
	
	public void setRegistrationBookDao(RegistrationBookDao registrationBookDao) {
		this.registrationBookDao = registrationBookDao;
	}

	@Override
	public Result<RegistrationBook> save(RegistrationBook t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SaleActivator.getSessionUser().getRealName());
			t.setCreatorId(SaleActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			registrationBookDao.save(t);
			return Result.success(R.RegistrationBook.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),RegistrationBook.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RegistrationBook.SAVE_FAILURE);
		}
	}

	@Override
	public Result<RegistrationBook> delete(RegistrationBook t) {
		try {
			registrationBookDao.delete(t);
			return Result.success(R.RegistrationBook.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RegistrationBook.DELETE_FAILURE);
		}
	}

	@Override
	public Result<RegistrationBook> deleteById(Serializable id) {
		try {
			registrationBookDao.deleteById(id);
			return Result.success(R.RegistrationBook.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RegistrationBook.DELETE_FAILURE);
		}
	}

	@Override
	public Result<RegistrationBook> deleteByIds(String ids) {
		try {
			registrationBookDao.deleteByIds(ids);
			return Result.success(R.RegistrationBook.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RegistrationBook.DELETE_FAILURE);
		}
	}

	@Override
	public Result<RegistrationBook> update(RegistrationBook t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SaleActivator.getSessionUser().getRealName());
			t.setModifierId(SaleActivator.getSessionUser().getId());
			registrationBookDao.update(t);
			return Result.success(R.RegistrationBook.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),RegistrationBook.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RegistrationBook.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<RegistrationBook> getBeanById(Serializable id) {
		try {
			return Result.value(registrationBookDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RegistrationBook.LOAD_FAILURE);
		}
	}

	@Override
	public Result<RegistrationBook> getBeanByFilter(Filter filter) {
		try {
			return Result.value(registrationBookDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RegistrationBook.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<RegistrationBook>> getList() {
		try {
			return Result.value(registrationBookDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RegistrationBook.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<RegistrationBook>> getListByFilter(Filter filter) {
		try {
			return Result.value(registrationBookDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RegistrationBook.LOAD_FAILURE);
		}
	}
	
	 
	@Override
	public Result changeState(Long id){
		Session session = null;
		Transaction tr = null;
		try {
			session = registrationBookDao.openSession();
			tr = session.beginTransaction();
			RegistrationBook t = (RegistrationBook) session.get(RegistrationBook.class, id);
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
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),RegistrationBook.class)));
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
}
